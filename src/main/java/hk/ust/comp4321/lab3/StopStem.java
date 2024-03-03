package hk.ust.comp4321.lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public class StopStem {
    private Set<String> stopWords;

    public boolean isStopWord(String str) {
        return stopWords.contains(str);
    }

    public StopStem(String str) throws IOException {
        stopWords = Files.lines(Path.of(str)).collect(Collectors.toSet());
    }

    public static void main(String[] arg) throws IOException {
        StopStem stopStem = new StopStem("stopwords.txt");
        String input = "";
        try {
            do {
                System.out.print("Please enter a single English word: ");
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                input = in.readLine();
                if (!input.isEmpty()) {
                    if (stopStem.isStopWord(input)) {
                        System.out.println("It should be stopped");
                    } else {
                        System.out.println("The stem of it is \"" + NltkPorter.stem(input) + "\"");
                    }
                }
            }
            while (!input.isEmpty());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
