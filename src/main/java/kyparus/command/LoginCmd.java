package kyparus.command;

import kyparus.Tour.Tour;
import kyparus.TravelAgency;
import kyparus.user.Agent;
import kyparus.user.Client;
import kyparus.user.DAO.AgentDAO;
import kyparus.user.DAO.ClientDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;

/**
 * Created by yurii on 12.12.15.
 */
public class LoginCmd implements Command {
    @Override
    public String apply(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("client");
        session.removeAttribute("agent");

        String login = request.getParameter("login");
        String pass = request.getParameter("password");

        logger.debug(login + " is trying to sign in.");
        System.out.println("лв " + request.getParameter("submitLogin"));

        //getting client
        Client client = (new ClientDAO(TravelAgency.getPool())).getClient(login);



        if (client != null && client.getPassword().equals(pass)) {
            session.setAttribute("client", client);
            logger.debug("Client " + login + "signed in successfully.");
            //get rest tours
            CommandFactory.executeCommand("get rest tours",request,response);
            System.out.println("return /client/homeClient.jsp");
            return "/client/homeClient.jsp";

        } else {

            Agent agent = (new AgentDAO(TravelAgency.getPool())).getAgent(login);
            if (agent != null && agent.getPassword().equals(pass)) {
                session.setAttribute("agent", agent);
                logger.debug("Agent " + login + "signed in successfully.");
                CommandFactory.executeCommand("get rest tours",request,response);
                return "/agent/homeAgent.jsp";
            } else {
                logger.debug("Wrong username or password.");
                session.setAttribute("msg", "Wrong username or password");
                return  "/errorPage.jsp";
            }

        }



    }
}
