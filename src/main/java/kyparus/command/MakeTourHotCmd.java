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
 * Created by yurii on 10.12.15.
 */
public class MakeTourHotCmd implements Command {
    private RestTourDAO restTourDAO = null;
    private ShopTourDAO shopTourDAO = null;
    private ExcursionTourDAO exTourDAO = null;

    @Override
    public String apply(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        Integer ID = Integer.parseInt(request.getParameter("tourID"));
        System.out.println("id " + ID  );
        String type = (String) session.getAttribute("tourType");
        //making tour hot and getting all tours
        System.out.println("type " + type);
        Tour tour;
        if (type.equals("rest")) {
            if (restTourDAO == null)
                restTourDAO = new RestTourDAO(TravelAgency.getPool());
            tour = restTourDAO.getTour(ID);
            assert tour != null;
            tour.setHot(!tour.isHot());
            restTourDAO.updateTour(tour);
            CommandFactory.executeCommand("get rest tours",request,response);
            logger.debug("RestTour with ID " + ID + " was marked as hot.");
        } else if (type.equals("shop")) {
            if (shopTourDAO == null)
                shopTourDAO = new ShopTourDAO(TravelAgency.getPool());
            tour = shopTourDAO.getTour(ID);
            assert tour != null;
            tour.setHot(!tour.isHot());
            shopTourDAO.updateTour(tour);
            CommandFactory.executeCommand("get shop tours", request, response);
            logger.debug("ShopTour with ID " + ID + " was marked as hot.");
        } else {
            if (exTourDAO == null)
                exTourDAO = new ExcursionTourDAO(TravelAgency.getPool());
            tour = exTourDAO.getTour(ID);
            assert tour != null;
            tour.setHot(!tour.isHot());
            exTourDAO.updateTour(tour);
            CommandFactory.executeCommand("get ex tours", request, response);
            logger.debug("ExTour with ID " + ID + " was marked as hot.");
        }
        return "/agent/homeAgent.jsp";
    }
}
