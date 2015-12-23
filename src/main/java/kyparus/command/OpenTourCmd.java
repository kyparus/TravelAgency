package kyparus.command;

import kyparus.Tour.DAO.ExcursionTourDAO;
import kyparus.Tour.DAO.RestTourDAO;
import kyparus.Tour.DAO.ShopTourDAO;
import kyparus.Tour.ExcursionTour;
import kyparus.Tour.RestTour;
import kyparus.Tour.ShoppingTour;
import kyparus.Tour.Tour;
import kyparus.TravelAgency;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;

/**
 * Created by yurii on 12.12.15.
 */
public class OpenTourCmd implements Command {
    private RestTourDAO restTourDAO = null;
    private ShopTourDAO shopTourDAO = null;
    private ExcursionTourDAO exTourDAO = null;
    @Override
    public String apply(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Integer ID = Integer.parseInt(request.getParameter("tourID"));
        logger.debug("Opening tour with id " + ID);

        String type = (String) session.getAttribute("tourType");

        Tour tour;
        if (type.equals("rest")) {
            if (restTourDAO == null)
                restTourDAO = new RestTourDAO(TravelAgency.getPool());
            tour = restTourDAO.getTour(ID);

        } else if (type.equals("shop")) {
            if (shopTourDAO == null)
                shopTourDAO = new ShopTourDAO(TravelAgency.getPool());
            tour = shopTourDAO.getTour(ID);

        } else {
            if (exTourDAO == null)
                exTourDAO = new ExcursionTourDAO(TravelAgency.getPool());
            tour = exTourDAO.getTour(ID);

        }
        session.setAttribute("tour",tour);
        logger.debug("Forwarding to /client/tour.jsp");
        return "/client/tour.jsp";
    }
}
