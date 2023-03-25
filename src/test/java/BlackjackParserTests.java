import org.junit.jupiter.api.Test;
import org.trialtask.blackjackanalyzer.BlackjackParser;
import org.trialtask.blackjackanalyzer.SessionAnalyzer;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlackjackParserTests {

    @Test
    void gameData0() {
        String source = "game_data_0.txt";
        String expectedOutput = "analyzer_output_0.txt";

        compareOutputFiles(source, expectedOutput);
    }

    @Test
    void gameData1() {
        String source = "game_data_1.txt";
        String expectedOutput = "analyzer_output_1.txt";

        compareOutputFiles(source, expectedOutput);
    }

    @Test
    void gameData2() {
        String source = "game_data_2.txt";
        String expectedOutput = "analyzer_output_2.txt";

        compareOutputFiles(source, expectedOutput);
    }

    private static void compareOutputFiles(String source, String output) {
        BlackjackParser parser = new BlackjackParser("./src/test/resources/" + source);
        SessionAnalyzer analyzer = new SessionAnalyzer(parser.readFile());
        analyzer.checkSessions();
        parser.writeOutput("./src/test/analyzer_results.txt", analyzer.getInvalidEntries());
        File expectedFile = new File("./src/test/resources/" + output);
        File actualFile = new File("./src/test/analyzer_results.txt");

        try (Scanner expectedReader = new Scanner(expectedFile);
             Scanner actualReader = new Scanner(actualFile)) {

            StringBuilder expected = new StringBuilder();
            StringBuilder actual = new StringBuilder();

            while (expectedReader.hasNextLine()) {
                expected.append(expectedReader.nextLine());
            }

            while (actualReader.hasNextLine()) {
                actual.append(actualReader.nextLine());
            }

            assertEquals(expected.toString(), actual.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
