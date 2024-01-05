package org.max.lesson3.seminar.accuweather;

import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.max.lesson3.home.accuweather.weather.Headline;
import org.max.lesson3.home.accuweather.weather.Night;
import org.max.lesson3.home.accuweather.weather.Weather;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ForecastsOneDayTest extends AccuweatherAbstractTest{
    @Test
    void testGetResponse(){
        String response = given().queryParam("apikey", getApiKey()).pathParam("locationKey", 50)
                .when().get(getBaseUrl()+ "/forecasts/v1/daily/1day/{locationKey}")
                .then().statusCode(200).time(lessThan(2000L))
                .extract().asString();
        System.out.println(response);
    }

    @Test
    void testWeatherInfo() {
        given()
                .queryParam("apikey", getApiKey())
                .pathParam("locationKey", 50)
                .when()
                .get(getBaseUrl() + "/forecasts/v1/daily/1day/{locationKey}")
                .then()
                .statusCode(200)
                .assertThat()
                .body("DailyForecasts[0].Night.Icon", equalTo(22))
                .body("DailyForecasts[0].Night.IconPhrase", equalTo("Snow"))
                .body("DailyForecasts[0].Night.HasPrecipitation", equalTo(true))
                .body("DailyForecasts[0].Night.PrecipitationType", equalTo("Snow"))
                .body("DailyForecasts[0].Night.PrecipitationIntensity", equalTo("Light"));
    }

    @Test
    void testHeadlineInfo() {
        Headline headline = given()
                .queryParam("apikey", getApiKey())
                .pathParam("locationKey", 50)
                .when()
                .get(getBaseUrl() + "/forecasts/v1/daily/1day/{locationKey}")
                .then()
                .statusCode(200)
                .extract().jsonPath().getObject("Headline", Headline.class);

        // Проверки для объекта Headline
        assertThat(headline.getCategory(), equalTo("snow"));
        assertThat(headline.getEffectiveDate(), equalTo("2024-01-04T19:00:00-07:00"));
        assertThat(headline.getSeverity(), equalTo(4));
    }
}
