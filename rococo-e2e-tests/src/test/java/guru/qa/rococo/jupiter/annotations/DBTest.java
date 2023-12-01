package guru.qa.rococo.jupiter.annotations;

import guru.qa.rococo.jupiter.extensions.UserRepositoryResolver;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(UserRepositoryResolver.class)
public @interface DBTest {

}
