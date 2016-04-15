/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.disc.jdbc;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author yubraj
 */
public class DBConnection {
    
    public static Connection connection = null;
    
    public static void main() throws IOException, SQLException, ClassNotFoundException{
     connection = getConnection();
    }
    

    
    public static Connection getConnection() throws IOException, SQLException, ClassNotFoundException{
        if(connection!=null) return connection;
        else{
                Properties prop = new Properties();
                InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("/com/disc/resourses/db.properties");
                prop.load(input);
                String driver = prop.getProperty("driver");
                String url = prop.getProperty("url");
                String user = prop.getProperty("user");
                String password = prop.getProperty("password");
                Class.forName(driver);
                connection = (Connection) DriverManager.getConnection(url, user, password);
                System.out.println("Connection to MySQL is Establised...");
                return connection;
        }
    }
    
    public static void close() throws SQLException {
        DBConnection.connection.close();

    }
    
}
