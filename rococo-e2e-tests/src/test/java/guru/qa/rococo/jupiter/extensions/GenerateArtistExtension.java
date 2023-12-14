package guru.qa.rococo.jupiter.extensions;

import guru.qa.rococo.data.entity.artist.ArtistEntity;
import guru.qa.rococo.data.repository.artist.ArtistRepository;
import guru.qa.rococo.data.repository.artist.ArtistRepositoryHibernate;
import guru.qa.rococo.jupiter.annotations.GenerateArtist;
import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.util.FakerUtils;
import io.qameta.allure.AllureId;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static guru.qa.rococo.util.FakerUtils.generateRandomInt;
import static guru.qa.rococo.util.FakerUtils.generateRandomName;
import static guru.qa.rococo.util.FakerUtils.generateRandomSurname;
import static guru.qa.rococo.util.Utils.getRandomImage;

public class GenerateArtistExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace
            ARTIST_NAMESPACE = ExtensionContext.Namespace.create(CreateMuseumExtension.class);

    public static File IMAGES = new File("src/test/resources/testdata/img");

    private final ArtistRepository artistRepository = new ArtistRepositoryHibernate();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        List<ArtistJson> artistJsons = new ArrayList<>();
        final String testId = getTestId(context);
        GenerateArtist generateArtistAnnotation = context.getRequiredTestMethod().getAnnotation(GenerateArtist.class);
        if (generateArtistAnnotation != null && generateArtistAnnotation.handleAnnotation() && generateArtistAnnotation.count() > 0) {
            int artistsQty = generateArtistAnnotation.count();
            for (int i = 0; i < artistsQty; i++) {
                ArtistJson artistJson = generateArtistJson(generateArtistAnnotation);
                ArtistEntity artistEntity = addArtistToDb(artistJson);
                artistJson.setId(artistEntity.getId());
                artistJsons.add(artistJson);
            }
            context.getStore(ARTIST_NAMESPACE).put(testId, artistJsons);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> parameterType = parameterContext.getParameter().getType();
        return (parameterType.isAssignableFrom(List.class));
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final String testId = getTestId(extensionContext);
        GenerateArtist annotation = parameterContext.getParameter().getAnnotation(GenerateArtist.class);
        return extensionContext.getStore(ARTIST_NAMESPACE).get(testId);
    }

    @Step("Generate artist json")
    private ArtistJson generateArtistJson(GenerateArtist generateArtistAnnotation) {

        ArtistJson artistJson = new ArtistJson();
        String artistName = generateArtistAnnotation.name();
        String biography = generateArtistAnnotation.biography();
        String photo = generateArtistAnnotation.photo();
        if ("".equals(artistName)) {
            artistName = generateRandomName() + " " + generateRandomSurname();
        }
        if ("".equals(biography)) {
            biography = FakerUtils.generateRandomSentence(generateRandomInt(4, 12));
        }
        if ("".equals(photo)) {
            photo = getRandomImage(IMAGES);
        }

        artistJson.setName(artistName);
        artistJson.setBiography(biography);
        artistJson.setPhoto(photo);
        return artistJson;
    }

    @Step("Add artist to data base")
    private ArtistEntity addArtistToDb(ArtistJson artistJson) {
        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setName(artistJson.getName());
        artistEntity.setBiography(artistJson.getBiography());
        artistEntity.setPhoto(artistJson.getPhoto().getBytes());
        artistRepository.addArtist(artistEntity);
        return artistRepository.findByName(artistEntity);
    }

    private String getTestId(ExtensionContext context) {
        return AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                AllureId.class
        ).orElseThrow().value();
    }
}
