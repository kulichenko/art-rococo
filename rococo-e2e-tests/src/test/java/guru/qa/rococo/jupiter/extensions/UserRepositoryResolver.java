package guru.qa.rococo.jupiter.extensions;

import guru.qa.rococo.data.repository.UserRepository;
import guru.qa.rococo.jupiter.annotations.Repository;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.junit.platform.commons.support.AnnotationSupport;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class UserRepositoryResolver implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        List<Field> fields = Arrays.stream(testInstance.getClass().getDeclaredFields())
                .filter(field -> AnnotationSupport.isAnnotated(field, Repository.class)
                        && field.getType().isAssignableFrom(UserRepository.class))
                .peek(field -> field.setAccessible(true))
                .toList();

        for (Field field : fields) {
            field.set(testInstance, UserRepository.getRepository());
        }
    }
}
