package kyparus.command;

import kyparus.Tour.DAO.ExcursionTourDAO;
import kyparus.Tour.DAO.RestTourDAO;
import kyparus.Tour.DAO.ShopTourDAO;
import kyparus.TravelAgency;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by yurii on 10.12.15.
 */
public class GetExToursCmd implements Command {
    private static ExcursionTourDAO exTourDAO = null;

    @Override
    public String apply(HttpServletRequest request, HttpServletResponse response) {
        if (exTourDAO == null)
            exTourDAO = new ExcursionTourDAO(TravelAgency.getPool());

        HttpSession session = request.getSession();
        session.setAttribute("tours",exTourDAO.getAllTours());
        session.setAttribute("tourType","ex");
        if(session.getAttribute("client") != null){
            return "/client/homeClient.jsp";
        } else if(session.getAttribute("agent") != null) {
            return "/agent/homeAgent.jsp";
        } else {
            return "/errorPage.jsp";
        }

    }
}
