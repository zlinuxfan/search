package net.search.commons.searchEngines;

import net.search.commons.utilities.Utilities;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class Google {
    private static final InetSocketAddress inetSocketAddress = new InetSocketAddress(
            "94.250.255.31",
            443
    );

    public ArrayList<String> find(String requestName) throws Exception {

        String url = "http://www.google.com.ua/search?q=" + requestName.replace(" ", "+");
        Document doc = Utilities.getDocument(url); //connectUrl(url);  //getDocument(url);
        ArrayList<String> whatOftenSearched = new ArrayList<>();

        Elements cardSection = doc.select(".card-section");

        for (Element section : cardSection) {
            Elements p_e4b = section.select("p._e4b");
            for (Element element : p_e4b) {
                whatOftenSearched.add(element.text());
            }
        }

        return whatOftenSearched;
    }


    public ArrayList<String> findYouTube(String requestName, int numberAds, int numberRequestInPage) throws Exception {
        String url = "http://www.google.com.ua/search?q=" + requestName.replace(" ", "+") + "site%3Ayoutube.com&num=" + numberRequestInPage ;

        Elements h3r;
        Document doc = Utilities.getDocument(url, inetSocketAddress); //connectUrl(url);  //getDocument(url);
        ArrayList<String> youTubeUrls = new ArrayList<>();

        h3r = doc.select("h3.r a");

        if (h3r.size() < 1) {
            return youTubeUrls;
        }

        for (int index = 1; index <= numberAds; index++) {
            youTubeUrls.add(h3r.get(0).select("a").first().attr("href"));
        }

        return youTubeUrls;
    }
}
