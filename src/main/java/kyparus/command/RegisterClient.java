package kyparus.command;

import kyparus.Tour.Tour;
import kyparus.TravelAgency;
import kyparus.user.Client;
import kyparus.user.DAO.ClientDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;

/**
 * Created by yurii on 10.12.15.
 */
public class RegisterClient implements Command {
    private static ClientDAO clientDAO = null;

    @Override
    public String apply(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String birth = request.getParameter("birth");
        String nickname = request.getParameter("nickname");
        String pass = request.getParameter("password");
        String url;

        logger.debug("Trying to register " + nickname + ".");

        //getting client
        Client client = (new ClientDAO(TravelAgency.getPool())).getClient(nickname);

        HttpSession session = request.getSession();
        session.removeAttribute("client");
        session.removeAttribute("agent");
        if (client != null) {
            session.setAttribute("msg", "Such nickname already exists((");
            logger.debug("Such nickname already exists((");
            return   "/errorPage.jsp";
        } else {

            client = new Client();
            client.setFirstName(firstName);
            client.setLastName(secondName);
            client.setNickName(nickname);
            client.setBirth(birth);
            client.setPassword(pass);

            if (clientDAO == null)
                clientDAO = new ClientDAO(TravelAgency.getPool());

            client.setID(clientDAO.addClient(client));
            session.setAttribute("client", client);

            //get all rest tours
            CommandFactory.executeCommand("get rest tours",request,response);



            logger.debug("Client " + nickname + " registered successfully.");
           return  "/client/homeClient.jsp";
        }
    }
}
