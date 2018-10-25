package games;

import static java.lang.Math.random;
import static java.lang.Math.round;

public class Slot {

    public static void main(String... __) {
        int playerFund = 100;
        int betSum = 10;
        int winFund = 1_000;
        int counterSize = 7;

        int firstCounter  = 0;
        int secondCounter = 0;
        int thirdCounter  = 0;

        while (playerFund != 0) {
            firstCounter  = (firstCounter + (int) round(random() * 100)) % counterSize;
            secondCounter = (secondCounter + (int) round(random() * 100)) % counterSize;
            thirdCounter  = (thirdCounter + (int) round(random() * 100)) % counterSize;

            System.out.printf("У Вас %d$, ставка - %d$%n", playerFund, betSum);
            System.out.println("Крутим барабаны! Розыгрыш принёс следующие результаты:");
            System.out.printf("первый барабан - %d, второй - %d, третий - %d%n", firstCounter, secondCounter, thirdCounter);

            if ((firstCounter == secondCounter) && (secondCounter == thirdCounter)) {
                playerFund = playerFund + winFund;
                System.out.printf("Выигрыш %d$, ваш капитал теперь составляет: %d$", winFund, playerFund);
            } else {
                playerFund = playerFund - betSum;
                System.out.printf("Проигрыш %d$, ваш капитал теперь составляет: %d$", betSum, playerFund);
            }
        }

    }
}
