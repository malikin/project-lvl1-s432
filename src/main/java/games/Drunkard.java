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

        playersCardTails[PLAYER_1] = 0;
        playersCardTails[PLAYER_2] = 0;
        playersCardHeads[PLAYER_1] = CARDS_TOTAL_COUNT/2;
        playersCardHeads[PLAYER_2] = CARDS_TOTAL_COUNT/2;
        playersWins[PLAYER_1] = false;
        playersWins[PLAYER_2] = false;

        int cycleCount = 0;

        while (!(playerCardsIsEmpty(PLAYER_1) || playerCardsIsEmpty(PLAYER_2))) {
            cycleCount++;

            int cardPlayer1 = getCardFromPlayerDeck(PLAYER_1);
            int cardPlayer2 = getCardFromPlayerDeck(PLAYER_2);

            playersBattle(cardPlayer1, cardPlayer2);

            log.info(
                    "Итерация №{} игрок №1 карта: {}; игрок №2 карта: {}.",
                    cycleCount,
                    CardUtils.toString(cardPlayer1),
                    CardUtils.toString(cardPlayer2)
            );

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
        playersCardDecks[player][playersCardHeads[player]] = card;
        playersCardHeads[player] = incrementIndex(playersCardHeads[player]);
    }

    private static int getCardFromPlayerDeck(int player) {
        int card = playersCardDecks[player][playersCardTails[player]];
        playersCardDecks[player][playersCardTails[player]] = 0;
        playersCardTails[player] = incrementIndex(playersCardTails[player]);

        return card;
    }

    private static int countCardsInPlayerDeck(int player) {
        int count;

        if (playersCardHeads[player] >= playersCardTails[player]) {
            count = playersCardHeads[player] - playersCardTails[player];
        } else {
            count = (playersCardHeads[player] + CARDS_TOTAL_COUNT) - playersCardTails[player];
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

        if (cardPlayer1Par.equals(Par.SIX) || cardPlayer2Par.equals(Par.SIX)) {
            if (cardPlayer1Par.equals(Par.SIX) && cardPlayer2Par.equals(Par.ACE)) {
                addCardToPlayerDeck(PLAYER_1, cardPlayer1);
                addCardToPlayerDeck(PLAYER_1, cardPlayer2);
                setWinner(PLAYER_1);

                log.info("Выиграл игрок 1!");

                return;
            }
            if (cardPlayer2Par.equals(Par.SIX) && cardPlayer1Par.equals(Par.ACE)) {
                addCardToPlayerDeck(PLAYER_2, cardPlayer1);
                addCardToPlayerDeck(PLAYER_2, cardPlayer2);
                setWinner(PLAYER_2);

                log.info("Выиграл игрок 2!");

                return;
            }
        }

        if (cardPlayer1Par.ordinal() > cardPlayer2Par.ordinal()) {
            addCardToPlayerDeck(PLAYER_1, cardPlayer1);
            addCardToPlayerDeck(PLAYER_1, cardPlayer2);
            setWinner(PLAYER_1);

            log.info("Выиграл игрок 1!");
        } else {
            addCardToPlayerDeck(PLAYER_2, cardPlayer1);
            addCardToPlayerDeck(PLAYER_2, cardPlayer2);
            setWinner(PLAYER_2);

            log.info("Выиграл игрок 2!");
        }
    }

    private static void setWinner(int player) {
        Arrays.fill(playersWins, false);
        playersWins[player] = true;
    }
}
