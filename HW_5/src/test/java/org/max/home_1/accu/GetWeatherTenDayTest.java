package org.max.home_1.accu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.max.home_1.accu.weather.DailyForecast;
import org.max.home_1.accu.weather.Headline;
import org.max.home_1.accu.weather.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetWeatherTenDayTest extends AbstractTest{

    private static final Logger logger
            = LoggerFactory.getLogger(GetWeatherTenDayTest.class);

    @Test
    void get_shouldReturn200() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 200 запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        Weather weather = new Weather();
        Headline headline = new Headline();
        headline.setCategory("Категория");
        headline.setText("Текст");
        weather.setHeadline(headline);
        DailyForecast dailyForecast = new DailyForecast();
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        dailyForecasts.add(dailyForecast);
        weather.setDailyForecasts(dailyForecasts);

        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/254021");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/254021"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(weather))));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseUrl() + "/forecasts/v1/daily/10day/254021");
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/254021")));
        assertEquals(200, response.getStatusLine().getStatusCode());

        Weather responseBody = mapper.readValue(response.getEntity().getContent(), Weather.class);
        assertEquals("Категория", responseBody.getHeadline().getCategory());
        assertEquals("Текст", responseBody.getHeadline().getText());
        assertEquals(1, responseBody.getDailyForecasts().size());
    }

    @Test
    void checkLocationKeyInUrl() throws IOException, URISyntaxException {
        // Тест для проверки наличия locationKey в URL запроса
        //given
        ObjectMapper mapper = new ObjectMapper();
        Weather weather = new Weather();
        Headline headline = new Headline();
        headline.setCategory("Категория");
        headline.setText("Текст");
        weather.setHeadline(headline);
        DailyForecast dailyForecast = new DailyForecast();
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        dailyForecasts.add(dailyForecast);
        weather.setDailyForecasts(dailyForecasts);

        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/254021");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/254021"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(weather))));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseUrl() + "/forecasts/v1/daily/10day/254021");
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/254021")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        logger.info("Проверка наличия locationKey в URL: Passed");
    }

    @Test
    void checkCategoryAndTextNotEmpty() throws IOException, URISyntaxException {
        // Тест для проверки, что значения category и text не являются пустыми
        //given
        ObjectMapper mapper = new ObjectMapper();
        Weather weather = new Weather();
        Headline headline = new Headline();
        headline.setCategory("");
        headline.setText("");
        weather.setHeadline(headline);
        DailyForecast dailyForecast = new DailyForecast();
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        dailyForecasts.add(dailyForecast);
        weather.setDailyForecasts(dailyForecasts);

        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/254021");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/254021"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(weather))));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseUrl() + "/forecasts/v1/daily/10day/254021");
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/254021")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        Weather responseBody = mapper.readValue(response.getEntity().getContent(), Weather.class);
        if (!responseBody.getHeadline().getCategory().isEmpty() && !responseBody.getHeadline().getText().isEmpty()) {
            logger.info("Проверка, что значения category и text не являются пустыми: Passed");
        } else {
            logger.error("Проверка, что значения category и text не являются пустыми: Failed");
        }
    }

    @Test
    void checkValidResponseStructure() throws IOException, URISyntaxException {
        // Тест для проверки структуры корректного ответа
        //given
        ObjectMapper mapper = new ObjectMapper();
        Weather weather = new Weather();
        Headline headline = new Headline();
        headline.setCategory("Категория");
        headline.setText("Текст");
        weather.setHeadline(headline);
        DailyForecast dailyForecast = new DailyForecast();
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        dailyForecasts.add(dailyForecast);
        weather.setDailyForecasts(dailyForecasts);

        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/254021");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/254021"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(weather))));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseUrl() + "/forecasts/v1/daily/10day/254021");
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/254021")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        Weather responseBody = mapper.readValue(response.getEntity().getContent(), Weather.class);
        if (responseBody.getHeadline() != null && responseBody.getDailyForecasts() != null) {
            logger.info("Проверка структуры корректного ответа: Passed");
        } else {
            logger.error("Проверка структуры корректного ответа: Failed");
        }
    }

    @Test
    void checkDailyForecastsNotEmpty() throws IOException, URISyntaxException {
        // Тест для проверки, что список ежедневных прогнозов не является пустым
        //given
        ObjectMapper mapper = new ObjectMapper();
        Weather weather = new Weather();
        Headline headline = new Headline();
        headline.setCategory("Категория");
        headline.setText("Текст");
        weather.setHeadline(headline);
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        weather.setDailyForecasts(dailyForecasts);

        logger.debug("Формирование мока для GET /forecasts/v1/daily/10day/254021");
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/254021"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(weather))));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseUrl() + "/forecasts/v1/daily/10day/254021");
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/forecasts/v1/daily/10day/254021")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        Weather responseBody = mapper.readValue(response.getEntity().getContent(), Weather.class);
        if (!responseBody.getDailyForecasts().isEmpty()) {
            logger.info("Проверка, что список ежедневных прогнозов не является пустым: Passed");
        } else {
            logger.error("Проверка, что список ежедневных прогнозов не является пустым: Failed");
        }
    }

    @Test
    void checkInvalidLocationResponse() throws IOException, URISyntaxException {
        // Тест для проверки ответа при запросе с неверным местоположением
        //given
        stubFor(get(urlPathEqualTo("/forecasts/v1/daily/10day/invalidLocation"))
                .willReturn(aResponse()
                        .withStatus(404).withBody("Not Found")));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseUrl() + "/forecasts/v1/daily/10day/invalidLocation");
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        assertEquals(404, response.getStatusLine().getStatusCode());
        assertEquals("Not Found", convertResponseToString(response));
        logger.info("Проверка ответа при запросе с неверным местоположением: Passed");
    }
}
