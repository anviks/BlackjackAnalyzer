package org.trialtask.blackjackanalyzer.hands;

import java.util.ArrayList;
import java.util.List;

public abstract class Hand {
    private List<Card> cards = new ArrayList<>();

    public Hand() {
    }

    public Hand(Card... cards) {
        this.cards.addAll(List.of(cards));
    }
}