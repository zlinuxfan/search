package net.search.commons.searchEngines;

import net.search.commons.utilities.Utilities;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import net.search.commons.url.UrlLink;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

public class Bing implements Find {
    private static final String NAME = "Bing";

    @Override
    public ArrayList<UrlLink> find(String requestName) throws IOException {
        String url = "http://www.bing.com/search?q=" + requestName.replace(" ", "+");
        ArrayList<UrlLink> urlLinks = new ArrayList<>();
        Document document = Utilities.getDocument(url);
        
        Elements lineResponse = document.select("li.b_algo");
        Elements h2 = lineResponse.select("h2");
        Elements descriptions = lineResponse.select(".b_caption");

        int index = 0;
        for (Element element : h2) {
            URL href;
            try {
                href = new URL(
                        URLDecoder.decode(
                                element.childNode(0).attributes().get("href"), "UTF-8").replace("'", "")
                );

                urlLinks.add(
                        new UrlLink(
                                href,
                                element.text().replace("\'", "").replace("'", ""),
                                descriptions.get(index++).select("p").text().replace("\'", "").replace("'", "")
                        )
                );
            } catch (MalformedURLException e) {
                System.out.println("for " + element.childNode(0).attributes().get("href"));
                e.printStackTrace();
            }
        }

        return urlLinks;
    }
}
