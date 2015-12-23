package kyparus.command;

import kyparus.Tour.DAO.RestTourDAO;
import kyparus.TravelAgency;
import kyparus.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by yurii on 10.12.15.
 */
public class GetRestToursCmd implements Command {

    private RestTourDAO restTourDAO = null;



    @Override
    public String apply(HttpServletRequest request, HttpServletResponse response) {
        if (restTourDAO == null)
            restTourDAO = new RestTourDAO(TravelAgency.getPool());

        HttpSession session = request.getSession();
        session.setAttribute("tours",restTourDAO.getAllTours());
        session.setAttribute("tourType","rest");
        if(session.getAttribute("client") != null){
            return "/client/homeClient.jsp";
        } else if(session.getAttribute("agent") != null) {
             return "/agent/homeAgent.jsp";
         } else {
            return "/errorPage.jsp";
        }

    }
}
