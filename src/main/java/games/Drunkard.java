package games;

import org.slf4j.Logger;

import java.util.Arrays;
import static games.CardUtils.CARDS_TOTAL_COUNT;

public class Drunkard {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Drunkard.class);

    private static final int PLAYER_1 = 0;
    private static final int PLAYER_2 = 1;

    private static int[][] playersCardDecks = new int[2][CARDS_TOTAL_COUNT];
    private static int[] playersCardTails = new int[2];
    private static int[] playersCardHeads = new int[2];
    private static boolean[] playersWins = new boolean[2];


    public static void main() {
        int[]cardsDeck = CardUtils.getShaffledCards();
        System.arraycopy(cardsDeck, 0, playersCardDecks[PLAYER_1], 0,cardsDeck.length/2);
        System.arraycopy(cardsDeck, cardsDeck.length/2, playersCardDecks[PLAYER_2], 0,cardsDeck.length/2);

        playersCardHeads[PLAYER_1] = CARDS_TOTAL_COUNT/2;
        playersCardHeads[PLAYER_2] = CARDS_TOTAL_COUNT/2;

        int cycleCount = 0;

        while (!(playerCardsIsEmpty(PLAYER_1) || playerCardsIsEmpty(PLAYER_2))) {
            cycleCount++;

            int cardPlayer1 = getCardFromPlayerDeck(PLAYER_1);
            int cardPlayer2 = getCardFromPlayerDeck(PLAYER_2);

            log.info(
                    "Итерация №{} игрок №1 карта: {}; игрок №2 карта: {}.",
                    cycleCount,
                    CardUtils.toString(cardPlayer1),
                    CardUtils.toString(cardPlayer2)
            );

            playersBattle(cardPlayer1, cardPlayer2);

            int player1cardsCount = countCardsInPlayerDeck(PLAYER_1);
            int player2cardsCount = countCardsInPlayerDeck(PLAYER_2);

            log.info("У игрока №1 {} карт, у игрока №2 {} карт.", player1cardsCount, player2cardsCount);
        }

        if (playersWins[PLAYER_1]) {
            log.info("Выиграл первый игрок! Количество произведённых итераций: {}.", cycleCount);
        } else {
            log.info("Выиграл второй игрок! Количество произведённых итераций: {}.", cycleCount);
        }
    }

    private static boolean playerCardsIsEmpty(int playerIndex) {
        int tail = playersCardTails[playerIndex];
        int head = playersCardHeads[playerIndex];

        return tail == head;
    }

    private static void addCardToPlayerDeck(int player, int card) {
        int head = playersCardHeads[player];

        playersCardDecks[player][head] = card;
        playersCardHeads[player] = incrementIndex(head);
    }

    private static int getCardFromPlayerDeck(int player) {
        int tail = playersCardTails[player];
        int card = playersCardDecks[player][tail];

        playersCardDecks[player][tail] = 0;
        playersCardTails[player] = incrementIndex(tail);

        return card;
    }

    private static int countCardsInPlayerDeck(int player) {
        int count;
        int tail = playersCardTails[player];
        int head = playersCardHeads[player];

        if (head >= tail) {
            count = head - tail;
        } else {
            count = (head + CARDS_TOTAL_COUNT) - tail;
        }

        if (count == 0 && playersWins[player]) {
            return CARDS_TOTAL_COUNT;
        }

        return count;
    }

    private static int incrementIndex(int i) {
        return (i + 1) % CARDS_TOTAL_COUNT;
    }

    private static void playersBattle(int cardPlayer1, int cardPlayer2) {
        Par cardPlayer1Par = CardUtils.getPar(cardPlayer1);
        Par cardPlayer2Par = CardUtils.getPar(cardPlayer2);

        if (cardPlayer1Par.ordinal() == cardPlayer2Par.ordinal()) {
            addCardToPlayerDeck(PLAYER_1, cardPlayer1);
            addCardToPlayerDeck(PLAYER_2, cardPlayer2);

            log.info("Спор - каждый остаётся при своих!");

            return;
        }

        if (cardPlayer1Par.equals(Par.SIX) && cardPlayer2Par.equals(Par.ACE)) {
            addCardsToWinner(PLAYER_1, cardPlayer1, cardPlayer2);

            log.info("Выиграл игрок 1!");

            return;
        }

        if (cardPlayer2Par.equals(Par.SIX) && cardPlayer1Par.equals(Par.ACE)) {
            addCardsToWinner(PLAYER_2, cardPlayer1, cardPlayer2);

            log.info("Выиграл игрок 2!");

            return;
        }

        if (cardPlayer1Par.ordinal() > cardPlayer2Par.ordinal()) {
            addCardsToWinner(PLAYER_1, cardPlayer1, cardPlayer2);

            log.info("Выиграл игрок 1!");
        }

        if (cardPlayer1Par.ordinal() < cardPlayer2Par.ordinal()) {
            addCardsToWinner(PLAYER_2, cardPlayer1, cardPlayer2);

            log.info("Выиграл игрок 2!");
        }
    }

    private static void addCardsToWinner(int player, int ...cards) {
        for (int card : cards) {
            addCardToPlayerDeck(player, card);
        }

        Arrays.fill(playersWins, false);
        playersWins[player] = true;
    }
}
