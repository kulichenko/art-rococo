package guru.qa.rococo.jupiter.annotations;

import guru.qa.rococo.jupiter.extensions.CreateMuseumExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@ExtendWith(CreateMuseumExtension.class)
public @interface GenerateMuseum {
    boolean handleAnnotation() default true;

    String title() default "";

    String description() default "";

    String photo() default "";

    String city() default "";

    int count() default 1;

    Geo geo() default @Geo;

    GeneratePictures generatePictures() default @GeneratePictures(handleAnnotation = false);
}
