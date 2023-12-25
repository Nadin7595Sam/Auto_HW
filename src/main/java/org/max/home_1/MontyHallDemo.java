package org.max.home_1;

import java.util.Random;

public class MontyHallDemo {
    public static void main(String[] args) {
        int totalGames = 1000;
        int stayWins = 0;
        int switchWins = 0;

        Random random = new Random();

        for (int i = 0; i < totalGames; i++) {
            int carBehindDoor = random.nextInt(3); // Случайная дверь
            int playerChoice = random.nextInt(3); // Игрок выбирает дверь

            int hostOpens = random.nextInt(3); // Ведущий выбирает дверь для открытия
            while (hostOpens == carBehindDoor || hostOpens == playerChoice) {
                hostOpens = random.nextInt(3);
            }

            // Игрок решает оставить свой выбор
            boolean playerStays = playerChoice == carBehindDoor;

            // Игрок решает поменять выбор
            boolean playerSwitches = !playerStays;

            if (playerStays) {
                stayWins++;
            } else if (playerSwitches) {
                switchWins++;
            }
        }

        System.out.println("Игры, в которых игрок оставил выбор: " + stayWins);
        System.out.println("Игры, в которых игрок поменял выбор: " + switchWins);
    }

    public int[] simulateMontyHallGames(int totalGames) {
        return new int[0];
    }
}