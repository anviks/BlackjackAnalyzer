package org.trialtask.blackjackanalyzer;

/**
 * Runs the program.
 */
public class Main {
    public static void main(String[] args) {
        BlackjackParser parser = new BlackjackParser("./src/main/resources/game_data.txt");
        SessionAnalyzer analyzer = new SessionAnalyzer(parser.readFile());
        analyzer.checkSessions();
        parser.writeOutput("./analyzer_results.txt", analyzer.getInvalidEntries());
    }
}
