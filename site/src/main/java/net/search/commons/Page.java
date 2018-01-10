package net.search.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.search.commons.url.*;

public class Page {
    private String pageName;
    private ArrayList<UrlLink> urlLinks = new ArrayList<>();
    private ArrayList<YouTube> youTubes = new ArrayList<>();
    private HashMap<String, Integer> whatOftenSearched = new HashMap<>();
    private boolean isFill = false;
    private int id;

    public Page() {
        // for servlet
    }

    public Page(String pageName, int id) {
        this.pageName = pageName;
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Page{" + "pageName='")
                .append(pageName)
                .append('\'')
                .append("whatOftenSearched=");

        for (Map.Entry<String, Integer> entry : whatOftenSearched.entrySet()) {
            stringBuilder
                    .append(entry.getValue())
                    .append(", ");
        }

        stringBuilder
                .append(", isFill=")
                .append(isFill)
                .append(", id=")
                .append(id)
                .append('}');

        return stringBuilder.toString();
    }

    public void setWhatOftenSearched(HashMap<String, Integer> whatOftenSearched) {
        this.whatOftenSearched = whatOftenSearched;
    }

    public String getPageName() {
        return pageName;
    }

    public int getId() {
        return id;
    }

    public ArrayList<YouTube> getYouTubes() {
        return youTubes;
    }

    public ArrayList<YouTube> getIdYouTubes() {
        return youTubes;
    }

    public void setYouTube(ArrayList<String> idYouTubes) {
        ArrayList<YouTube> youTubes = new ArrayList<>();

        for (String idYouTube : idYouTubes) {
            youTubes.add(new YouTube(idYouTube));
        }

        this.youTubes = youTubes;
    }

    public void setUrlLinks(ArrayList<UrlLink> urlLinks) {
        this.urlLinks = urlLinks;
    }

    public ArrayList<UrlLink> getUrlLinks() {
        return urlLinks;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<String, Integer> getWhatOftenSearched() {
        return whatOftenSearched;
    }
}

