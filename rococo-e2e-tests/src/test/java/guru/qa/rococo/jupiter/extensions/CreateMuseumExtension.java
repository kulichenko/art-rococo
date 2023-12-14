package guru.qa.rococo.jupiter.extensions;

import guru.qa.rococo.data.entity.artist.ArtistEntity;
import guru.qa.rococo.data.entity.geo.GeoEntity;
import guru.qa.rococo.data.entity.museum.MuseumEntity;
import guru.qa.rococo.data.entity.painting.PaintingEntity;
import guru.qa.rococo.data.repository.artist.ArtistRepository;
import guru.qa.rococo.data.repository.artist.ArtistRepositoryHibernate;
import guru.qa.rococo.data.repository.geo.GeoRepository;
import guru.qa.rococo.data.repository.geo.GeoRepositoryHibernate;
import guru.qa.rococo.data.repository.museum.MuseumRepository;
import guru.qa.rococo.data.repository.museum.MuseumRepositoryHibernate;
import guru.qa.rococo.data.repository.painting.PaintingRepository;
import guru.qa.rococo.data.repository.painting.PaintingRepositoryHibernate;
import guru.qa.rococo.jupiter.annotations.Artist;
import guru.qa.rococo.jupiter.annotations.GenerateMuseum;
import guru.qa.rococo.jupiter.annotations.GeneratePictures;
import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.GeoJson;
import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.model.PaintingJson;
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
import java.util.UUID;

import static guru.qa.rococo.util.FakerUtils.generateNewMuseumTitle;
import static guru.qa.rococo.util.FakerUtils.generateRandomCity;
import static guru.qa.rococo.util.FakerUtils.generateRandomInt;
import static guru.qa.rococo.util.FakerUtils.generateRandomName;
import static guru.qa.rococo.util.FakerUtils.generateRandomSentence;
import static guru.qa.rococo.util.FakerUtils.generateRandomSurname;
import static guru.qa.rococo.util.Utils.getRandomImage;

public class CreateMuseumExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace
            MUSEUM_NAMESPACE = ExtensionContext.Namespace.create(CreateMuseumExtension.class);

    public static File IMAGES = new File("src/test/resources/testdata/img");

    private final GeoRepository geoRepository = new GeoRepositoryHibernate();
    private final ArtistRepository artistRepository = new ArtistRepositoryHibernate();

    private final MuseumRepository museumRepository = new MuseumRepositoryHibernate();

    private final PaintingRepository paintingRepository = new PaintingRepositoryHibernate();

    @Override
    public void beforeEach(ExtensionContext context) {
        List<PaintingJson> paintingJsons = new ArrayList<>();
        final String testId = getTestId(context);
        PaintingJson paintingJson;
        GenerateMuseum generateMuseumAnnotation = context.getRequiredTestMethod().getAnnotation(GenerateMuseum.class);
        if (generateMuseumAnnotation != null && generateMuseumAnnotation.handleAnnotation() && generateMuseumAnnotation.count() > 0) {
            int museumsQuantity = generateMuseumAnnotation.count();
            for (int i = 0; i < museumsQuantity; i++) {
                MuseumJson museumJson = getMuseumJson(generateMuseumAnnotation);
                MuseumEntity museumEntity = addMuseumToDb(museumJson);
                museumJson.setId(museumEntity.getId());
                GeneratePictures generatePicturesAnnotation = generateMuseumAnnotation.generatePictures();
                if (generatePicturesAnnotation != null && generatePicturesAnnotation.handleAnnotation() && generatePicturesAnnotation.count() > 0) {
                    int picturesQuantity = generatePicturesAnnotation.count();
                    for (int j = 0; j < picturesQuantity; j++) {
                        paintingJson = getPaintingJson(generatePicturesAnnotation);
                        paintingJson.setMuseum(museumJson);
                        paintingJson.setMuseumId(museumEntity.getId());
                        PaintingEntity paintingEntity = addPaintingToDb(paintingJson);
                        paintingJson.setId(paintingEntity.getId());
                        paintingJsons.add(paintingJson);
                    }
                } else {
                    paintingJson = new PaintingJson();
                    paintingJson.setMuseum(museumJson);
                    paintingJson.setMuseumId(museumEntity.getId());
                    paintingJsons.add(paintingJson);
                }
            }
            context.getStore(MUSEUM_NAMESPACE).put(testId, paintingJsons);
        }
    }

    @Step("Generate painting json")
    private PaintingJson getPaintingJson(GeneratePictures generatePicturesAnnotation) {
        PaintingJson paintingJson = new PaintingJson();
        String pictureTitle = generatePicturesAnnotation.title();
        String pictureDescription = generatePicturesAnnotation.description();
        String pictureContent = generatePicturesAnnotation.content();
        if ("".equals(pictureTitle)) {
            pictureTitle = generateRandomSentence(generateRandomInt(1, 3));
        }
        if ("".equals(pictureDescription)) {
            pictureDescription = generateRandomSentence(generateRandomInt(3, 8));
        }
        if ("".equals(pictureContent)) {
            pictureContent = getRandomImage(IMAGES);
        }
        ArtistJson artistJson = getArtistJson(generatePicturesAnnotation);
        UUID artistId = addArtistToDb(artistJson).getId();
        artistJson.setId(artistId);
        paintingJson.setTitle(pictureTitle);
        paintingJson.setDescription(pictureDescription);
        paintingJson.setContent(pictureContent);
        paintingJson.setArtist(artistJson);
        paintingJson.setArtistId(artistId);
        return paintingJson;
    }

    @Step("Add painting to data base")
    private PaintingEntity addPaintingToDb(PaintingJson paintingJson) {
        PaintingEntity paintingEntity = new PaintingEntity();
        paintingEntity.setTitle(paintingJson.getTitle());
        paintingEntity.setDescription(paintingJson.getDescription());
        paintingEntity.setContent(paintingJson.getContent().getBytes());
        paintingEntity.setArtistId(paintingJson.getArtistId());
        paintingEntity.setMuseumId(paintingJson.getMuseumId());
        paintingRepository.addPainting(paintingEntity);
        return paintingRepository.findByName(paintingEntity);
    }

    @Step("Generate museum json")
    private MuseumJson getMuseumJson(GenerateMuseum generateMuseumAnnotation) {
        MuseumJson museumJson = new MuseumJson();
        String title = generateMuseumAnnotation.title();
        String description = generateMuseumAnnotation.description();
        String photo = generateMuseumAnnotation.photo();
        String city = generateMuseumAnnotation.city();
        if ("".equals(title)) {
            title = generateNewMuseumTitle();
        }
        if ("".equals(description)) {
            description = generateRandomSentence(generateRandomInt(5, 15));
        }
        if ("".equals(photo)) {
            photo = getRandomImage(IMAGES);
        }
        if ("".equals(city)) {
            city = generateRandomCity();
        }
        GeoJson randomGeo = getRandomGeo(city);
        museumJson.setTitle(title);
        museumJson.setCity(city);
        museumJson.setDescription(description);
        museumJson.setPhoto(photo);
        museumJson.setGeo(randomGeo);
        museumJson.setCountryId(randomGeo.getCountry().getId());
        return museumJson;
    }

    @Step("Add museum to data base")
    private MuseumEntity addMuseumToDb(MuseumJson museumJson) {
        MuseumEntity museumEntity = new MuseumEntity();
        museumEntity.setTitle(museumJson.getTitle());
        museumEntity.setDescription(museumJson.getDescription());
        museumEntity.setPhoto(museumJson.getPhoto().getBytes());
        museumEntity.setCountryId(museumJson.getCountryId());
        museumEntity.setCity(museumJson.getCity());
        museumRepository.addMuseum(museumEntity);
        return museumRepository.findByName(museumEntity);
    }


    @Step("Get random geo for museum")
    private GeoJson getRandomGeo(String city) {
        GeoJson geoJson = new GeoJson();
        GeoEntity randomCountry = geoRepository.getRandomCountry(0, 180);
        CountryJson countryJson = new CountryJson();
        countryJson.setName(randomCountry.getName());
        countryJson.setId(randomCountry.getId());
        geoJson.setCountry(countryJson);
        geoJson.setCity(city);
        return geoJson;
    }

    @Step("Generate artist json")
    private ArtistJson getArtistJson(GeneratePictures generatePicturesAnnotation) {
        Artist generateArtistAnnotation = generatePicturesAnnotation.generateArtist();
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


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> parameterType = parameterContext.getParameter().getType();
        return (parameterType.isAssignableFrom(List.class));
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final String testId = getTestId(extensionContext);
        GenerateMuseum annotation = parameterContext.getParameter().getAnnotation(GenerateMuseum.class);
        return extensionContext.getStore(MUSEUM_NAMESPACE).get(testId);
    }


    private String getTestId(ExtensionContext context) {
        return AnnotationSupport.findAnnotation(
                context.getRequiredTestMethod(),
                AllureId.class
        ).orElseThrow().value();
    }


}
