package guru.qa.rococo.util;

import guru.qa.rococo.data.entity.geo.GeoEntity;
import guru.qa.rococo.data.repository.geo.GeoRepositoryHibernate;
import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.model.GeoJson;
import io.qameta.allure.Step;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

public class Utils {
    GeoRepositoryHibernate geoRepositoryHibernate = new GeoRepositoryHibernate();

    @Step("Get random geo for museum")
    public GeoJson getRandomGeo(String city, int start, int end) {
        GeoJson geoJson = new GeoJson();
        GeoEntity randomCountry = geoRepositoryHibernate.getRandomCountry(start, end);
        CountryJson countryJson = new CountryJson();
        countryJson.setName(randomCountry.getName());
        countryJson.setId(randomCountry.getId());
        geoJson.setCountry(countryJson);
        geoJson.setCity(city);
        return geoJson;
    }

    public static String getRandomImage(File dirPath) {
        byte[] byteArray;
        try {
            byteArray = Files.readAllBytes(getRandomFileFromDir(dirPath).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "data:image/jpeg;base64," + Base64.encodeBase64String(byteArray);
    }

    public static File getRandomFileFromDir(File dirPath) {
        File[] files = dirPath.listFiles();
        Random rand = new Random();
        return files[rand.nextInt(files.length)];
    }
}
