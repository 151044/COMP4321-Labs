package hk.ust.comp4321.lab2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Crawler {
    private final Document doc;

    Crawler(String url) throws IOException {
        doc = Jsoup.connect(url).get();
    }

    public List<String> extractWords() {
        return List.of(doc.select("body").text().split(" "));
    }

    public List<String> extractLinks() {
        List<String> ret = new ArrayList<>();
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            System.out.println(link.attr("abs:href") + " (" + link.text() + ")");
            ret.add(link.attr("abs:href"));
        }
        return ret;
    }

    public static void main(String[] args) throws IOException {
        String url = "http://www.cs.ust.hk/~dlee/4321/";
        Crawler crawler = new Crawler(url);

        List<String> words = crawler.extractWords();

        System.out.println("Words in " + url + " (size = " + words.size() + ") :");
        for (int i = 0; i < words.size(); i++) {
			if (i < 5 || i > words.size() - 6) {
				System.out.println(words.get(i));
			} else if (i == 5) {
				System.out.println("...");
			}
		}
        System.out.println("\n\n");


        List<String> links = crawler.extractLinks();
        System.out.println("Links in " + url + ":");
        for (String link : links) {
            System.out.println(link);
        }
        System.out.println();
    }
}

	
