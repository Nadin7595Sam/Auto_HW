package org.max.home_1.accu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.RequestPatternBuilder;
import org.junit.jupiter.api.Test;
import org.max.home_1.accu.weather.Night;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NightTest extends AbstractTest{
    private static final Logger logger
            = LoggerFactory.getLogger(GetWeatherOneDayTest.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testNightProperties() throws Exception {
        //given
        Night night = new Night();
        night.setIcon(1);
        night.setIconPhrase("Clear");
        night.setHasPrecipitation(false);

        //when
        Integer icon = night.getIcon();
        String iconPhrase = night.getIconPhrase();
        Boolean hasPrecipitation = night.getHasPrecipitation();

        //then
        System.out.println("Test: testNightProperties");
        System.out.println("icon: " + icon);
        System.out.println("iconPhrase: " + iconPhrase);
        System.out.println("hasPrecipitation: " + hasPrecipitation);
        assertEquals(1, icon);
        assertEquals("Clear", iconPhrase);
        assertEquals(false, hasPrecipitation);
    }

    @Test
    void testNightSerialization() throws Exception {
        //given
        Night night = new Night();
        night.setIcon(2);
        night.setIconPhrase("Cloudy");
        night.setHasPrecipitation(true);

        //when
        String json = mapper.writeValueAsString(night);

        //then
        System.out.println("Test: testNightSerialization");
        System.out.println("Serialized JSON: " + json);
        String expectedJson = "{\"Icon\":2,\"IconPhrase\":\"Cloudy\",\"HasPrecipitation\":true}";
        assertEquals(expectedJson, json);

        //and
        Night deserializedNight = mapper.readValue(expectedJson, Night.class);
        assertEquals(night.getIcon(), deserializedNight.getIcon());
        assertEquals(night.getIconPhrase(), deserializedNight.getIconPhrase());
        assertEquals(night.getHasPrecipitation(), deserializedNight.getHasPrecipitation());
    }

    private RequestPatternBuilder urlPathEqualTo(String path) {
        return getRequestedFor(urlPathEqualTo(path));
    }

    private RequestPatternBuilder getRequestedFor(RequestPatternBuilder requestPatternBuilder) {
        return requestPatternBuilder;
    }

    private RequestPatternBuilder request(String path) {
        return urlPathEqualTo(path);
    }
}