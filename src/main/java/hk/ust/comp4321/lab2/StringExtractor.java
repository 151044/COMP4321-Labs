package hk.ust.comp4321.lab2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Extract plaintext strings from a web page.
 * Illustrative program to gather the textual contents of a web page.
 */
public class StringExtractor {
    private final Document doc;

    /**
     * Construct a hk.ust.comp4321.lab2.StringExtractor to read from the given resource.
     *
     * @param resource Either a URL or a file name.
     */
    public StringExtractor(String resource) throws IOException {
        doc = Jsoup.connect(resource).get();
    }

    /**
     * Extract the text from a page.
     *
     * @return The textual contents of the page.
     */
    public String extractStrings() {
        return doc.select("body").text();
    }

    /**
     * @param args The command line arguments.
     */
    public static void main(String[] args) throws IOException {
        String url = "http://www.cs.ust.hk/~dlee/4321/";
        StringExtractor se;
        se = new StringExtractor(url);
        System.out.println(se.extractStrings());
    }
}