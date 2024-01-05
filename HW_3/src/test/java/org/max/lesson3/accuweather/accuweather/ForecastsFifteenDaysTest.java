package org.max.lesson3.seminar.accuweather;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class ForecastsFifteenDaysTest extends AccuweatherAbstractTest{
    @Test
    void testGetResponse() {
        String response = given().queryParam("apikey", getApiKey()).pathParam("locationKey", 50)
                .when().get(getBaseUrl() + "/forecasts/v1/daily/15day/{locationKey}")
            //тут будет код 401, а не 200. По используемому ключу погода за 15 дней не доступна
                .then().statusCode(200).time(lessThan(2000L))
                .extract().asString();
        System.out.println(response);
//        По собственному ключу:
//        java.lang.AssertionError: 1 expectation failed.
//        Expected status code <200> but was <401>.
    }
}
