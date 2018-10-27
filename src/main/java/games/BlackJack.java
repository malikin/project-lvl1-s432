package games;

import java.io.IOException;

import static games.Choice.getCharacterFromUser;

public class BlackJack {
    private static final int AI_PLAYER = 0;
    private static final int PLAYER = 1;
    private static final int MAX_VALUE = 21;
    private static final int MAX_CARDS_COUNT = 8;
    private static final int STEP_FUND = 10;

    private static int[] cards;
    private static int cursor;

    private static int[][] playersCards;
    private static int[] playersCursors;
    private static int[] playersMoney = {100, 100};

    public static void main() throws IOException {

        while(true) {
            if (playersMoney[AI_PLAYER] == 0 || playersMoney[PLAYER] == 0) {
                break;
            }

            initRound();

            System.out.printf("Вам выпала карта %s%n", CardUtils.toString(addCard2Player(PLAYER)));
            System.out.printf("Вам выпала карта %s%n", CardUtils.toString(addCard2Player(PLAYER)));

            while (sum(PLAYER) < 20) {
                if (confirm("Берём ещё?")){
                    System.out.printf("Вам выпала карта %s%n", CardUtils.toString(addCard2Player(PLAYER)));
                } else {
                    break;
                }
            }

            System.out.printf("Компьютеру выпала карта %s%n", CardUtils.toString(addCard2Player(AI_PLAYER)));
            System.out.printf("Компьютеру  выпала карта %s%n", CardUtils.toString(addCard2Player(AI_PLAYER)));

            while (sum(AI_PLAYER) < 17) {
                System.out.printf(
                        "Компьютер решил взять ещё и ему выпала карта %s%n",
                        CardUtils.toString(addCard2Player(AI_PLAYER))
                );
            }

            int sumPlayer = getFinalSum(PLAYER);
            int sumAiPlayer = getFinalSum(AI_PLAYER);

            System.out.printf("Сумма ваших очков - %d, компьютера - %d%n", sumPlayer, sumAiPlayer);

            if (sumPlayer == sumAiPlayer) {
                continue;
            }

            if (sumPlayer > sumAiPlayer) {
                playersMoney[PLAYER] += STEP_FUND;
                playersMoney[AI_PLAYER] -= STEP_FUND;
                System.out.printf("Вы выйграли раунд! Выигрыш %d$%n", STEP_FUND);
            } else {
                playersMoney[PLAYER] -= STEP_FUND;
                playersMoney[AI_PLAYER] += STEP_FUND;
                System.out.printf("Вы проиграли раунд! Теряете %d$%n", STEP_FUND);
            }
        }

        if (playersMoney[PLAYER] > 0)
            System.out.println("Вы выиграли! Поздравляем!");
        else
            System.out.println("Вы проиграли. Соболезнуем...");
    }

    private static void initRound() {
        System.out.println("\nУ Вас " + playersMoney[PLAYER] + "$, у компьютера - " + playersMoney[AI_PLAYER] + "$. Начинаем новый раунд!");
        cards = CardUtils.getShaffledCards();
        playersCards = new int[2][MAX_CARDS_COUNT];
        playersCursors = new int[]{0, 0};
        cursor = 0;
    }

    private static int addCard2Player(int player) {
        int card = cards[cursor];
        int playerCoursor = playersCursors[player];

        playersCards[player][playerCoursor] = card;

        cursor += 1;
        playersCursors[player] += 1;

        return card;
    }

    private static int sum(int player) {
        int sum = 0;
        int playerCursor = playersCursors[player];

        for(int i = 0; i < playerCursor; i++) {
            sum += value(playersCards[player][i]);
        }

        return sum;
    }

    private static int getFinalSum(int player) {
        int sum = sum(player);

        if (sum <= MAX_VALUE) {
            return sum;
        }

        return 0;
    }

    private static boolean confirm(String message) throws IOException {
        System.out.println(message + " \"Y\" - Да, {любой другой символ} - нет (Что бы выйти из игры, нажмите Ctrl + C)");
        switch (getCharacterFromUser()) {
            case 'Y':
            case 'y': return true;
            default: return false;
        }
    }

    private static int value(int card) {
        switch (CardUtils.getPar(card)) {
            case JACK: return 2;
            case QUEEN: return 3;
            case KING: return 4;
            case SIX: return 6;
            case SEVEN: return 7;
            case EIGHT: return 8;
            case NINE: return 9;
            case TEN: return 10;
            case ACE:
            default: return 11;
        }
    }
}
