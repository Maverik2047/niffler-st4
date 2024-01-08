package guru.qa.niffler.jupiter;

import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.TestData;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static guru.qa.niffler.jupiter.User.UserType.*;

public class UsersQueueExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(UsersQueueExtension.class);

    private static Map<User.UserType, Queue<UserJson>> users = new ConcurrentHashMap<>();

    static {
        Queue<UserJson> friendsQueue = new ConcurrentLinkedQueue<>();
        friendsQueue.add(user("dima", "12345", WITH_FRIENDS, "duck1"));
        friendsQueue.add(user("duck1", "12345", WITH_FRIENDS, "dima"));

        Queue<UserJson> invitationSent = new ConcurrentLinkedQueue<>();
        invitationSent.add(user("maverik2047", "12345", INVITATION_SEND, "duck1"));
        invitationSent.add(user("duck1", "12345", INVITATION_SEND, "maverik2047"));

        Queue<UserJson> invitationReceived = new ConcurrentLinkedQueue<>();
        invitationReceived.add(user("dog", "12345", INVITATION_RECEIVED, "duck1"));
        invitationReceived.add(user("duck", "12345", INVITATION_RECEIVED, "maverik2047"));

        users.put(WITH_FRIENDS, friendsQueue);
        users.put(INVITATION_SEND, invitationSent);
        users.put(INVITATION_RECEIVED, invitationReceived);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Parameter[] parameters = context.getRequiredTestMethod().getParameters();

        for (Parameter parameter : parameters) {
            User annotation = parameter.getAnnotation(User.class);
            if (annotation != null && parameter.getType().isAssignableFrom(UserJson.class)) {
                UserJson testCandidate = null;
                Queue<UserJson> queue = users.get(annotation.value());
                while (testCandidate == null) {
                    testCandidate = queue.poll();
                }
                context.getStore(NAMESPACE).put(context.getUniqueId(), testCandidate);
                break;
            }
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        UserJson userFromTest = context.getStore(NAMESPACE)
                .get(context.getUniqueId(), UserJson.class);
        users.get(userFromTest.testData().userType()).add(userFromTest);
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
        return extensionContext.getStore(NAMESPACE)
                .get(extensionContext.getUniqueId(), UserJson.class);
    }

    private static UserJson user(String username, String password, User.UserType userType, String friendName) {
        return new UserJson(
                null,
                username,
                null,
                null,
                CurrencyValues.RUB,
                null,
                null,
                new TestData(
                        password,
                        friendName,
                        userType
                )
        );
    }
}
