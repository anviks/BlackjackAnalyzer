package org.trialtask.blackjackanalyzer.cards;

import java.util.Arrays;

public enum CardSuit {
    CLUBS, DIAMONDS, HEARTS, SPADES;

    public static CardSuit parseSuit(char suit) {
        return Arrays.stream(values()).filter(
                cardSuit -> cardSuit.name().charAt(0) == Character.toUpperCase(suit)
        ).findFirst().orElse(null);
    }
}
