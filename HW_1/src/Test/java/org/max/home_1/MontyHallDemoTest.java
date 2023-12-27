package org.max.home_1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public abstract class MontyHallDemoTest {

    @Test
    void testMontyHallGame_Positive() {
        MontyHallDemo montyHallDemo = new MontyHallDemo();
        int totalGames = 1000;
        int[] result = montyHallDemo.simulateMontyHallGames(totalGames);

        assertNotNull(result);
        assertEquals(2, result.length); // Ожидаем два значения

        int stayWins = result[0];
        int switchWins = result[1];

        assertTrue(stayWins >= 0);
        assertTrue(switchWins >= 0);
        assertEquals(totalGames, stayWins + switchWins); // Общее количество игр должно быть равно сумме побед при оставлении и при смене выбора
    }

    @Test
    void testMontyHallGame_Negative() {
        MontyHallDemo montyHallDemo = new MontyHallDemo();
        int totalGames = -1000; // Отрицательное количество игр

        // Попытка выполнить метод с отрицательным числом игр
        assertThrows(IllegalArgumentException.class, () -> {
            montyHallDemo.simulateMontyHallGames(totalGames);
        });
    }

    @ParameterizedTest
    @CsvSource({"100", "500", "1000"})
    void testMontyHallGame_Parameterized(int totalGames) {
        MontyHallDemo montyHallDemo = new MontyHallDemo();

        int[] result = montyHallDemo.simulateMontyHallGames(totalGames);

        assertNotNull(result);
        assertEquals(2, result.length);

        int stayWins = result[0];
        int switchWins = result[1];

        assertTrue(stayWins >= 0);
        assertTrue(switchWins >= 0);
        assertEquals(totalGames, stayWins + switchWins);
    }
}
