package kyparus.Tour.DAO;

import kyparus.Tour.ExcursionTour;
import kyparus.Tour.RestTour;

import java.sql.*;

/**
 * Created by yurii on 30.11.15.
 */

//for forward developing
public class TourDAOFactory {

    Connection connection = null;
    Statement stmt = null;

    TourDAOFactory(Connection conn){
        connection = conn;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Boolean tableExists(String table){
        ResultSet rs = null;
        try {
            DatabaseMetaData md = connection.getMetaData();
            rs = md.getTables(null, null, table, null);
            if (rs.next()) {
                rs.close();
                return true;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void createShopTourTable(){

        String sql = "CREATE TABLE ShopTours " +
                "(ID INTEGER not NULL, " +
                " durationFrom VARCHAR(255), " +
                " durationTo VARCHAR(255), " +
                " departure DATE , " +
                " arrival DATE , " +
                " price DOUBLE, " +
                "isHot BIT" +
                "boutiquesID INTEGER not NULL" +
                " PRIMARY KEY ( ID ))";

        String sqlBtq = "CREATE TABLE BtqLastID " +
                "(ID INTEGER not NULL," +
                "LastID INTEGER not NULL," +
                "PRIMARY KEY ( ID ))";
        String sqlInsert = "INSERT INTO BtqLastID (LastID) " +
                "VALUES (0)";
        try {
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sqlBtq);
            stmt.executeUpdate(sqlInsert);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createExTourTable(){

        String sql = "CREATE TABLE ExTours " +
                "(ID INTEGER not NULL, " +
                " durationFrom VARCHAR(255), " +
                " durationTo VARCHAR(255), " +
                " departure DATE , " +
                " arrival DATE , " +
                " price DOUBLE, " +
                " isHot BIT," +
                " landmarksID INTEGER not NULL," +
                " PRIMARY KEY ( ID ))";

        String sqlLnd = "CREATE TABLE LndLastID " +
                "(ID INTEGER not NULL," +
                "LastID INTEGER not NULL," +
                "PRIMARY KEY ( ID ))";
        String sqlInsert = "INSERT INTO LndLastID (LastID) " +
                "VALUES (0)";
        try {
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sqlLnd);
            stmt.executeUpdate(sqlInsert);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addExcursionTour(ExcursionTour tour){
        if(!tableExists("ExTours"))
            createExTourTable();

        int lndID = 0;
        String sqlLastId = "SELECT LastID FROM LndLastID";
        try {
            ResultSet result = stmt.executeQuery(sqlLastId);
            lndID = result.getInt("LastID");
            lndID++;
            sqlLastId = "UPDATE LndLastID SET LastID=" + lndID + "WHERE ID = 0";
            stmt.executeQuery(sqlLastId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO ExTours (durationFrom, durationTo, departure, arrival, isHot, price, landmarksID ) " +
                "VALUES ('" +
                tour.getDurationFrom() + "','" +
                tour.getDurationTo() + "','" +
                tour.getDeparture() + "','" +
                tour.getArrival() +  "','" +
                tour.isHot() + "','" +
                tour.getPrice() + "','" +
                lndID + "')";

        String sqlLandmark =  "CREATE TABLE Landmarks" + lndID +
                " (ID INTEGER not NULL," +
                "landmark VARCHAR (255)," +
                "PRIMARY KEY ( ID ))";

        try {
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sqlLandmark);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createRestTourTable(){

        String sql = "CREATE TABLE RestTours " +
                "(ID INTEGER not NULL, " +
                " durationFrom VARCHAR(255), " +
                " durationTo VARCHAR(255), " +
                " departure DATE , " +
                " arrival DATE , " +
                " price DOUBLE, " +
                " isHot BIT," +
                " hotel VARCHAR (255)," +
                " board VARCHAR (255)," +
                " PRIMARY KEY ( ID ))";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addRestTour(RestTour tour){
        if(!tableExists("RestTours"))
            createRestTourTable();

        String sql = "INSERT INTO RestTours (durationFrom, durationTo, departure,arrival,isHot, price, hotel, board) " +
                "VALUES ('" +
                tour.getDurationFrom() + "','" +
                tour.getDurationTo() + "','" +
                tour.getDeparture() + "','" +
                tour.getArrival() +  "','" +
                tour.isHot() + "','" +
                tour.getPrice() + "','" +
                tour.getHotel() + "','" +
                tour.getBoard() + "')";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
