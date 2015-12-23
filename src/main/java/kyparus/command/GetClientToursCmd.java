package kyparus.command;

import kyparus.Tour.DAO.ExcursionTourDAO;
import kyparus.TravelAgency;
import kyparus.user.Client;
import kyparus.user.DAO.ClientDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by yurii on 10.12.15.
 */
public class GetClientToursCmd implements Command {

    @Override
    public String apply(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        session.setAttribute("tours",TravelAgency.getAllToursOfClient(client));
        if(session.getAttribute("client") != null){
            return "/client/homeClient.jsp";
        } else if(session.getAttribute("agent") != null) {
            return "/agent/homeAgent.jsp";
        } else {
            return "/errorPage.jsp";
        }


    }
}
