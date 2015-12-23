package kyparus;

import kyparus.Tour.DAO.ExcursionTourDAO;
import kyparus.Tour.DAO.RestTourDAO;
import kyparus.Tour.DAO.ShopTourDAO;
import kyparus.Tour.ExcursionTour;
import kyparus.Tour.RestTour;
import kyparus.Tour.ShoppingTour;
import kyparus.Tour.Tour;
import kyparus.user.Agent;
import kyparus.user.Client;
import kyparus.user.DAO.AgentDAO;
import kyparus.user.DAO.ClientDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Objects;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
/**
 * Created by yurii on 04.12.15.
 */
public class Test {
    final static Logger logger = Logger.getLogger(Test.class);

    public static void main(String...args){


        Object client = new Client();

        ((Client) client).setNickName("idiot");


        System.out.println( ((Client) client).getNickName());

//        logger.setLevel(Level.ALL);
//        logger.warn("warning");
//        ClientDAO dao = new ClientDAO(TravelAgency.getPool());
//        Client client = dao.getClient("yurii");
//        LinkedList<Tour> lol = TravelAgency.getAllToursOfClient(client);
//
//        if(lol != null)
//        for (Tour or: lol){
//            System.out.println(or + " -> ");
//        }

//        Client client = new Client();
//        client.setFirstName("Юра");
//        client.setLastName("Яценко");
//        client.setBirth("03.05.1996");
//        client.setPassword("lol");
//
//        client.setNickName("yurii");
//        ClientDAO clientDAO = new ClientDAO(TravelAgency.getPool());
//
//        int id =  clientDAO.addClient(client);
//
//        client = clientDAO.getClient(id);
//        System.out.println(client.getNickName());
//        System.out.println(client.getFirstName());
    //    TravelAgency.getPool();
        //TravelAgency.createDiscountTable();
//        TravelAgency.setDiscountForRegClients(13.4);
//        System.out.println( TravelAgency.getDiscountForRegClients());

//        AgentDAO agentDAO = new AgentDAO(TravelAgency.getPool());
//        agentDAO.drop();
//        Agent agent = new Agent();
//        agent.setPassword("lol");
//        agent.setNickName("admin");
//        agent.setBirth("03.05.1996");
//        agent.setFirstName("Yurii");
//        agent.setLastName("Yatsenko");
//        agent.setPosition("Administrator");
//        agent.setSalary(4600.);
//        agentDAO.addAgent(agent);
//
//        LinkedList<Agent> ls = agentDAO.getAllAgents();
//
//        for(Agent ag : ls){
//            System.out.println("___________");
//            System.out.println(ag.getNickName());
//        }



//
//
//        ExcursionTourDAO excursionTourDAO = new ExcursionTourDAO(TravelAgency.getPool());
//
//        ExcursionTour excursionTour = new ExcursionTour();
//
//        excursionTour.setDescription("Romantic traveling");
//        excursionTour.setLandmark("Eiffel tower");
//        excursionTour.setTransToLocation("Paris");
//        excursionTour.setTransFromLocation("Kiev");
//        excursionTour.setPrice(550.);
//        excursionTour.setArrival("07.10.2016");
//        excursionTour.setDeparture("10.10.2016");
//        excursionTour.setDurationFrom(2);
//        excursionTour.setDurationTo(3);
//        excursionTour.setName("World Romantic");
//
//        excursionTourDAO.addTour(excursionTour);
//        LinkedList<ExcursionTour> ls = excursionTourDAO.getAllTours();
//
//        for(ExcursionTour tr : ls){
//            System.out.println(tr.getLandmark());
//        }

    }
}
