/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author liana
 */
public class DBUtils {
//    please insert username and password
    
    public static String USERNAME = "";
    public static String PASS = "";
    public static final String MYSQLURL = "jdbc:mysql://localhost/individual?useUnicode="
            + "true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
            + "&useUnicode=true"
            + "&useJDBCCompliantTimezoneShift=true"
            + "&useLegacyDatetimeCode=false"
            + "&serverTimezone=UTC"
            + "&allowPublicKeyRetrieval=true"
            + "&useSSL=false";
         
         
    public static Connection getConnection() {
    
    Connection con =null;
    try {
        con = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);
    } catch (SQLException ex) {
        System.out.println("Failed to connect to database");
        Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
    }

    return con;
}
    
}
