package org.max.home_1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

//Класс с тестами не должен быть abstract
public abstract class MontyHallDemoTest {

    @Test
    void testMontyHallGame_Positive() {
        MontyHallDemo montyHallDemo = new MontyHallDemo();
        int totalGames = 1000;
        int[] result = montyHallDemo.simulateMontyHallGames(totalGames);

        assertNotNull(result);
        // у Вас result всегда int[0] - т.е. пустой массив, поэтому его длина будет 0
        assertEquals(2, result.length); // Ожидаем два значения

        int stayWins = result[0];
        //тут у Вас будет Exception
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
        // у Вас параметр totalGames никак не используется в методе simulateMontyHallGames, поэтому IllegalArgumentException не будет
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
        // у Вас result всегда int[0] - т.е. пустой массив, поэтому его длина будет 0
        assertEquals(2, result.length);

        int stayWins = result[0];
        int switchWins = result[1];

        assertTrue(stayWins >= 0);
        assertTrue(switchWins >= 0);
        assertEquals(totalGames, stayWins + switchWins);
    }
}
