package hk.ust.comp4321.lab2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * LinkExtractor extracts all the links from the given webpage
 * and prints them on standard output.
 */


public class LinkExtractor {
	private final Document doc;
	public LinkExtractor(String url) throws IOException {
		doc = Jsoup.connect(url).get();
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
	
    public static void main (String[] args) throws IOException {
        String url = "http://www.cs.ust.hk/~dlee/4321/";
        LinkExtractor extractor = new LinkExtractor(url);
        extractor.extractLinks();
    }
}
