package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.BaseModel;

import utils.ConfigLoader; // class load config từ WEB-INF/config.properties

public abstract class DBContext<T extends BaseModel> {

    protected Connection connection;

    public DBContext() {
        
        try {
            String username = ConfigLoader.get("db.username");
            String password = ConfigLoader.get("db.password");
            String url = ConfigLoader.get("db.url");
            Class.forName(ConfigLoader.get("db.driver"));
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    protected void commitTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    protected void rollbackTransaction() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void resetTransactionMode() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public abstract ArrayList<T> list();

    public abstract T get(int id);

    public abstract void insert(T model);

    public abstract void update(T model);

    public abstract void delete(T model);
}
