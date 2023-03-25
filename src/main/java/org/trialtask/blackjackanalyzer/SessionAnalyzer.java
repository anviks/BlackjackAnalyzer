package org.trialtask.blackjackanalyzer;

import java.util.*;

public class SessionAnalyzer {
    private final List<Turn> turns;
    private final List<String> invalidEntries = new ArrayList<>();
    private final List<Long> invalidSessions = new ArrayList<>();
    private boolean playerStand = false;

    public SessionAnalyzer(List<Turn> turns) {
        turns.sort(Comparator.comparingLong(Turn::getSessionID).thenComparingLong(Turn::getTimestamp));
        this.turns = turns;
    }

    public List<Turn> getTurns() {
        return turns;
    }

    public List<String> getInvalidEntries() {
        return invalidEntries;
    }

    public List<Long> getInvalidSessions() {
        return invalidSessions;
    }

    /**
     * Checks for a fault in all the sessions in a file. If a fault is found, the information is added to class variables.
     */
    public void checkSessions() {
        for (Turn turn : turns) {
            String action = turn.getAction();

            if (invalidSessions.contains(turn.getSessionID())) {
                continue;
            }

            if (action.equals("P Stand")) {
                playerStand = true;
            }

            if (action.equals("P Joined") || action.equals("D Redeal")) {
                playerStand = false;
            }

            if (action.equals("P Hit") && playerStand) {
                registerFault(turn);
            }

            if (!turn.isValidDealerHand()
                    || !turn.isValidPlayerHand()
                    || !turn.areValidHands()) {
                registerFault(turn);
            }
        }
    }

    private void registerFault(Turn turn) {
        invalidSessions.add(turn.getSessionID());
        invalidEntries.add(turn.getTimestamp() + "," + turn.getSessionID());
    }
}
