package kyparus.Servlets;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yurii on 04.12.15.
 */
@javax.servlet.annotation.WebServlet(name = "indexServlet", urlPatterns = {"/logOrReg"})
public class indexServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(indexServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.debug("Index page opened.");
        String option1 = request.getParameter("submitLogin");
        String option2 = request.getParameter("submitRegister");
        String url;
        if (option1 != null) {
            url = "/login.jsp";
            logger.debug("Forward to login page.");
            System.out.println("log " + option1);
        } else if (option2 != null) {
            url = "/signUp.jsp";
            logger.debug("Forward to register page.");
        } else url = "/index.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
