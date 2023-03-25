package org.trialtask.blackjackanalyzer;

import org.trialtask.blackjackanalyzer.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a hand of cards.
 */
public class Hand {
    private final List<Card> cards = new ArrayList<>();

    public Hand() {
    }

    public void add(Card card) {
        cards.add(card);
    }

    public int count(Card card) {
        return Collections.frequency(cards, card);
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getHandValue() {
        return cards.stream().filter(Objects::nonNull).mapToInt(Card::getValue).sum();
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + cards +
                '}';
    }
}