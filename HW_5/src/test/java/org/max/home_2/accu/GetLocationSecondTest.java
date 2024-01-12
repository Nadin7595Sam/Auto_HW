package org.max.home_2.accu;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.max.home_2.accu.location.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetLocationSecondTest extends AbstractTest{

    private static final Logger logger
            = LoggerFactory.getLogger(GetLocationSecondTest.class);

    @Test
    void get_shouldReturn404() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 404 запущен");
        //given
        logger.debug("Формирование мока для GET /locations/v1/cities/autocomplete");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withQueryParam("q", equalTo("notFound"))
                .willReturn(aResponse()
                        .withStatus(404).withBody("Not Found")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/locations/v1/cities/autocomplete");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("q", "notFound")
                .build();
        request.setURI(uri);
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/autocomplete")));
        assertEquals(404, response.getStatusLine().getStatusCode());
        assertEquals("Not Found", convertResponseToString(response));
    }

    @Test
    void checkLocationKey() throws IOException, URISyntaxException {
        logger.info("Тест проверки ключа локации запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        Location body = new Location();
        body.setKey("TestKey");

        logger.debug("Формирование мока для GET /locations/v1/cities/autocomplete");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withQueryParam("q", equalTo("TestLocation"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(body))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/locations/v1/cities/autocomplete");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("q", "TestLocation")
                .build();
        request.setURI(uri);
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/autocomplete")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("TestKey", mapper.readValue(response.getEntity().getContent(), Location.class).getKey());
    }

    @Test
    void checkEmptyResponse() throws IOException, URISyntaxException {
        logger.info("Тест проверки пустого ответа запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();

        logger.debug("Формирование мока для GET /locations/v1/cities/autocomplete");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withQueryParam("q", equalTo("empty"))
                .willReturn(aResponse()
                        .withStatus(200).withBody("")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/locations/v1/cities/autocomplete");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("q", "empty")
                .build();
        request.setURI(uri);
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/autocomplete")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("", convertResponseToString(response));
    }

    @Test
    void checkNullResponse() throws IOException, URISyntaxException {
        logger.info("Тест проверки нулевого ответа запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();

        logger.debug("Формирование мока для GET /locations/v1/cities/autocomplete");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withQueryParam("q", equalTo("null"))
                .willReturn(aResponse()
                        .withStatus(200).withBody("null")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/locations/v1/cities/autocomplete");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("q", "null")
                .build();
        request.setURI(uri);
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/autocomplete")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("null", convertResponseToString(response));
    }

    @Test
    void checkInvalidQueryParam() throws IOException, URISyntaxException {
        logger.info("Тест проверки неверного параметра запроса запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        Location body = new Location();
        body.setKey("InvalidParam");

        logger.debug("Формирование мока для GET /locations/v1/cities/autocomplete");
        stubFor(get(urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withQueryParam("invalid", equalTo("invalid"))
                .willReturn(aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(body))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/locations/v1/cities/autocomplete");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("invalid", "invalid")
                .build();
        request.setURI(uri);
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        verify(getRequestedFor(urlPathEqualTo("/locations/v1/cities/autocomplete")));
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("InvalidParam", mapper.readValue(response.getEntity().getContent(), Location.class).getKey());
    }
}
