package net.search.commons.DAO;


import net.search.commons.url.UrlLink;

import java.util.HashMap;
import java.util.List;


interface Dao {

    public HashMap<String, Integer> readEmptyRequestName();

    public boolean writeUrlLink(String requestName, UrlLink urlLink);

    public boolean writeUrlLink(int requestNameId, List<UrlLink> urlLinks);

    public boolean writeYouTube(int requestNameId, List<String> youTubes);
}
