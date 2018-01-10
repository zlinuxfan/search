package net.search.commons.url;


import java.net.URL;

public class UrlLink {
    private URL url;
    private String urlName;
    private String description;

    public UrlLink(URL url, String urlName, String description) {
        this.url = url;
        this.description = description.replace("\'", "").replace("\\", "");
        this.urlName = urlName.replace("\'", "").replace("\'", "");
    }

    public String getUrlName() {
        return urlName;
    }

    public URL getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }
}
