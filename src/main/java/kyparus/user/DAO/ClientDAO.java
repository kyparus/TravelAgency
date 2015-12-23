package kyparus.user.DAO;

import kyparus.user.Client;
import org.apache.log4j.Logger;
import snaq.db.ConnectionPool;

import java.sql.*;
import java.util.LinkedList;


/**
 * Created by yurii on 29.11.15.
 */
public class ClientDAO {


    private static String CLIENTS_TABLE = "Clients";
    Connection connection = null;
    Statement stmt = null;
    ConnectionPool pool;
    private final static Logger logger = Logger.getLogger(ClientDAO.class);

    public ClientDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    private Boolean tableExists() {

        ResultSet rs = null;
        try {
            connection = pool.getConnection();
            stmt = connection.createStatement();
            DatabaseMetaData md = connection.getMetaData();
            rs = md.getTables(null, null, CLIENTS_TABLE, null);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert rs != null;
                rs.close();
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void createTable() {
        logger.debug("Creating " + CLIENTS_TABLE + ".");
        String sql = "CREATE TABLE " + CLIENTS_TABLE +
                " (ID INTEGER not NULL AUTO_INCREMENT, " +
                " firstName  VARCHAR(255), " +
                " lastName VARCHAR (255), " +
                " nickName VARCHAR(255), " +
                " password VARCHAR(255), " +
                " birth VARCHAR(255) , " +
                " usageTimes INTEGER, " +
                " isRegular INTEGER , " +
                " PRIMARY KEY ( ID ))";
        try {
            connection = pool.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            try {
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            }
        }
        logger.debug("Creating " + CLIENTS_TABLE + ".");
    }

    public void drop() {
        if (!tableExists()) return;
        logger.debug("Dropping " + CLIENTS_TABLE);
        String sql = "DROP TABLE " + CLIENTS_TABLE;
        try {
            connection = pool.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            try {
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            }
        }
        logger.debug(CLIENTS_TABLE + " was dropped.");
    }

    public Integer addClient(Client client) {
        if (!tableExists()) {
            createTable();
        }
        logger.debug("Adding client " + client.getNickName() + ".");
        String sql = "INSERT INTO " + CLIENTS_TABLE + " (firstName, lastName, nickName, password, birth, usageTimes, isRegular) " +
                "VALUES ('" +
                client.getFirstName() + "','" +
                client.getLastName() + "','" +
                client.getNickName() + "','" +
                client.getPassword() + "','" +
                client.getBirth() + "','" +
                client.getUsageTimes() + "','" +
                (client.isRegular() ? 1 : 0) + "')";
        try {
            connection = pool.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {

            try {
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            }
        }
        logger.debug("Client " + client.getNickName() + " was added.");
        return getClient(client.getNickName()).getID();
    }

    public LinkedList<Client> getAllClients() {
        if (!tableExists()) return null;
        logger.debug("Selecting all clients");
        String sql = "SELECT * FROM " + CLIENTS_TABLE;
        LinkedList<Client> answer = new LinkedList<Client>();
        ResultSet result = null;
        try {
            connection = pool.getConnection();
            stmt = connection.createStatement();
            result = stmt.executeQuery(sql);
            // Extract data from result set
            Client curClient;
            while (result.next()) {
                //Retrieve by column name
                int id = result.getInt("ID");
                String date = result.getString("birth");
                String first = result.getString("firstName");
                String last = result.getString("lastName");
                String pass = result.getString("password");
                String nick = result.getString("nickName");
                Integer usageTimes = result.getInt("usageTimes");
                Integer isRegular = result.getInt("isRegular");
                curClient = new Client();
                curClient.setUsageTimes(usageTimes);
                if (isRegular > 0)
                    curClient.setAsRegular();
                curClient.setBirth(date);
                curClient.setFirstName(first);
                curClient.setLastName(last);
                curClient.setID(id);
                curClient.setPassword(pass);
                curClient.setNickName(nick);
                answer.add(curClient);

            }

        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            try {
                assert result != null;
                result.close();
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            }
        }
        logger.debug("Selected all clients.");
        return answer;
    }

    public void updateClient(Client client) {
        if (!tableExists()) return;
        logger.debug("Updating client " + client.getNickName());
        String sql = "UPDATE " + CLIENTS_TABLE +
                " SET " +
                "usageTimes = " + client.getUsageTimes() + ", " +
                "isRegular = " + (client.isRegular() ? 1 : 0) + ", " +
                "password = '" + client.getPassword() + "' " +
                " WHERE ID = " + client.getID();
        try {
            connection = pool.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            try {
                stmt.close();
                connection.close();
            } catch (SQLException ex) {
                logger.warn(ex.getMessage());
            }
        }
        logger.debug("Client " + client.getNickName() + " was updated.");
    }

    public Client getClient(Integer ID) {
        if (!tableExists()) return null;
        logger.debug("Trying to get client with id " + ID);
        String sql = "SELECT * FROM  " + CLIENTS_TABLE +
                " WHERE ID = " + ID;
        Client client = null;
        ResultSet result = null;
        try {
            connection = pool.getConnection();
            stmt = connection.createStatement();
            result = stmt.executeQuery(sql);
            if (result.next()) {
                String date = result.getString("birth");
                String first = result.getString("firstName");
                String last = result.getString("lastName");
                String nick = result.getString("nickName");
                String pass = result.getString("password");
                Integer usageTimes = result.getInt("usageTimes");
                Integer isRegular = result.getInt("isRegular");
                client = new Client();
                client.setUsageTimes(usageTimes);
                if (isRegular > 0)
                    client.setAsRegular();
                client.setBirth(date);
                client.setFirstName(first);
                client.setLastName(last);
                client.setNickName(nick);
                client.setPassword(pass);
                client.setID(ID);
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            try {
                assert result != null;
                result.close();
                stmt.close();
                connection.close();
            } catch (SQLException ex) {
                logger.warn(ex.getMessage());
            }
        }
        logger.debug("Client " + client.getNickName() + " was picked from DB.");
        return client;
    }

    public Client getClient(String nickName) {
        if (!tableExists()) return null;
        logger.debug("Trying to get client with nickName " + nickName);
        String sql = "SELECT * FROM  " + CLIENTS_TABLE +
                " WHERE nickName = '" + nickName + "'";
        Client client = null;
        ResultSet result = null;
        try {
            connection = pool.getConnection();
            stmt = connection.createStatement();
            result = stmt.executeQuery(sql);
            if (result.next()) {
                String date = result.getString("birth");
                String first = result.getString("firstName");
                String last = result.getString("lastName");
                String pass = result.getString("password");
                Integer usageTimes = result.getInt("usageTimes");
                Integer isRegular = result.getInt("isRegular");
                client = new Client();
                client.setUsageTimes(usageTimes);
                if (isRegular > 0)
                    client.setAsRegular();
                client.setBirth(date);
                client.setFirstName(first);
                client.setLastName(last);
                client.setNickName(nickName);
                client.setPassword(pass);
                client.setID(result.getInt("ID"));
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            try {
                assert result != null;
                result.close();
                stmt.close();
                connection.close();
            } catch (SQLException ex) {
                logger.warn(ex.getMessage());
            }
        }
        if (client != null)
            logger.debug("Client " + client.getNickName() + " was picked from DB.");
        else
            logger.debug(" No client was picked from DB.");
        return client;
    }


    public void deleteClient(Client client) {
        if (!tableExists()) return;
        String sql = "DELETE FROM " + CLIENTS_TABLE +
                " WHERE ID = " + client.getID();
        try {
            connection = pool.getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            try {
                stmt.close();
                connection.close();
            } catch (SQLException ex) {
                logger.warn(ex.getMessage());
            }
        }
        logger.debug("Client " + client.getNickName() + " was deleted from DB.");
    }
}
