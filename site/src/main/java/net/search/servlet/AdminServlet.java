package net.search.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        value = "/admin",
        initParams = {
                @WebInitParam(name = "ip", value = "127.0.0.3"),
                @WebInitParam(name = "ACCESS_KEY", value = "qwerty"),
                @WebInitParam(name = "login", value = "admin"),
                @WebInitParam(name="password", value = "123")
        }
)
public class AdminServlet extends HttpServlet {
    private String ip;
    private String accessKey;
    private String login;
    private String password;

    @Override
    public void init() {
        ip = getServletConfig().getInitParameter("ip");
        accessKey = getServletConfig().getInitParameter("ACCESS_KEY");
        login = getServletConfig().getInitParameter("login");
        password = getServletConfig().getInitParameter("password");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        req.setAttribute("error", "Login is required");

            PrintWriter out = resp.getWriter();
            if (req.getAttribute("error") != null) {
                out.println("<h5 style='color:red'>" + req.getAttribute("error") + "</h5>");
            }

            out.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (ip.equals(req.getRemoteAddr())) {
            System.out.println(String.format("Login via ip %s", req.getRemoteAddr()));
        } else if (login.equals(req.getParameter("login")) && password.equals(req.getParameter("password"))) {
            System.out.println(String.format("Login via login and password: %s/%s ", req.getParameter("login"), req.getParameter("password")));
        } else {
            System.out.println(String.format("Login via ip %s", req.getRemoteAddr()));
            System.out.println(String.format("Login via login and password: %s/%s ", req.getParameter("login"), req.getParameter("password")));
            doGet(req, resp);
        }
    }
}
