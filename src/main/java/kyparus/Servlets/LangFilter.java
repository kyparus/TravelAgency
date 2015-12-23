package kyparus.Servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by yurii on 04.12.15.
 */
@WebFilter(filterName = "LangFilter", urlPatterns = {"/uaua", "/enen"})
public class LangFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        String URI = request.getRequestURI();
        String page = (String) session.getAttribute("page");
        String locale = URI.contains("ua") ? "ua" : "en";
        synchronized (session) {
            session.setAttribute("locale", locale);
        }
        if (page != null)
            response.sendRedirect(page);
        else response.sendRedirect("/index.jsp");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
