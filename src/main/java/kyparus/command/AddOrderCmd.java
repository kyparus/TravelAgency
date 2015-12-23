package kyparus.command;

import kyparus.Tour.Tour;
import kyparus.TravelAgency;
import kyparus.user.Client;
import kyparus.user.DAO.ClientDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by yurii on 10.12.15.
 */
public class AddOrderCmd implements Command {

    private static ClientDAO clientDAO;

    @Override
    public String apply(HttpServletRequest request, HttpServletResponse response) {
        if (clientDAO == null)
            clientDAO = new ClientDAO(TravelAgency.getPool());


        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        Tour tour = (Tour) session.getAttribute("tour");
        String type = (String) session.getAttribute("tourType");

        TravelAgency.addOrder(client, tour, type);
        client.incUsageTimes();
        clientDAO.updateClient(client);
        logger.debug("Tour " + tour.getName() + " was ordered by " + client.getNickName());
        session.setAttribute("msg", "Tour booked successfully.");
        return "/client/tour.jsp";
    }
}
