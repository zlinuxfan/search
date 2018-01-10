package net.search.commons.url;

import java.net.URL;

public class YouTube {
    private URL url;
    private String id;
    private String urlName;
    private String description;

    public YouTube(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
