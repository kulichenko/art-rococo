package guru.qa.rococo.jupiter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface GeneratePictures {
    boolean handleAnnotation() default true;

    int count() default 1;

    String title() default "";

    String description() default "";

    String content() default "";

    GenerateArtist generateArtist() default @GenerateArtist;

}
