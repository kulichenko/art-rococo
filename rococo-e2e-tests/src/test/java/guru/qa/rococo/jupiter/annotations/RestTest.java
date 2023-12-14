package guru.qa.rococo.jupiter.annotations;

import guru.qa.rococo.jupiter.extensions.ClearCookiesExtension;
import guru.qa.rococo.jupiter.extensions.ContextHolderExtension;
import guru.qa.rococo.jupiter.extensions.RestCreateUserExtension;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith({
        ContextHolderExtension.class,
        RestCreateUserExtension.class,
        ClearCookiesExtension.class,
        AllureJunit5.class
})
public @interface RestTest {

}