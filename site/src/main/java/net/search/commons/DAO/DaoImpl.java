package net.search.commons.DAO;


import net.search.commons.url.UrlLink;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class DaoImpl implements Dao {
    private String host;
    private String user;
    private String password;

    private Statement statement = null;

    private static final String TABLE_MAIN = "main";
    private static final String TABLE_MAIN_FIELD_REQUEST_NAME = "request_name";
    private static final String TABLE_FIELD_ID = "id";
    private static final String TABLE_FIELD_IS_URL_LINKS = "is_url_links";
    private static final String TABLE_FIELD_IS_WHAT_OFTEN_SEACHED = "is_what_often_searched";
    private static final String TABLE_FIELD_IS_YOUTUBE = "is_youtube";

    private static final String TABLE_WHAT_OFTEN_SEARCHED = "what_often_searched";
    private static final String TABLE_FIELD_MAIN_ID = "main_id";
    private static final String TABLE_FIELD_PHRASE = "phrase";
    private static final String TABLE_FIELD_OWN_ID = "own_id";

    private static final String TABLE_URL_LINKS = "url_links";
    private static final String TABLE_FIELD_LINK_NAME = "link_name";
    private static final String TABLE_FIELD_LINK = "link";
    private static final String TABLE_FIELD_LINK_DESCRIPTION = "link_description";

    private static final String TABLE_YOUTUBE = "youtube";
    private static final String TABLE_FIELD_KEY = "key";

    public DaoImpl(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    @Override
    public HashMap<String, Integer> readEmptyRequestName() {

        HashMap<String, Integer> emptyRequestName = new HashMap<>();
        ResultSet result = selectIn(
                String.format("SELECT %s, %s FROM %s WHERE is_filled IS FALSE;",
                        TABLE_FIELD_ID,
                        TABLE_MAIN_FIELD_REQUEST_NAME,
                        TABLE_MAIN)
        );

        if (result == null) {
            return emptyRequestName;
        }

        try {
            if (result.isBeforeFirst()) {
                while (result.next()) {
                    emptyRequestName.put(
                            hexToString(result.getString(TABLE_MAIN_FIELD_REQUEST_NAME).replace("\\x", "")),
                            result.getInt(TABLE_FIELD_ID)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return emptyRequestName;
    }

    public int findRequestName(String requestName) {
        int id = 0;
        ResultSet result = selectIn(
                String.format("SELECT %s FROM %s WHERE  %s = '%s' AND %s AND %s AND %s;",
                        TABLE_FIELD_ID,
                        TABLE_MAIN,
                        TABLE_MAIN_FIELD_REQUEST_NAME,
                        requestName,
                        TABLE_FIELD_IS_URL_LINKS,
                        TABLE_FIELD_IS_WHAT_OFTEN_SEACHED,
                        TABLE_FIELD_IS_YOUTUBE
                )
        );

        if (result == null) {
            return id;
        }

        try {
            if (result.isBeforeFirst()) {
                result.next();
                id = result.getInt(TABLE_FIELD_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }

    public ArrayList<String> readYouTubes(int idRequestName) {
        ArrayList<String> idYouTubes = new ArrayList<>();
        ResultSet result = selectIn(
                String.format("SELECT %s FROM %s WHERE %s=%s;",
                        TABLE_FIELD_KEY,
                        TABLE_YOUTUBE,
                        TABLE_FIELD_MAIN_ID,
                        idRequestName
                )
        );

        if (result == null) {
            return null;
        }

        try {
            if (result.isBeforeFirst()) {
                while (result.next()) {
                    idYouTubes.add(result.getString(TABLE_FIELD_KEY));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return idYouTubes;
    }

    public ArrayList<UrlLink> readUrlLinks(int idRequestName) {
        ArrayList<UrlLink> urlLinks = new ArrayList<>();
        ResultSet result = selectIn(
                String.format("SELECT %s, %s, %s FROM %s WHERE %s = %s;",
                        TABLE_FIELD_LINK_NAME,
                        TABLE_FIELD_LINK,
                        TABLE_FIELD_LINK_DESCRIPTION,
                        TABLE_URL_LINKS,
                        TABLE_FIELD_MAIN_ID,
                        idRequestName
                )
        );

        if (result == null) {
            return null;
        }

        try {
            if (result.isBeforeFirst()) {
                while (result.next()) {
                    urlLinks.add(new UrlLink(
                            new URL(result.getString(TABLE_FIELD_LINK)),
                            result.getString(TABLE_FIELD_LINK_NAME),
                            result.getString(TABLE_FIELD_LINK_DESCRIPTION)
                            ));
                }
            }
        } catch (SQLException | MalformedURLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return urlLinks;
    }

    public int isThere(String requestName) {
        int answer = 0;
        ResultSet result = selectIn(
                String.format("SELECT %s, %s FROM %s WHERE  %s = '%s';",
                        TABLE_FIELD_ID,
                        TABLE_MAIN_FIELD_REQUEST_NAME,
                        TABLE_MAIN,
                        TABLE_MAIN_FIELD_REQUEST_NAME,
                        requestName)
        );

        if (result == null) {
            return answer;
        }

        try {
            if (result.isBeforeFirst()) {
                result.next();
                answer = result.getInt(TABLE_FIELD_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return answer;
    }

    public HashMap<String, Integer> readEmptyRequestName(String withField) {
        HashMap<String, Integer> emptyRequestName = new HashMap<>();

         ResultSet result = selectIn(
                String.format("SELECT %s, %s FROM %s WHERE is_filled IS FALSE AND %s IS FALSE;",
                        TABLE_FIELD_ID,
                        TABLE_MAIN_FIELD_REQUEST_NAME,
                        TABLE_MAIN,
                        withField)
        );

        if (result == null) {
            return emptyRequestName;
        }

        try {
            if (result.isBeforeFirst()) {
                while (result.next()) {
                    emptyRequestName.put(
                            hexToString(result.getString(TABLE_MAIN_FIELD_REQUEST_NAME).replace("\\x", "")),
                            result.getInt(TABLE_FIELD_ID)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return emptyRequestName;
    }

    private ResultSet selectIn(String query) {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(host, user, password);
            statement = connection.createStatement();
            return statement.executeQuery(query);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ResultSet selectIn1(String query) {
        HashMap<String, Integer> emptyRequestName = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(host, user, password);
            statement = connection.createStatement();
            result = statement.executeQuery(query);


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
//            if (statement != null) {
//                try {
//                    statement.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public HashMap<String, Integer> readWhatOftenSearched(int idRequestName) {
        HashMap<String, Integer> whatOftenSearched = new HashMap<>();
        ResultSet result = selectIn(
                "SELECT id,  request_name FROM main WHERE id IN (SELECT own_id FROM what_often_searched WHERE main_id="+idRequestName+");"
        );

        try {
            if (result.isBeforeFirst()) {
                while (result.next()) {
                    whatOftenSearched.put(
                            hexToString(result.getString(TABLE_MAIN_FIELD_REQUEST_NAME).replace("\\x", "")),
                            result.getInt(TABLE_FIELD_ID)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return whatOftenSearched;
    }

    public boolean writeRequestName(String requestName) {
        return insertInto(
                String.format("INSERT INTO %s (%s) VALUES ('%s');",
                        TABLE_MAIN,
                        TABLE_MAIN_FIELD_REQUEST_NAME,
                        requestName)
        );
    }

    public boolean writeRequestName(ArrayList<String> requestNames) {
        if (requestNames.size() == 0) {
            return true;
        }

        String request = String.format("INSERT INTO %s (%s) VALUES ",
                TABLE_MAIN,
                TABLE_MAIN_FIELD_REQUEST_NAME
        );

        StringBuilder builder = new StringBuilder();
        builder.append(request);

        for (String requestName : requestNames) {
            builder
                    .append("('\\\\x")
                    .append(requestName)
                    .append("'), ");
        }

        // delete delete an redundant comma
        builder
                .delete(builder.length() - 2, builder.length() - 1)
                .append(";");
        System.out.println(builder);
        return insertInto(builder.toString());
    }

    public boolean writeWhatOftenSearched(int main_id, int own_id) {

        return insertInto(String.format("INSERT INTO %s (%s, %s) VALUES (%s, %s)",
                TABLE_WHAT_OFTEN_SEARCHED,
                TABLE_FIELD_MAIN_ID,
                TABLE_FIELD_OWN_ID,
                main_id,
                own_id
        )) && update(
                String.format("UPDATE %s SET is_what_often_searched=true WHERE id=%s;",
                        TABLE_MAIN,
                        main_id)
        );
    }

    public boolean writeWhatOftenSearched(int main_id, ArrayList<String> phrases) {
        if (phrases.size() == 0) {
            return true;
        }
        String request = String.format("INSERT INTO %s (%s, %s) VALUES ",
                TABLE_WHAT_OFTEN_SEARCHED,
                TABLE_FIELD_MAIN_ID,
                TABLE_FIELD_PHRASE
        );

        StringBuilder builder = new StringBuilder();
        builder.append(request);

        for (String phrase : phrases) {
            builder
                    .append("(")
                    .append(main_id)
                    .append(", \'")
                    .append(phrase)
                    .append("\'), ");
        }

        // delete delete an redundant comma
        builder
                .delete(builder.length() - 2, builder.length() - 1)
                .append(";");

        return insertInto(builder.toString()) && update(
                String.format("UPDATE %s SET is_what_often_searched=true WHERE id=%s;",
                        TABLE_MAIN,
                        main_id)
        );
    }

    private boolean update(String request) {
        return insertInto(request);
    }

    private boolean insertInto(String query) {
        Connection connection = null;
        PreparedStatement statement = null;
        int numberOfInsert = 0;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(host, user, password);
            System.out.println(query);
            statement = connection.prepareStatement(query);
            numberOfInsert = statement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("-> " + query);
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("error: statement close.");
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("error: connection close.");
                    e.printStackTrace();
                }
            }
        }
        return numberOfInsert > 0;
    }

    @Override
    public boolean writeUrlLink(String requestName, UrlLink urlLink) {
        return false;
    }

    @Override
    public boolean writeUrlLink(int requestNameId, List<UrlLink> urlLinks) {
        if (urlLinks.size() == 0) {
            return false;
        }

        String request = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES ",
                TABLE_URL_LINKS,
                TABLE_FIELD_MAIN_ID,
                TABLE_FIELD_LINK_NAME,
                TABLE_FIELD_LINK,
                TABLE_FIELD_LINK_DESCRIPTION
        );

        StringBuilder builder = new StringBuilder();
        builder.append(request);

        for (UrlLink urlLink : urlLinks) {
            builder
                    .append("(\'")
                    .append(requestNameId)
                    .append("\', \'")
                    .append(urlLink.getUrlName())
                    .append("\', \'")
                    .append(urlLink.getUrl())
                    .append("\', \'")
                    .append(urlLink.getDescription())
                    .append("\'), ");
        }

        // delete delete an redundant comma
        builder
                .delete(builder.length() - 2, builder.length() - 1)
                .append(";");

        return insertInto(builder.toString()) && update(
                String.format("UPDATE %s SET is_url_links=true WHERE id=%s;",
                        TABLE_MAIN,
                        requestNameId)
        );
    }

    @Override
    public boolean writeYouTube(int requestNameId, List<String> youTubes) {
        if (youTubes.size() == 0) {
            return false;
        }

        String request = String.format("INSERT INTO %s (%s, %s) VALUES ",
                TABLE_YOUTUBE,
                TABLE_FIELD_MAIN_ID,
                TABLE_FIELD_KEY
        );

        StringBuilder builder = new StringBuilder();
        builder.append(request);

        for (String youtube : youTubes) {
            builder
                    .append("(")
                    .append(requestNameId)
                    .append(", \'")
                    .append(youtube)
                    .append("\'), ");
        }

        // delete delete an redundant comma
        builder
                .delete(builder.length() - 2, builder.length() - 1)
                .append(";");

        return insertInto(builder.toString()) && update(
                String.format("UPDATE %s SET is_youtube=true WHERE id=%s;",
                        TABLE_MAIN,
                        requestNameId)
        );
    }

    private static String hexToString(String hex) {
        byte[] bytes;
        String answer = null;
        try {
            bytes = Hex.decodeHex(hex.toCharArray());
            answer = new String(bytes, "UTF-8");
        } catch (DecoderException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public static void main(String[] args) throws SQLException {
        DaoImpl dao = new DaoImpl("jdbc:postgresql://194.87.219.8:5432/postgres", "search_site_user", "500");

        ResultSet resultSet = dao.selectIn1("SELECT id FROM public.youtube limit 3;");
//        ResultSet resultSet = dao.selectIn1("SELECT id FROM main WHERE is_youtube limit 3");

        assert resultSet != null;

        if (resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(TABLE_FIELD_ID));
            }
        }
    }
}
