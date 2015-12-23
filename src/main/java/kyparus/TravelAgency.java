package kyparus;

import kyparus.Tour.*;
import kyparus.Tour.DAO.ExcursionTourDAO;
import kyparus.Tour.DAO.RestTourDAO;
import kyparus.Tour.DAO.ShopTourDAO;
import kyparus.user.Client;
import snaq.db.ConnectionPool;

import java.sql.*;
import java.util.LinkedList;
import org.apache.log4j.Logger;

/**
 * Created by yurii on 29.11.15.
 */
public class TravelAgency {


    final static Logger logger = Logger.getLogger(TravelAgency.class);

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/DREAM_TRAVEL?useUnicode=true&characterEncoding=UTF-8";

    //  Database credentials
    private static  String USER = "root";
    private static  String PASS = "";
    private static  String SCHEDULE_TABLE = "Schedule";
    private static  String DISCOUNT_TABLE = "Discount";
    private static ConnectionPool pool = null;
    private static Connection conn;
    private static Statement statement;
    private static ResultSet result;


    private static Double discountForRegClients = 0.;

    static {
        getPool();
        updateDiscount();
    }

    public static ConnectionPool getPool(){
        if(pool == null){
            try {
                logger.debug("Try to create connection pool.");
                Class.forName(JDBC_DRIVER);
                pool = new ConnectionPool("local",
                        5, 10, 30, 180, DB_URL, USER, PASS);
                logger.debug("Pool for DB created.");
            } catch (ClassNotFoundException e) {
                logger.warn(e.getMessage());
            }

        }
        return pool;
    }

    protected static Boolean tableExists(String tableName) {
        logger.debug("Check if " + tableName+ " exists.");
        try {
            beginQuery();
            DatabaseMetaData md = conn.getMetaData();
            result = md.getTables(null, null, tableName, null);
            if (result.next()) {
                logger.debug("tableName exists.");
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
    protected static Boolean tableExists(){
        return tableExists(SCHEDULE_TABLE);
    }

    protected static void beginQuery() throws SQLException {
        conn = pool.getConnection();
        statement =  conn.createStatement();

    }

    protected static void endQuery() {
        try {
            if(result != null)
                result.close();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            logger.warn("endQuery :" + e.getMessage());
        }
    }

    private static void createDiscountTable(){
        if (tableExists(DISCOUNT_TABLE)) return;
        logger.debug("Creating " + DISCOUNT_TABLE + "...");
        String sql = "CREATE TABLE " + DISCOUNT_TABLE +
                " (ID INTEGER not NULL AUTO_INCREMENT, " +
                " dis DOUBLE , " +
                " PRIMARY KEY ( ID ))";
        String insert = "INSERT INTO " + DISCOUNT_TABLE + " ( dis ) " +
                " VALUES ( 0 )";
        try {
            beginQuery();
            statement.executeUpdate(sql);
            statement.executeUpdate(insert);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug(DISCOUNT_TABLE + " created.");
    }

    public static void createSchedule() {
        if (tableExists()) return;
        logger.debug("Creating " + SCHEDULE_TABLE + "...");
        String sql = "CREATE TABLE " + SCHEDULE_TABLE +
                " (ID INTEGER not NULL AUTO_INCREMENT, " +
                " ClientID INTEGER , " +
                " TourID   INTEGER , " +
                " TourType VARCHAR (10), " +
                " PRIMARY KEY ( ID ))";

        try {
            beginQuery();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug(SCHEDULE_TABLE + " created.");
    }

    public static void dropSchedule(){
        if (!tableExists()) return;
        logger.debug("Dropping " + SCHEDULE_TABLE +"...");
        String sql = "DROP TABLE " + SCHEDULE_TABLE;
        try {
            beginQuery();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug(SCHEDULE_TABLE + " was dropped.");
    }

    public static void addOrder(Integer clientID, Integer tourID, String tourType){
        if (!tableExists()) {
            createSchedule();
        }
        logger.debug("Adding order of client " + clientID + ", tour " + tourType + ", tourType " + tourType);
        String sql = "INSERT INTO " + SCHEDULE_TABLE + " (ClientID, TourID, TourType) " +
                "VALUES (" +
                 clientID + "," +
                 tourID +  ",'" +
                tourType +"')";

        try {
            beginQuery();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug("Order " + clientID + ", " + tourType + ", " + tourType + " added.");
    }

    public static Double countDiscountPrice(Double price ){
        logger.debug("Counting discount for " + price);
        return (discountForRegClients * price) / 100.;
    }

    public static void addOrder(Client client, Tour tour, String tourType ){
        addOrder(client.getID(), tour.getID(), tourType);
    }

    public static LinkedList<Tour> getAllToursOfClient(Client client){
        if (!tableExists()) return null;
        logger.debug("Selecting all orders of " + client.getNickName());
        String sql = "SELECT * FROM " + SCHEDULE_TABLE +
                " WHERE ClientID = " + client.getID();

        LinkedList<Order> answer = new LinkedList<Order>();
        try {
            beginQuery();
            result = statement.executeQuery(sql);
            Order order;
            while (result.next()){
                order = new Order();
                order.ID = result.getInt("ID");
                order.clientID = result.getInt("ClientID");
                order.tourID = result.getInt("TourID");
                order.tourType = result.getString("TourType");
                answer.add(order);
            }
        } catch (SQLException e) {
            logger.debug(e.getMessage());
        } finally {
            endQuery();
        }

        logger.debug("All orders of "+ client.getNickName() + " picked from DB.");
        LinkedList<Tour> tours = new LinkedList<Tour>();
        ExcursionTourDAO excursionTourDAO = new ExcursionTourDAO(TravelAgency.getPool());
        ShopTourDAO shopTourDAO = new ShopTourDAO(TravelAgency.getPool());
        RestTourDAO restTourDAO = new RestTourDAO(TravelAgency.getPool());
        for(Order order : answer){
            if(order.tourType.equals("ex")){
                tours.add(excursionTourDAO.getTour(order.tourID));
            } else if(order.tourType.equals("shop")){
                tours.add(shopTourDAO.getTour(order.tourID));
            } else {
                tours.add(restTourDAO.getTour(order.tourID));
            }
        }
        logger.debug("All tours of "+ client.getNickName() + " where picked from.");
        return tours;

    }
    public static LinkedList<Order> getAllOrders(){
        if (!tableExists()) return null;
        logger.debug("Selecting all orders...");
        String sql = "SELECT * FROM " + SCHEDULE_TABLE;
        LinkedList<Order> answer = new LinkedList<Order>();

        try {
            beginQuery();
            result = statement.executeQuery(sql);
            Order order;
            while (result.next()){
                order = new Order();
                order.ID = result.getInt("ID");
                order.clientID = result.getInt("ClientID");
                order.tourID = result.getInt("TourID");
                order.tourType = result.getString("TourType");
                answer.add(order);
            }

        } catch (SQLException e) {
            logger.debug(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug("All orders were picked from DB.");
        return answer;
    }

    public static void updateDiscount(){
    if(!tableExists(DISCOUNT_TABLE)){
        createDiscountTable();
    }
        logger.debug("Updating discount value." + " (before " + discountForRegClients + ")" );
        String sql = "SELECT * FROM " + DISCOUNT_TABLE +
                " WHERE ID = " + 1;

        try{
            beginQuery();
            result = statement.executeQuery(sql);
            if (result.next()) {
                discountForRegClients = result.getDouble("dis");
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        logger.debug("Updated discount value." + " (after " + discountForRegClients + ")" );
    }

    public static String getDBUSER() {
        return USER;
    }

    public static void getDBUSER(String USER) {
        TravelAgency.USER = USER;
    }

    public static String getPASS() {
        return PASS;
    }

    public static void setPASS(String PASS) {
        TravelAgency.PASS = PASS;
    }

    public static Double getDiscountForRegClients() {
        updateDiscount();
        return discountForRegClients;
    }

    public static void setDiscountForRegClients(Double discount) {
        //discountForRegClients = discount;

        if(!tableExists(DISCOUNT_TABLE)){
            createDiscountTable();
        }
        logger.debug("Changing discount value from " + discountForRegClients + " to " + discount + ".");
        String sql = "UPDATE " + DISCOUNT_TABLE +
                " SET " +
                " dis = " + discount +
                " WHERE ID = " + 1;
        try{
            beginQuery();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            endQuery();
        }
        updateDiscount();
        logger.debug("Discount updated successfully.");

    }


    public static class Order {
        public Integer ID;
        public Integer clientID;
        public Integer tourID;
        public String tourType; //rest , ex, shop
    }
}
