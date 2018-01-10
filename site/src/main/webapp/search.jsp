<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" import="net.search.commons.*" %>
<%@ page import="net.search.commons.page.PageDao" %>
<%@ page import="net.search.commons.url.YouTube" %>
<%@ page import="net.search.commons.url.UrlLink" %>
<%@ page import="net.search.commons.searchEngines.Bing" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title><%=request.getParameter("q")%>
    </title>
    <link href="static/css/bootstrap.min.css" rel="stylesheet"/>
    <%--<link href="static/css/bootstrap-theme.min.css" rel="stylesheet"/>--%>
    <link href="static/css/font-awesome.css" rel="stylesheet"/>
    <link href="static/css/app.css" rel="stylesheet"/>
</head>
<body>

<%
    Page sitePage;
    if (response.getHeader("id").equals("0")) {
        Bing bing = new Bing();
        sitePage = new Page(request.getParameter("q"), -1);
        sitePage.setUrlLinks(bing.find(URLEncoder.encode(request.getParameter("q"), "UTF-8")));
    } else {
        sitePage = PageDao.getPage(
                Integer.parseInt(response.getHeader("id")),
                request.getParameter("q")
        );
    }
%>

<div class="wrapper container">
    <header>
        <form name="search" action="search" class="form-inline form-search">
            <div class="input-group">
                <label class="sr-only" for="searchInput"><%=request.getParameter("q")%>
                </label>
                <input class="form-control" id="searchInput" name="q" value="<%=request.getParameter("q")%>">
                <div class="input-group-btn">
                    <button class="btn btn-primary">Найти</button>
                </div>
            </div>
        </form>
    </header>

    <div class="row">
        <aside class="col-xs-12 col-sm-6 col-lg-8">
            <ul>
                <div class="adsbygoogles">
                    <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                    <ins class="adsbygoogle"
                         style="display:block"
                         data-ad-format="fluid"
                         data-ad-layout-key="-ga-29+35-3h+ay"
                         data-ad-client="ca-pub-4382890192627931"
                         data-ad-slot="3063128330"></ins>
                    <script>
                        (adsbygoogle = window.adsbygoogle || []).push({});
                    </script>
                </div>
                <%
                    if (sitePage != null) {
                        int counterUrlLink = 0;
                        for (UrlLink urlLink : sitePage.getUrlLinks()) {
                            if (counterUrlLink == 3) {
                %>
                <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                <ins class="adsbygoogle"
                     style="display:block"
                     data-ad-format="fluid"
                     data-ad-layout-key="-g7-29+35-3h+ay"
                     data-ad-client="ca-pub-4382890192627931"
                     data-ad-slot="1091694827"></ins>
                <script>
                    (adsbygoogle = window.adsbygoogle || []).push({});
                </script>
                <%
                } else if (counterUrlLink == 7) {
                %>
                <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
                <ins class="adsbygoogle"
                     style="display:block"
                     data-ad-format="fluid"
                     data-ad-layout-key="-gi-29+35-3h+ay"
                     data-ad-client="ca-pub-4382890192627931"
                     data-ad-slot="5330322734"></ins>
                <script>
                    (adsbygoogle = window.adsbygoogle || []).push({});
                </script>
                <%
                    }
                %>
                <li>
                    <h5><a href="<%=urlLink.getUrl()%>"><%=urlLink.getUrlName()%>
                    </a></h5>
                    <p><%=urlLink.getDescription()%>
                    </p>
                    <p class="link"><%=urlLink.getUrl()%>
                    </p>
                </li>
                <%
                            counterUrlLink++;
                        }
                    }
                %>
            </ul>
            <%
                if (sitePage != null) {
                    int counter = sitePage.getWhatOftenSearched().size();
                    if (counter > 0) {
                        counter /= 2;
            %>
            <br>
            <h5>Поиски, связанные с запросом <%=request.getParameter("q")%>
            </h5>
            <div class="row">
                <div class="col-md-6">
                    <%
                        for (Map.Entry<String, Integer> entry : sitePage.getWhatOftenSearched().entrySet()) {
                    %>
                    <ul>
                        <h6><a href="search?q=<%=(entry.getKey()).replace(" ", "+")%>"><%=entry.getKey()%>
                        </a></h6>
                    </ul>
                    <%
                        if (--counter < 1) {
                            counter = sitePage.getWhatOftenSearched().size() / 2;
                    %>
                </div>
                <div class="col-md-6">
                    <%
                            }
                        }
                    %>
                </div>
            </div>
            <%
                    }
                }
            %>
        </aside>
        <main class="col-xs-6 col-lg-4">
            <%
                if (sitePage != null) {
                    for (YouTube youTube : sitePage.getIdYouTubes()) {
            %>
            <div class="embed-responsive embed-responsive-21by9">
                <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/<%=youTube.getId()%>?rel=0"
                        allowfullscreen></iframe>
            </div>
            <br>
            <%
                    }
                }
            %>
        </main>
    </div>

    <footer class="footer">
        <div class="container">
            <p class="text-center">
                <a href="#" class="badge badge-light">О компании</a> -
                <a href="#" class="badge badge-light">Авторское право</a> -
                <a href="#" class="badge badge-light">Отказ от ответственности</a> -
                <a href="#" class="badge badge-light">Конфиденциальность</a> -
                <a href="#" class="badge badge-light">Контакты</a>
            </p>
        </div>
        <div class="container">
            <p class="text-center year">&#9400; 2017</p>
        </div>
    </footer>
</div>
<script src="static/js/jquery.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/app.js"></script>
</body>
</html>
