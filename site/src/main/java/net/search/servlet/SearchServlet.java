package net.search.servlet;

import net.search.commons.DAO.DaoImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        DaoImpl dao = new DaoImpl("jdbc:postgresql://194.87.219.8:5432/postgres", "search_site_user", "500");

        if (req.getHeader("id") == null) {
            resp.setHeader("id", String.valueOf(dao.isThere(req.getParameter("q"))));
        }
        req.getRequestDispatcher("search.jsp").forward(req, resp);

    }
}
