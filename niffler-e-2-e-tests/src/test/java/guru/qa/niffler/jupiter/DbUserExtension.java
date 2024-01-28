package guru.qa.niffler.jupiter;

import com.github.javafaker.Faker;
import guru.qa.niffler.db.model.*;
import guru.qa.niffler.db.repository.UserRepository;
import guru.qa.niffler.db.repository.UserRepositoryJdbc;
import org.junit.jupiter.api.extension.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DbUserExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(DbUserExtension.class);

    public UserAuthEntity userAuth = new UserAuthEntity();
    public UserEntity user = new UserEntity();
    public UserRepository userRepository = new UserRepositoryJdbc();

    Faker faker = new Faker();


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        String userName = faker.funnyName().name();
        String password = faker.internet().password(5, 10);

        userAuth.setUsername(userName);
        userAuth.setPassword(password);
        userAuth.setEnabled(true);
        userAuth.setAccountNonExpired(true);
        userAuth.setAccountNonLocked(true);
        userAuth.setCredentialsNonExpired(true);
        userAuth.setAuthorities(Arrays.stream(Authority.values())
                .map(e -> {
                    AuthorityEntity ae = new AuthorityEntity();
                    ae.setAuthority(e);
                    return ae;
                }).toList()
        );

        user.setUsername(userName);
        user.setCurrency(CurrencyValues.RUB);

        userRepository.createInAuth(userAuth);
        userRepository.createInUserdata(user);

        Map<String, Object> userEntities = new HashMap<>();
        userEntities.put("userAuth", userAuth);
        userEntities.put("user", user);

        context.getStore(NAMESPACE).put(context.getUniqueId(), userEntities);
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {

        Map<String, Object> userEntities = (Map<String, Object>) extensionContext.getStore(DbUserExtension.NAMESPACE)
                .get(extensionContext.getUniqueId());

        UserAuthEntity userAuth = (UserAuthEntity) userEntities.get("userAuth");
        UserEntity user = (UserEntity) userEntities.get("user");

        userRepository.deleteInAuthById(userAuth.getId());
        userRepository.deleteInUserdataById(user.getId());
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType().isAssignableFrom(UserAuthEntity.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return (UserAuthEntity) extensionContext.getStore(NAMESPACE)
                .get(extensionContext.getUniqueId(), Map.class)
                .get("userAuth");
    }
}
