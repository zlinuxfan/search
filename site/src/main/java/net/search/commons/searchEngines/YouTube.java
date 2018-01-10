package net.search.commons.searchEngines;

import net.search.commons.utilities.Utilities;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class YouTube {


    public ArrayList<String> find(String requestName) throws Exception {
        String url = "https://www.youtube.com/results?search_query=" + requestName.replace(" ", "+");
        Document doc = Utilities.getDocument(url); //connectUrl(url);  //getDocument(url);
        ArrayList<String> keys = new ArrayList<>();

        Elements contents = doc.select("#img-preload");
        String[] split;

        for (Element section : contents) {
            Elements img = section.select("img");
            for (Element element : img) {
                split = (element.absUrl("src")).split("/");
                keys.add(split[4]);
            }
        }

        return keys;
    }
}
