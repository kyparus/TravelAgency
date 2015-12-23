package kyparus.Tour.DAO;

import kyparus.Tour.ExcursionTour;
import kyparus.Tour.Tour;
import org.apache.log4j.Logger;
import snaq.db.ConnectionPool;

import java.sql.*;

/**
 * Created by yurii on 03.12.15.
 */

//builder pattern
public abstract class TourDAO {

    protected static Connection connection = null;
    protected static Statement stmt = null;
    protected static ConnectionPool pool;
    protected static ResultSet rs = null;
    private final static Logger logger = Logger.getLogger(TourDAO.class);

    TourDAO(ConnectionPool pool) {
        TourDAO.pool = pool;
    }

    protected static Boolean tableExists(String tableName) {
        logger.debug("Checking if " + tableName + " exists.");
        try {
            beginQuery();
            DatabaseMetaData md = connection.getMetaData();
            rs = md.getTables(null, null, tableName, null);
            if (rs.next()) {
                logger.debug(tableName + " exists.");
                return true;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug(tableName + " does not exist.");
        return false;
    }

    protected static void beginQuery() throws SQLException {
        connection = pool.getConnection();
        stmt = connection.createStatement();
    }

    protected static void endQuery() {
        try {
            if (rs != null)
                rs.close();

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  abstract   void createTable();

    protected void drop(String tableName) {
        logger.debug("Dropping " + tableName);
        if (!tableExists(tableName)) return;
        String sql = "DROP TABLE " + tableName;
        try {
            beginQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug(tableName + " dropped.");
    }

    protected static void updateTour(Tour tour, String tableName) {
        if (!tableExists(tableName)) return;
        logger.debug("Updating tour " + tour.getID() + " from " + tableName);
        String sql = "UPDATE " + tableName +
                " SET " +
                " isHot = " + (tour.isHot() ? 1 : 0) + ", " +
                " price = " + tour.getPrice() + " , " +
                " name = '" + tour.getName() +
                "' WHERE ID = " + tour.getID();

        try {
            beginQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug("Tour " + tour.getID() + " was updated.");
    }

    public abstract void updateTour(Tour tour);

    public abstract void drop();

    protected void deleteTour(String tableName, Integer ID) {
        if (!tableExists(tableName)) return;
        String sql = "DELETE FROM " + tableName +
                " WHERE ID = " + ID;
        try {
            beginQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug("Deleted tour " +ID + " from " + tableName);
    }

    public abstract void deleteTour(Tour tour);

    public abstract void deleteTour(Integer ID);
}
