package kyparus.Tour.DAO;

import kyparus.Tour.ExcursionTour;
import kyparus.Tour.Tour;
import org.apache.log4j.Logger;
import snaq.db.ConnectionPool;

import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by yurii on 04.12.15.
 */

public class ExcursionTourDAO extends TourDAO {

    private final static Logger logger = Logger.getLogger(ExcursionTourDAO.class);

    private static final String EX_TOURS_TABLE = "ExTours";

    public ExcursionTourDAO(ConnectionPool pool) {
        super(pool);
    }

    @Override
    public  void createTable() {
        if (tableExists(EX_TOURS_TABLE)) return;
        logger.debug("Creating " + EX_TOURS_TABLE);
        String sql = "CREATE TABLE " + EX_TOURS_TABLE +
                " (ID INTEGER not NULL AUTO_INCREMENT, " +
                " name VARCHAR(255), " +
                " transToLocation VARCHAR(255), " +
                " transFromLocation VARCHAR(255), " +
                " departure VARCHAR(255), " +
                " arrival VARCHAR(255) , " +
                " durationFrom INTEGER, " +
                " durationTo INTEGER , " +
                " isHot INTEGER , " +
                " price DOUBLE, " +
                " landmark VARCHAR(255), " +
                " description VARCHAR(255), " +
                " PRIMARY KEY ( ID ))";
        try {
            beginQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug(EX_TOURS_TABLE + " created.");
    }

    @Override
    public void drop() {
        drop(EX_TOURS_TABLE);
    }

    @Override
    public void deleteTour(Tour tour) {
        deleteTour(EX_TOURS_TABLE, tour.getID());
    }

    @Override
    public void deleteTour(Integer ID) {
        deleteTour(EX_TOURS_TABLE, ID);
    }

    public  ExcursionTour getTour(Integer ID) {
        if (!tableExists(EX_TOURS_TABLE)) return null;
        logger.debug("Selecting tour with ID " + ID);
        String sql = "SELECT * FROM  " + EX_TOURS_TABLE +
                " WHERE ID = " + ID;
        ExcursionTour tour = null;
        try {
            beginQuery();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                tour = new ExcursionTour();
                tour.setID(ID);
                tour.setName(rs.getString("name"));
                tour.setDescription(rs.getString("description"));
                tour.setLandmark(rs.getString("landmark"));
                tour.setArrival(rs.getString("arrival"));
                tour.setDeparture(rs.getString("departure"));
                tour.setDurationFrom(rs.getInt("durationFrom"));
                tour.setDurationTo(rs.getInt("durationTo"));
                tour.setHot(rs.getInt("isHot") > 0);
                tour.setPrice(rs.getDouble("price"));
                tour.setTransToLocation(rs.getString("transToLocation"));
                tour.setTransFromLocation(rs.getString("transFromLocation"));
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug("Selected tour with ID " + ID);
        return tour;
    }

    @Override
    public void updateTour(Tour tour) {
        updateTour(tour,EX_TOURS_TABLE);
    }

    public void addTour(ExcursionTour tour) {
        if (!tableExists(EX_TOURS_TABLE))
            createTable();
        logger.debug("Adding tour " + tour.getName());
        String sql = "INSERT INTO " + EX_TOURS_TABLE + " (name, transToLocation, transFromLocation, departure, arrival," +
                " durationFrom, durationTo, isHot, price, landmark, description) " +
                "VALUES ('" +
                tour.getName() + "','" +
                tour.getTransToLocation() + "','" +
                tour.getTransFromLocation() + "','" +
                tour.getDeparture() + "','" +
                tour.getArrival() + "'," +
                tour.getDurationFrom() + "," +
                tour.getDurationTo() + "," +
                (tour.isHot() ? 1 : 0) + "," +
                tour.getPrice() + ",'" +
                tour.getLandmark() + "','" +
                tour.getDescription() + "')";
        try {
            beginQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug("Tour " + tour.getName() + " was added.");
    }

    public LinkedList<ExcursionTour> getAllTours(){
        if (!tableExists(EX_TOURS_TABLE)) return null;
        logger.debug("Getting all tours.");
        String sql = "SELECT * FROM " + EX_TOURS_TABLE;
        LinkedList<ExcursionTour> answer = new LinkedList<ExcursionTour>();

        try {
            beginQuery();
            rs = stmt.executeQuery(sql);

            ExcursionTour tour;
            while (rs.next()) {
                tour = new ExcursionTour();
                tour.setID(rs.getInt("ID"));
                tour.setName(rs.getString("name"));
                tour.setDescription(rs.getString("description"));
                tour.setLandmark(rs.getString("landmark"));
                tour.setArrival(rs.getString("arrival"));
                tour.setDeparture(rs.getString("departure"));
                tour.setDurationFrom(rs.getInt("durationFrom"));
                tour.setDurationTo(rs.getInt("durationTo"));
                tour.setHot(rs.getInt("isHot") > 0);
                tour.setPrice(rs.getDouble("price"));
                tour.setTransToLocation(rs.getString("transToLocation"));
                tour.setTransFromLocation(rs.getString("transFromLocation"));
                answer.add(tour);
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug("Got all tours.");
        return answer;
    }

}
