package org.max.home_2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToyTest {
    @Test
    void testOpenToy() {
        Toy toy = new Toy("Мяч");
        assertEquals("Открыт подарок: игрушка Мяч", toy.open());
    }
}
