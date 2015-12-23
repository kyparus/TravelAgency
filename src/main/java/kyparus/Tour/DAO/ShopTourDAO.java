package kyparus.Tour.DAO;

import kyparus.Tour.ShoppingTour;
import kyparus.Tour.Tour;
import org.apache.log4j.Logger;
import snaq.db.ConnectionPool;

import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by yurii on 04.12.15.
 */
public class ShopTourDAO extends TourDAO {

    private final static Logger logger = Logger.getLogger(ShopTourDAO.class);
    private static final String SHOP_TOURS_TABLE = "ShopTours";

    public ShopTourDAO(ConnectionPool pool) {
        super(pool);
    }

    @Override
    public void createTable() {
        if (tableExists(SHOP_TOURS_TABLE)) return;
        logger.debug("Creating " + SHOP_TOURS_TABLE);
        String sql = "CREATE TABLE " + SHOP_TOURS_TABLE +
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
                " mall VARCHAR(255), " +
                " putativeMoney VARCHAR(255), " +
                " PRIMARY KEY ( ID ))";
        try {
            beginQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug(SHOP_TOURS_TABLE + " created.");
    }

    @Override
    public void drop() {
        drop(SHOP_TOURS_TABLE);
    }

    @Override
    public void updateTour(Tour tour) {
        updateTour(tour,SHOP_TOURS_TABLE);
    }
    @Override
    public void deleteTour(Tour tour) {
        deleteTour(SHOP_TOURS_TABLE, tour.getID());
    }

    @Override
    public void deleteTour(Integer ID) {
        deleteTour(SHOP_TOURS_TABLE, ID);
    }

    public ShoppingTour getTour(Integer ID) {
        if (!tableExists(SHOP_TOURS_TABLE)) return null;
        logger.debug("Selecting tour with ID " + ID);
        String sql = "SELECT * FROM  " + SHOP_TOURS_TABLE +
                " WHERE ID = " + ID;
        ShoppingTour tour = null;
        try {
            beginQuery();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                tour = new ShoppingTour();
                tour.setID(ID);
                tour.setName(rs.getString("name"));
                tour.setPutativeMoney(rs.getDouble("putativeMoney"));
                tour.setMall(rs.getString("mall"));
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


    public void addTour(ShoppingTour tour) {
        if (!tableExists(SHOP_TOURS_TABLE))
            createTable();
        logger.debug("Adding tour " + tour.getName());
        String sql = "INSERT INTO " + SHOP_TOURS_TABLE + " (name, transToLocation, transFromLocation, departure, arrival," +
                " durationFrom, durationTo, isHot, price, mall, putativeMoney) " +
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
                tour.getMall() + "','" +
                tour.getPutativeMoney() + "')";
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

    public LinkedList<ShoppingTour> getAllTours() {
        if (!tableExists(SHOP_TOURS_TABLE)) return null;
        logger.debug("Getting all tours.");
        String sql = "SELECT * FROM " + SHOP_TOURS_TABLE;
        LinkedList<ShoppingTour> answer = new LinkedList<ShoppingTour>();

        try {
            beginQuery();
            rs = stmt.executeQuery(sql);
            ShoppingTour tour;
            while (rs.next()) {
                tour = new ShoppingTour();
                tour.setID(rs.getInt("ID"));
                tour.setName(rs.getString("name"));
                tour.setMall(rs.getString("mall"));
                tour.setPutativeMoney(rs.getDouble("putativeMoney"));
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
