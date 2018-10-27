package games;

import org.apache.commons.math3.util.MathArrays;

import java.util.stream.IntStream;

public class CardUtils {
    public static final int PARS_TOTAL_COUNT = Par.values().length;
    public static final int CARDS_TOTAL_COUNT = PARS_TOTAL_COUNT * Suit.values().length;

    static int[] getShaffledCards() {
        int[] cards = IntStream.rangeClosed(0, CARDS_TOTAL_COUNT - 1).toArray();

        MathArrays.shuffle(cards);
        return cards;
    }

    public static Suit getSuit(int cardNumber) {
        return Suit.values()[cardNumber / PARS_TOTAL_COUNT];
    }

    public static Par getPar(int cardNumber) {
        return Par.values()[cardNumber % PARS_TOTAL_COUNT];
    }

    public static String toString(int cardNumber) {
        return getPar(cardNumber) + " " + getSuit(cardNumber);
    }
}
