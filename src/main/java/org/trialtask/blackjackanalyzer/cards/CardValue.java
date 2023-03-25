package org.trialtask.blackjackanalyzer.cards;

import java.util.Arrays;

public enum CardValue {
    V2, V3, V4, V5, V6, V7, V8, V9, V10, J, Q, K, A;

    public static CardValue parseValue(String value) {
        if (Character.isDigit(value.charAt(0))) {
            value = "V" + value;
        }

        if (Arrays.stream(values()).map(CardValue::name).toList().contains(value.toUpperCase())) {
            return CardValue.valueOf(value.toUpperCase());
        }

        return null;
    }
}
