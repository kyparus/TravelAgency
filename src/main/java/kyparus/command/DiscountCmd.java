package kyparus.command;

import kyparus.TravelAgency;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yurii on 12.12.15.
 */
public class DiscountCmd implements Command {
    @Override
    public String apply(HttpServletRequest request, HttpServletResponse response) {
        double discount = Double.parseDouble(request.getParameter("disVal"));
        logger.debug("Request for updating discount to " + discount);

        TravelAgency.setDiscountForRegClients(discount);
        logger.debug("Discount updated.");
        return "/agent/homeAgent.jsp";
    }
}
