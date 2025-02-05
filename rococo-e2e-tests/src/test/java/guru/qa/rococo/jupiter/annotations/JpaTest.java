package guru.qa.rococo.jupiter.annotations;

import guru.qa.rococo.jupiter.extensions.JpaExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(JpaExtension.class)
public @interface JpaTest {

}
