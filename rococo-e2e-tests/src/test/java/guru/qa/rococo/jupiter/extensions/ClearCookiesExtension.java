package guru.qa.rococo.jupiter.extensions;


import guru.qa.rococo.api.ThreadLocalCookieStore;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ClearCookiesExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        ThreadLocalCookieStore.INSTANCE.removeAll();
    }
}
