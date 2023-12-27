package org.max.home_2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CandyTest {
    @Test
    void testOpenCandy() {
        Candy candy = new Candy("'Мишки в лесу'");
        assertEquals("Открыт подарок: шоколадка ", candy.open());
    }
}
