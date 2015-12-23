package kyparus.user.DAO;

import kyparus.user.Agent;
import org.apache.log4j.Logger;
import snaq.db.ConnectionPool;

import java.sql.*;
import java.util.LinkedList;

/**
 * Created by yurii on 29.11.15.
 */
public class AgentDAO {

    private static String AGENTS_TABLE = "Agents";
    Connection connection = null;
    Statement stmt = null;
    ConnectionPool pool;
    private final static Logger logger = Logger.getLogger(AgentDAO.class);

    public AgentDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    private Boolean tableExists() {

        ResultSet rs = null;
        try {
            connection = pool.getConnection();
            stmt = connection.createStatement();
            DatabaseMetaData md = connection.getMetaData();
            rs = md.getTables(null, null, AGENTS_TABLE, null);
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
        logger.debug("Creating " + AGENTS_TABLE + ".");
        String sql = "CREATE TABLE " + AGENTS_TABLE +
                " (ID INTEGER not NULL AUTO_INCREMENT, " +
                " firstName NCHAR (255), " +
                " lastName VARCHAR(255), " +
                " nickName VARCHAR(255), " +
                " password VARCHAR(255), " +
                " birth VARCHAR(255) , " +
                " position VARCHAR(255), " +
                " salary DOUBLE, " +
                " PRIMARY KEY ( ID )) default character set utf8";
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
        logger.debug(AGENTS_TABLE + " created.");
    }

    public void drop() {
        if (!tableExists()) return;
        logger.debug("Dropping " + AGENTS_TABLE);
        String sql = "DROP TABLE " + AGENTS_TABLE;
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
        logger.debug(AGENTS_TABLE + " was dropped.");
    }

    public void addAgent(Agent agent) {
        if (!tableExists()) {
            createTable();
        }
        logger.debug("Adding agent " + agent.getNickName() + ".");
        String sql = "INSERT INTO " + AGENTS_TABLE + " (firstName, lastName, nickName, password, birth, position, salary) " +
                "VALUES ( CONVERT( '" +
                agent.getFirstName() + "' USING utf8) , '" +
                agent.getLastName() + "','" +
                agent.getNickName() + "','" +
                agent.getPassword() + "','" +
                agent.getBirth() + "','" +
                agent.getPosition() + "'," +
                agent.getSalary() + ")";
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
        logger.debug("Agent " + agent.getNickName() + " was added.");
    }

    public LinkedList<Agent> getAllAgents() {
        if (!tableExists()) return null;
        logger.debug("Selecting all agents");
        String sql = "SELECT * FROM " + AGENTS_TABLE;
        LinkedList<Agent> answer = new LinkedList<Agent>();
        ResultSet result = null;
        try {
            connection = pool.getConnection();
            stmt = connection.createStatement();
            result = stmt.executeQuery(sql);
            // Extract data from result set
            Agent curAgent;
            while (result.next()) {

                int id = result.getInt("ID");
                String date = result.getString("birth");
                String first = result.getString("firstName");
                String last = result.getString("lastName");
                String pass = result.getString("password");
                String nick = result.getString("nickName");
                Double salary = result.getDouble("salary");
                String position = result.getString("position");
                curAgent = new Agent();
                curAgent.setSalary(salary);
                curAgent.setPosition(position);
                curAgent.setBirth(date);
                curAgent.setFirstName(first);
                curAgent.setLastName(last);
                curAgent.setID(id);
                curAgent.setPassword(pass);
                curAgent.setNickName(nick);
                answer.add(curAgent);
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
        logger.debug("Selected all agents.");
        return answer;
    }

    public void updateAgent(Agent agent) {
        if (!tableExists()) return;
        logger.debug("Updating agent " + agent.getNickName());
        String sql = "UPDATE " + AGENTS_TABLE +
                " SET " +
                "position = " + agent.getPosition() + "," +
                "salary = " + agent.getSalary() + ", " +
                "password = '" + agent.getPassword() + "' " +
                " WHERE ID = " + agent.getID();
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
        logger.debug("Agent " + agent.getNickName() + " was updated.");
    }


    public Agent getAgent(Integer ID) {
        if (!tableExists()) return null;
        logger.debug("Trying to get agent with id " + ID);
        String sql = "SELECT * FROM " + AGENTS_TABLE +
                " WHERE ID = " + ID;
        Agent agent = null;
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
                String position = result.getString("position");
                Double salary = result.getDouble("salary");
                agent = new Agent();
                agent.setPosition(position);
                agent.setSalary(salary);
                agent.setBirth(date);
                agent.setFirstName(first);
                agent.setLastName(last);
                agent.setPassword(pass);
                agent.setNickName(nick);
                agent.setID(ID);
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
        assert agent != null;
        logger.debug("Agent " + agent.getNickName() + " was picked from DB.");
        return agent;
    }
    public Agent getAgent(String nickName) {
        if (!tableExists()) return null;
        logger.debug("Trying to get agent with nickName " + nickName);
        String sql = "SELECT * FROM " + AGENTS_TABLE +
                " WHERE nickName = '" + nickName + "'";
        Agent agent = null;
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
                String position = result.getString("position");
                Double salary = result.getDouble("salary");
                agent = new Agent();
                agent.setPosition(position);
                agent.setSalary(salary);
                agent.setBirth(date);
                agent.setFirstName(first);
                agent.setLastName(last);
                agent.setPassword(pass);
                agent.setNickName(nickName);
                agent.setID(result.getInt("ID"));
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
        if (agent != null)
            logger.debug("Agent " + agent.getNickName() + " was picked from DB.");
        else
            logger.debug(" No agent was picked from DB.");
        return agent;
    }

    public void deleteAgent(Agent agent) {
        if (!tableExists() || agent == null) return;
        String sql = "DELETE FROM " + AGENTS_TABLE +
                " WHERE ID = " + agent.getID();
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
        logger.debug("Agent " + agent.getNickName() + " was deleted from DB.");
    }

}
