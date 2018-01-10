package net.search.commons.page;

import net.search.commons.DAO.DaoImpl;
import net.search.commons.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PageDao {
    static Connection connection;
    static PreparedStatement preparedStatement;
    private static DaoImpl dao = new DaoImpl("jdbc:postgresql://194.87.219.8:5432/postgres", "search_site_user", "500");

    public static Page getPage(String requestName) {

        return createPage(
                dao.findRequestName(requestName),
                requestName
        );
    }

    public static Page getPage(int idRequestName, String requestName) {
        return createPage(
                idRequestName,
                requestName
        );
    }

    private static Page createPage(int idRequestName, String requestName) {
        Page page = null;
        if ( idRequestName > 0) {
            page = new Page(requestName, idRequestName);
            page.setWhatOftenSearched(dao.readWhatOftenSearched(idRequestName));
            page.setUrlLinks(dao.readUrlLinks(idRequestName));
            page.setYouTube(dao.readYouTubes(idRequestName));
        }

        return page;
    }
}
