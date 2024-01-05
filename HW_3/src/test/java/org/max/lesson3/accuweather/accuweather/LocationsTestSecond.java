package org.max.lesson3.seminar.accuweather;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.max.lesson3.home.accuweather.location.GeoPosition;
import org.max.lesson3.home.accuweather.location.Location;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocationsTestSecond extends AccuweatherAbstractTest{
    @Test
    void testLocationsAsString(){
        Map<String, String> params = new HashMap<>();
        params.put("apikey", getApiKey());
        params.put("q", "Subot");
        String response = given().queryParams(params)
                .when().get(getBaseUrl() + "/locations/v1/cities/autocomplete")
                .then().statusCode(200).time(lessThan(2000L))
                .extract().asString();

        assertTrue(response.contains("Subotica"));
        assertTrue(response.contains("Serbia"));
    }

    @Test
    void testLocationsAsLocation(){
        Map<String, String> params = new HashMap<>();
        params.put("apikey", getApiKey());
        params.put("q", "Sara");
        List<Location> response = given().queryParams(params)
                .when().get(getBaseUrl() + "/locations/v1/cities/autocomplete")
                .then().statusCode(200).time(lessThan(2000L))
                .extract().body().jsonPath().getList(".", Location.class);
        System.out.println(response);

        Assertions.assertEquals("Sarajevo", response.get(0).getLocalizedName());
        Assertions.assertEquals("Bosnia and Herzegovina", response.get(0).getCountry().getLocalizedName());
    }

    @Test
    void testWithDifferentAssertion(){
        Map<String, String> params = new HashMap<>();
        params.put("apikey", getApiKey());
        params.put("q", "New");
        Response response = given().queryParams(params)
                .when().get(getBaseUrl() + "/locations/v1/cities/autocomplete")
                .then().statusCode(200)
                .extract().response();

        // Проверяем, что заголовок ответа содержит информацию о типе контента
        String contentTypeHeader = response.header("Content-Type");
        Assertions.assertNotNull(contentTypeHeader);
        assertTrue(contentTypeHeader.contains("application/json"));
    }
}
