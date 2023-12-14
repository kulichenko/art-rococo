package guru.qa.rococo.jupiter.annotations;

import guru.qa.rococo.jupiter.extensions.ApiLoginExtension;
import guru.qa.rococo.jupiter.extensions.BrowserExtension;
import guru.qa.rococo.jupiter.extensions.ClearCookiesExtension;
import guru.qa.rococo.jupiter.extensions.DbCreateUserExtension;
import guru.qa.rococo.jupiter.extensions.JpaExtension;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({DbCreateUserExtension.class, ApiLoginExtension.class, BrowserExtension.class, JpaExtension.class, ClearCookiesExtension.class, AllureJunit5.class})
public @interface WebTest {
}
