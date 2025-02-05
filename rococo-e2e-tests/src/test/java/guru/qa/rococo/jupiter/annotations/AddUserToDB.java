package guru.qa.rococo.jupiter.annotations;

import guru.qa.rococo.jupiter.extensions.DBUserExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(DBUserExtension.class)
public @interface AddUserToDB {
    String username() default "";

    String password() default "";
}
