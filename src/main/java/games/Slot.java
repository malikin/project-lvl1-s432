package games;

import org.slf4j.Logger;

import static java.lang.Math.random;
import static java.lang.Math.round;

public class Slot {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Slot.class);

    public static void main() {
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

            log.info("У Вас {}$, ставка - {}$", playerFund, betSum);
            log.info("Крутим барабаны! Розыгрыш принёс следующие результаты:");
            log.info("первый барабан - {}, второй - {}, третий - {}", firstCounter, secondCounter, thirdCounter);

            if ((firstCounter == secondCounter) && (secondCounter == thirdCounter)) {
                playerFund = playerFund + winFund;
                log.info("Выигрыш {}$, ваш капитал теперь составляет: {}$", winFund, playerFund);
            } else {
                playerFund = playerFund - betSum;
                log.info("Проигрыш {}$, ваш капитал теперь составляет: {}$", betSum, playerFund);
            }
        }

    }
}
