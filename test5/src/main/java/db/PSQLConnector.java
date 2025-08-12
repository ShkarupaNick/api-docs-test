package db;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Syma on 06.02.2016.
 */
public class PSQLConnector {
    public static final Logger log = Logger.getLogger(PSQLConnector.class);

    FileInputStream input;
    Connection connection = null;


    public PSQLConnector(){
        getConnection();
    }


    public Connection getConnection() {
        Properties prop = new Properties();

        try {
            //log.info(new File(".").getAbsolutePath());
            input = new FileInputStream("src/main/resources/db.properties");
            // load a properties file
            prop.load(input);
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + prop.getProperty("db_host") + ":" + prop.getProperty("port") + "/" + prop.getProperty("db_name"), prop.getProperty("username"), prop.getProperty("password"));

        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (SQLException e) {
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        return connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public ResultSet execQuery(String query) throws SQLException {
        return this.connection.createStatement().executeQuery(query);
    }


}
