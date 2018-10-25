package games;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.apache.commons.math3.util.MathArrays.shuffle;

public class Drunkard {
    private static final int PARS_TOTAL_COUNT = Par.values().length;
    private static final int CARDS_TOTAL_COUNT = PARS_TOTAL_COUNT * Suit.values().length;

    private static final int PLAYER_1 = 0;
    private static final int PLAYER_2 = 1;

    private static int[][] playersCards = new int[2][CARDS_TOTAL_COUNT];

    private static int[] playersCardTails = new int[2];
    private static int[] playersCardHeads = new int[2];

    private static int[] cardsDeck = IntStream.rangeClosed(0, CARDS_TOTAL_COUNT - 1).toArray();


    public static void main() {
        shuffle(cardsDeck);
        System.arraycopy(cardsDeck, 0, playersCards[PLAYER_1], 0,cardsDeck.length/2);
        System.arraycopy(cardsDeck, cardsDeck.length/2, playersCards[PLAYER_2], 0,cardsDeck.length/2);

        playersCardTails[PLAYER_1] = 0;
        playersCardTails[PLAYER_2] = 0;
        playersCardHeads[PLAYER_1] = CARDS_TOTAL_COUNT/2 - 1;
        playersCardHeads[PLAYER_2] = CARDS_TOTAL_COUNT/2 - 1;

        int cycleCount = 0;
        boolean player1win = false;
        boolean player2win = false;

        while (true) {
            if (player1win) {
                System.out.printf("Выиграл первый игрок! Количество произведённых итераций: %d.%n", cycleCount);
                break;
            }

            if (player2win) {
                System.out.printf("Выиграл второй игрок! Количество произведённых итераций: %d.%n", cycleCount);
                break;
            }

            int cardPlayer1 = getCardFromPlayerDeck(PLAYER_1);
            int cardPlayer2 = getCardFromPlayerDeck(PLAYER_2);

            System.out.printf("Итерация №1 грок №1 карта: %s; игрок №2 карта: %s.%n", toString(cardPlayer1), toString(cardPlayer2));

            if (cardPlayer1 > cardPlayer2) {
                addCardToPlayerDeck(PLAYER_1, cardPlayer1);
                addCardToPlayerDeck(PLAYER_1, cardPlayer2);

                System.out.println("Выиграл игрок 1!");
            } else {
                addCardToPlayerDeck(PLAYER_2, cardPlayer1);
                addCardToPlayerDeck(PLAYER_2, cardPlayer2);

                System.out.println("Выиграл игрок 2!");
            }

            playersCardHeads[PLAYER_1] = incrementIndex(playersCardHeads[PLAYER_1]);
            playersCardHeads[PLAYER_2] = incrementIndex(playersCardHeads[PLAYER_2]);

            int player1cardsCount = playersCardHeads[PLAYER_1] - playersCardTails[PLAYER_1];
            int player2cardsCount = playersCardHeads[PLAYER_2] - playersCardTails[PLAYER_2];

            System.out.printf("У игрока №1 %d карт, у игрока №2 %d карт.%n", player1cardsCount, player2cardsCount);

            System.out.println(Arrays.toString(playersCards[PLAYER_1]));
            System.out.println(Arrays.toString(playersCards[PLAYER_2]));
            player1win = playerCardsIsEmpty(PLAYER_2);
            player2win = playerCardsIsEmpty(PLAYER_1);
        }
    }

    enum Suit {
        SPADES, // пики
        HEARTS, // червы
        CLUBS, // трефы
        DIAMONDS // бубны
    }

    enum Par {
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK, // Валет
        QUEEN, // Дама
        KING, // Король
        ACE // Туз
    }

    private static Suit getSuit(int cardNumber) {
        return Suit.values()[cardNumber / PARS_TOTAL_COUNT];
    }

    private static Par getPar(int cardNumber) {
        return Par.values()[cardNumber % PARS_TOTAL_COUNT];
    }

    private static boolean playerCardsIsEmpty(int playerIndex) {
        int tail = playersCardTails[playerIndex];
        int head = playersCardHeads[playerIndex];

        return tail == head;
    }

    private static void addCardToPlayerDeck(int player, int card) {
        playersCards[player][playersCardHeads[player] - 1] = card;
        playersCardHeads[player] = incrementIndex(playersCardHeads[player]);
    }

    private static int getCardFromPlayerDeck(int player) {
        int card = playersCards[player][playersCardTails[player]];
        playersCards[player][playersCardTails[player]] = 0;
        playersCardTails[player] = incrementIndex(playersCardTails[player]);

        return card;
    }

    private static int incrementIndex(int i) {
        return (i + 1) % CARDS_TOTAL_COUNT;
    }

    private static String toString(int cardNumber) {
        return getPar(cardNumber) + " " + getSuit(cardNumber);
    }
}
