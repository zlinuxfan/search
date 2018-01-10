package net.search.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@WebServlet("/params")
public class GetParametersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param1 = req.getParameter("param1");
        int param2 = Integer.parseInt(req.getParameter("param2"));
        boolean param3 = Boolean.parseBoolean(req.getParameter("param3"));
        String[] param4 = req.getParameterValues("param4");
        // process parameters
        System.out.println();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("It is post params:");
        out.println("- " + req.getParameter("param1") + ";");
        out.println("- " + req.getParameter("param2") + ";");
        out.println("- " + req.getParameter("param3") + ";");
        out.println("- " + Arrays.toString(req.getParameterValues("param4")) + ";");
        out.println("</body></html>");
    }
}