package org.max.home_1.accu;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.max.home_1.accu.weather.Night;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NightAllureTest extends AbstractTest {
    private static final Logger logger = LoggerFactory.getLogger(NightAllureTest.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Night Properties Test")
    @Description("Verify Night properties such as Icon, IconPhrase, and HasPrecipitation")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Weather")
    @Story("Night properties")
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
        logger.info("Test: testNightProperties");
        logger.info("icon: " + icon);
        logger.info("iconPhrase: " + iconPhrase);
        logger.info("hasPrecipitation: " + hasPrecipitation);
        assertEquals(1, icon);
        assertEquals("Clear", iconPhrase);
        assertEquals(false, hasPrecipitation);
    }

    @Test
    @DisplayName("Night Serialization Test")
    @Description("Verify Night serialization to JSON")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Weather")
    @Story("Night serialization")
    void testNightSerialization() throws Exception {
        //given
        Night night = new Night();
        night.setIcon(2);
        night.setIconPhrase("Cloudy");
        night.setHasPrecipitation(true);

        //when
        String json = mapper.writeValueAsString(night);

        //then
        logger.info("Test: testNightSerialization");
        logger.info("Serialized JSON: " + json);
        String expectedJson = "{\"Icon\":2,\"IconPhrase\":\"Cloudy\",\"HasPrecipitation\":true}";
        assertEquals(expectedJson, json);

        //and
        Night deserializedNight = mapper.readValue(expectedJson, Night.class);
        assertEquals(night.getIcon(), deserializedNight.getIcon());
        assertEquals(night.getIconPhrase(), deserializedNight.getIconPhrase());
        assertEquals(night.getHasPrecipitation(), deserializedNight.getHasPrecipitation());
    }
}