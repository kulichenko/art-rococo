package guru.qa.rococo.jupiter.extensions;

import guru.qa.rococo.jupiter.annotations.ApiLogin;
import guru.qa.rococo.jupiter.annotations.GenerateUser;
import guru.qa.rococo.jupiter.annotations.GeneratedUser;
import guru.qa.rococo.model.UserJson;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class CreateUserExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace
            NESTED = ExtensionContext.Namespace.create(GeneratedUser.Selector.NESTED),
            OUTER = ExtensionContext.Namespace.create(GeneratedUser.Selector.OUTER);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Map<GeneratedUser.Selector, GenerateUser> usersForTest = usersForTest(extensionContext);
        for (Map.Entry<GeneratedUser.Selector, GenerateUser> entry : usersForTest.entrySet()) {
            UserJson user = createUserForTest(entry.getValue());
            extensionContext.getStore(ExtensionContext.Namespace.create(entry.getKey()))
                    .put(getAllureId(extensionContext), user);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().isAnnotationPresent(GeneratedUser.class) &&
                parameterContext.getParameter().getType().isAssignableFrom(UserJson.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        GeneratedUser generatedUser = parameterContext.getParameter().getAnnotation(GeneratedUser.class);
        return extensionContext.getStore(ExtensionContext.Namespace.create(generatedUser.selector()))
                .get(getAllureId(extensionContext), UserJson.class);
    }

    protected abstract UserJson createUserForTest(GenerateUser annotation) throws IOException;

    private Map<GeneratedUser.Selector, GenerateUser> usersForTest(ExtensionContext extensionContext) {
        Map<GeneratedUser.Selector, GenerateUser> result = new HashMap<>();
        ApiLogin apiLogin = extensionContext.getRequiredTestMethod().getAnnotation(ApiLogin.class);
        if (apiLogin != null && apiLogin.user().handleAnnotation()) {
            result.put(GeneratedUser.Selector.NESTED, apiLogin.user());
        }
        GenerateUser outerUser = extensionContext.getRequiredTestMethod().getAnnotation(GenerateUser.class);
        if (outerUser != null && outerUser.handleAnnotation()) {
            result.put(GeneratedUser.Selector.OUTER, outerUser);
        }
        return result;
    }

    private String getAllureId(ExtensionContext context) {
        var allureId = context.getRequiredTestMethod().getAnnotation(AllureId.class);
        if (allureId == null) {
            throw new IllegalStateException("Annotation @AllureId must be present!");
        }
        return allureId.value();
    }
}