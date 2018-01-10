package net.search.commons.searchEngines;


import net.search.commons.url.UrlLink;

import java.util.ArrayList;

interface Find {
    ArrayList<UrlLink> find(String requestName) throws Exception;
}
