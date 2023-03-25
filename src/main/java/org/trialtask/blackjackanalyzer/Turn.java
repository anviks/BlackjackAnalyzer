package org.trialtask.blackjackanalyzer;

import org.trialtask.blackjackanalyzer.cards.Card;

import java.util.List;

/**
 * A class representing one blackjack turn.
 */
public class Turn {
    private long timestamp;
    private long sessionID;
    private long playerID;
    private String action;
    private Hand dealerHand;
    private Hand playerHand;


    public Turn(long timestamp, long sessionID, long playerID, String action, Hand dealerHand, Hand playerHand) {
        this.timestamp = timestamp;
        this.sessionID = sessionID;
        this.playerID = playerID;
        this.action = action;
        this.dealerHand = dealerHand;
        this.playerHand = playerHand;
    }

    /**
     * Checks if any of the following conditions are true:
     * <ul>
     *     <li>Dealer has more than one cards facing down</li>
     *     <li>Dealer has cards worth less than 17 points, the player hasn't gone bust, but the game is over</li>
     *     <li>Dealer hits despite having cards worth 17 points or more</li>
     *     <li>Dealer has a face-up card that has an invalid suit or value</li>
     *     <li>Dealer has gone bust, but player didn't win</li>
     *     <li>Dealer won despite having a hand that is equal or worth less than player's hand</li>
     * </ul>
     * If any of the listed conditions turned out to be true, that means a fault has occurred and false is returned.
     *
     * @return false if any blackjack rules have been violated.
     */
    public boolean isValidDealerHand() {
        if (dealerHand.count(null) > 1) {
            return false;
        }

        if (dealerHand.getHandValue() < 17 && playerHand.getHandValue() <= 21
                && (action.contains("Win") || action.contains("Lose"))) {
            return false;
        }

        if (dealerHand.getHandValue() >= 17 && action.equals("D Hit")) {
            return false;
        }

        if (dealerHand.getCards().stream().anyMatch(
                card -> card != null && (card.getCardValue() == null || card.getSuit() == null)
        )) {
            return false;
        }

        if (dealerHand.getHandValue() > 21 && !action.equals("P Win")) {
            return false;
        }

        if (action.equals("D Win") && playerHand.getHandValue() >= dealerHand.getHandValue()) {
            return false;
        }

        return true;
    }

    /**
     * Checks if any of the following conditions are true:
     * <ul>
     *     <li>Player has a face-down card</li>
     *     <li>Player has a card with an invalid suit or value</li>
     *     <li>Player has gone bust, but dealer hasn't won</li>
     *     <li>Player has won despite having a hand worth less than the dealer's</li>
     *     <li>Player has the ability to hit with 100% chance of going bust</li>
     * </ul>
     * If any of the listed conditions turned out to be true, that means a fault has occurred and false is returned.
     *
     * @return false if any blackjack rules have been violated.
     */
    public boolean isValidPlayerHand() {
        if (playerHand.count(null) != 0) {
            return false;
        }

        if (playerHand.getCards().stream().anyMatch(card -> card.getCardValue() == null || card.getSuit() == null)) {
            return false;
        }

        if (playerHand.getHandValue() > 21 && !action.equals("D Win")) {
            return false;
        }

        if (action.equals("P Win") && playerHand.getHandValue() < dealerHand.getHandValue()) {
            return false;
        }

        if (action.equals("P Hit") && playerHand.getHandValue() >= 20) {
            return false;
        }

        return true;
    }

    /**
     * Checks if there are two of the same card on the table.
     *
     * @return false if there's a duplicate card, true otherwise.
     */
    public boolean areValidHands() {
        List<Card> tempCards = dealerHand.getCards();
        tempCards.addAll(playerHand.getCards());
        return tempCards.size() == tempCards.stream().distinct().toList().size();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getSessionID() {
        return sessionID;
    }

    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long playerID) {
        this.playerID = playerID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    @Override
    public String toString() {
        return "Turn{" +
                "timestamp=" + timestamp +
                ", sessionID=" + sessionID +
                ", playerID=" + playerID +
                ", action='" + action + '\'' +
                ", dealerHand=" + dealerHand +
                ", playerHand=" + playerHand +
                '}';
    }
}
