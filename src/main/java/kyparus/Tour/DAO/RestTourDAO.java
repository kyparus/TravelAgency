package kyparus.Tour.DAO;

import kyparus.Tour.ExcursionTour;
import kyparus.Tour.RestTour;
import kyparus.Tour.Tour;
import org.apache.log4j.Logger;
import snaq.db.ConnectionPool;

import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by yurii on 04.12.15.
 */
public class RestTourDAO extends TourDAO {


    private final static Logger logger = Logger.getLogger(RestTourDAO.class);

    protected static final String REST_TOUR_TABLE = "RestTours";

    public RestTourDAO(ConnectionPool pool) {
        super(pool);
    }

    @Override
    public void createTable() {
        if (tableExists(REST_TOUR_TABLE)) return;
        logger.debug("Creating " + REST_TOUR_TABLE);
        String sql = "CREATE TABLE " + REST_TOUR_TABLE +
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
                " hotel VARCHAR(255), " +
                " board VARCHAR(3), " +
                " PRIMARY KEY ( ID ))";
        try {
            beginQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug(REST_TOUR_TABLE + " created.");
    }

    @Override
    public void updateTour(Tour tour) {
        updateTour(tour, REST_TOUR_TABLE);
    }

    @Override
    public void drop() {
        drop(REST_TOUR_TABLE);
    }

    @Override
    public void deleteTour(Tour tour) {
        deleteTour(REST_TOUR_TABLE, tour.getID());
    }

    @Override
    public void deleteTour(Integer ID) {
        deleteTour(REST_TOUR_TABLE, ID);
    }

    public RestTour getTour(Integer ID) {
        if (!tableExists(REST_TOUR_TABLE)) return null;
        logger.debug("Selecting tour with ID " + ID);
        String sql = "SELECT * FROM  " + REST_TOUR_TABLE +
                " WHERE ID = " + ID;
        RestTour tour = null;
        try {
            beginQuery();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                tour = new RestTour();
                tour.setID(ID);
                tour.setName(rs.getString("name"));
                tour.setBoard(RestTour.Board.valueOf(rs.getString("board")));
                tour.setHotel(rs.getString("hotel"));
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


    public void addTour(RestTour tour) {
        if (!tableExists(REST_TOUR_TABLE))
            createTable();
        logger.debug("Adding tour " + tour.getName());
        String sql = "INSERT INTO " + REST_TOUR_TABLE + " (name, transToLocation, transFromLocation, departure, arrival," +
                " durationFrom, durationTo, isHot, price, hotel, board) " +
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
                tour.getHotel() + "','" +
                tour.getBoard() + "')";
        try {
            beginQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug("Adding tour " + tour.getName());
    }

    public LinkedList<RestTour> getAllTours() {
        if (!tableExists(REST_TOUR_TABLE)) return null;
        logger.debug("Getting all tours.");
        String sql = "SELECT * FROM " + REST_TOUR_TABLE;
        LinkedList<RestTour> answer = new LinkedList<RestTour>();

        try {
            beginQuery();
            rs = stmt.executeQuery(sql);
            RestTour tour;
            while (rs.next()) {
                tour = new RestTour();
                tour.setID(rs.getInt("ID"));
                tour.setName(rs.getString("name"));
                tour.setBoard(RestTour.Board.valueOf(rs.getString("board")));
                tour.setHotel(rs.getString("hotel"));
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
