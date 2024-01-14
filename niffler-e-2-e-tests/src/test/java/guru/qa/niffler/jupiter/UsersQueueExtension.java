package guru.qa.niffler.jupiter;

import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static guru.qa.niffler.jupiter.User.UserType.*;
import static guru.qa.niffler.model.UserJson.user;

public class UsersQueueExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(UsersQueueExtension.class);

    private static final Map<User.UserType, Queue<UserJson>> USERS = new ConcurrentHashMap<>();

    static {
        Queue<UserJson> friendsQueue = new ConcurrentLinkedQueue<>();
        friendsQueue.add(user("duck1", "12345", WITH_FRIENDS, "dima"));
        friendsQueue.add(user("dima", "12345", WITH_FRIENDS, "duck1"));


        Queue<UserJson> invitationSent = new ConcurrentLinkedQueue<>();
        invitationSent.add(user("maverik2047", "12345", INVITATION_SEND, "duck"));
        invitationSent.add(user("duck1", "12345", INVITATION_SEND, "dog"));

        Queue<UserJson> invitationReceived = new ConcurrentLinkedQueue<>();
        invitationReceived.add(user("dog", "12345", INVITATION_RECEIVED, "duck1"));
        invitationReceived.add(user("duck", "12345", INVITATION_RECEIVED, "maverik2047"));

        USERS.put(WITH_FRIENDS, friendsQueue);
        USERS.put(INVITATION_SEND, invitationSent);
        USERS.put(INVITATION_RECEIVED, invitationReceived);
    }

    @Override
    public void beforeEach(ExtensionContext context) {

        List<Method> methods = new ArrayList<>();
        methods.add(context.getRequiredTestMethod());
        Arrays.stream(context.getRequiredTestClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(BeforeEach.class))
                .forEach(methods::add);

        List<Parameter> parameters = methods.stream()
                .map(Executable::getParameters)
                .flatMap(Arrays::stream)
                .filter(parameter -> parameter.isAnnotationPresent(User.class))
                .filter(parameter -> parameter.getType().isAssignableFrom(UserJson.class))
                .toList();

        Map<User.UserType, UserJson> usersForTest = new HashMap<>();


        for (Parameter parameter : parameters) {
            User.UserType userType = parameter.getAnnotation(User.class).value();

            if (usersForTest.containsKey(userType)) {
                continue;
            }
            UserJson testCandidate = null;
            Queue<UserJson> queue = USERS.get(userType);
            while (testCandidate == null) {
                testCandidate = queue.poll();
            }
            usersForTest.put(userType, testCandidate);
        }
        context.getStore(NAMESPACE)
                .put(context.getUniqueId(), usersForTest);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {

        Map<User.UserType, UserJson> usersFromTest = (Map<User.UserType, UserJson>) context.getStore(NAMESPACE)
                .get(context.getUniqueId(), Map.class);
        for (User.UserType userType : usersFromTest.keySet()) {
            USERS.get(userType).add(usersFromTest.get(userType));
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {

        return parameterContext.getParameter()
                .getType()
                .isAssignableFrom(UserJson.class) &&
                parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {

        return (UserJson) extensionContext.getStore(NAMESPACE)
                .get(extensionContext.getUniqueId(), Map.class)
                .get(parameterContext.findAnnotation(User.class).get().value());
    }
}
