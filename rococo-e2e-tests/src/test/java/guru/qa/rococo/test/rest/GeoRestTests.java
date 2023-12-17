package guru.qa.rococo.test.rest;

import guru.qa.rococo.api.geo.GeoRestClient;
import guru.qa.rococo.model.CountryJson;
import io.qameta.allure.AllureId;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.UUID;

import static guru.qa.rococo.util.FakerUtils.generateRandomInt;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tags({@Tag("REST"), @Tag("GEO")})
@Epic("GEO")
public class GeoRestTests extends BaseRestTests {

    private final GeoRestClient restClient = new GeoRestClient();

    @CsvSource("180")
    @ParameterizedTest
    @DisplayName("[REST][GEO] Countries quantity should be greater")
    @AllureId("33")
    void geoQtyShouldBeGreater180(int expectedCountriesQty) throws Exception {
        List<CountryJson> countries = restClient.findAll();
        step("Check that quantity of countries greater than " + expectedCountriesQty, () ->
                assertTrue(countries.size() > expectedCountriesQty, "countries qty less then "
                        + expectedCountriesQty + ". Actual: " + countries.size()));
    }


    @Test
    @DisplayName("[REST][GEO] Find country by id")
    @AllureId("34")
    void findByIdChecking() throws Exception {
        List<CountryJson> allCountries = restClient.findAll();
        UUID id = allCountries.get(generateRandomInt(0, 180)).getId();
        List<CountryJson> countryFoundedById = restClient.findByIds(List.of(String.valueOf(id)));
        step("Check country is not null", () ->
                assertFalse(countryFoundedById.isEmpty()));
        step("Check that founded 1 country by id", () ->
                assertEquals(1, countryFoundedById.size()));
        step("Check country's id'", () ->
                assertEquals(id, countryFoundedById.get(0).getId()));
    }

}
