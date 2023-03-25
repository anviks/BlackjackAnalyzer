package org.trialtask.blackjackanalyzer;

import org.trialtask.blackjackanalyzer.cards.Card;
import org.trialtask.blackjackanalyzer.cards.CardSuit;
import org.trialtask.blackjackanalyzer.cards.CardValue;

import java.io.*;
import java.util.*;
import java.util.function.Function;

public class BlackjackParser {
    private final String fileName;
    private final List<String> fileContent = new ArrayList<>();

    public BlackjackParser(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Reads an input file into a list of {@link Turn} objects.
     *
     * @return a list of {@link Turn} objects.
     */
    public List<Turn> readFile() {
        List<Turn> turns = new ArrayList<>();
        File file = new File(fileName);
        Scanner reader;

        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            fileContent.add(line);
            String[] data = line.split(",");
            if (data.length < 6) {
                continue;
            }

            Function<String, Boolean> isNumber = s -> s.chars().allMatch(Character::isDigit);

            if (!isNumber.apply(data[0])
                    || !isNumber.apply(data[1])
                    || !isNumber.apply(data[2])) {
                continue;
            }

            long timestamp = Long.parseLong(data[0]);
            long sessionID = Long.parseLong(data[1]);
            long playerID = Long.parseLong(data[2]);
            String action = data[3];
            String[] dealerCards = data[4].split("-");
            String[] playerCards = data[5].split("-");
            Hand dealerHand = new Hand();
            Hand playerHand = new Hand();

            for (String dealerCardString : dealerCards) {
                addCardToHand(dealerHand, dealerCardString);
            }

            for (String playerCardString : playerCards) {
                addCardToHand(playerHand, playerCardString);
            }

            Turn turn = new Turn(timestamp, sessionID, playerID, action, dealerHand, playerHand);
            turns.add(turn);
        }

        return turns;
    }

    private void addCardToHand(Hand hand, String cardString) {
        if (cardString.equals("?")) {
            hand.add(null);
            return;
        }
        char suitChar = cardString.charAt(cardString.length() - 1);
        String valueString = cardString.substring(0, cardString.length() - 1);
        CardSuit suit = CardSuit.parseSuit(suitChar);
        CardValue value = CardValue.parseValue(valueString);
        hand.add(new Card(suit, value));
    }

    /**
     * Writes the turn of a session, where the first fault was detected, to a file.
     *
     * @param filePath       the path of the file to write faults to
     * @param invalidEntries the beginning parts of faulty turns
     */
    public void writeOutput(String filePath, List<String> invalidEntries) {
        List<String> output = new ArrayList<>();

        for (String entryBeginning : invalidEntries) {
            for (String entry : fileContent) {
                if (entry.split(",")[0].equals(entryBeginning.split(",")[0])
                        && entry.split(",")[1].equals(entryBeginning.split(",")[1])) {
                    output.add(entry);
                    break;
                }
            }
        }

        File file = new File(filePath);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(String.join("\n", output));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
