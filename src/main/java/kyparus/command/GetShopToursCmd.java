package kyparus.command;

import kyparus.Tour.DAO.RestTourDAO;
import kyparus.Tour.DAO.ShopTourDAO;
import kyparus.TravelAgency;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by yurii on 10.12.15.
 */
public class GetShopToursCmd implements Command {

    private ShopTourDAO shopTourDAO = null;


    @Override
    public String apply(HttpServletRequest request, HttpServletResponse response) {
        if (shopTourDAO == null)
            shopTourDAO = new ShopTourDAO(TravelAgency.getPool());

        HttpSession session = request.getSession();
        session.setAttribute("tours",shopTourDAO.getAllTours());
        session.setAttribute("tourType","shop");
        if(session.getAttribute("client") != null){
            return "/client/homeClient.jsp";
        } else if(session.getAttribute("agent") != null) {
            return "/agent/homeAgent.jsp";
        } else {
            return "/errorPage.jsp";
        }

    }
}
