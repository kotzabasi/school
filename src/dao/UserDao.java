/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import school.Utils;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import menus.Home;
import model.User;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import utils.DBUtils;

/**
 *
 * @author liana
 */
public class UserDao {

    public static User user;
    static String username;
    static int user_id;
    public static String password;

//    list of users
    
    public static ArrayList<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select * from user";
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {
                int user_id = rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                int student_id = rs.getInt(4);
                int trainer_id = rs.getInt(5);
                String role = rs.getString(6);

                User user = new User(user_id, username, password, student_id, trainer_id, role);
                list.add(user);
                System.out.println(user.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;

    }
//    for error message when username input is incorrect

    public static String checkIfUsernameExists(String username) {
        Scanner sc = new Scanner(System.in);
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        try {

            String sql = "SELECT user_id,role from user where username='" + username + "'";
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            if (rs.next()) {
                int user_id = rs.getInt(1);
                System.err.println("Hello " + username + "!");
            } else {
                System.err.println("this username does not exist. please try again");
                System.out.println("Type the correct username:");
                username = sc.nextLine();
                checkIfUsernameExists(username);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return UserDao.username;
    }
//    checking passord

    public static String checkPassword() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("PASSWORD: " );
        String s = Utils.notNull(sc.nextLine());

        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;

        try {
            String sql = "SELECT student_id,trainer_id,role,password from user where password='" + s + "'";
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            if (rs.next()) {

                int student_id = rs.getInt(1);
                int trainer_id = rs.getInt(2);
                String role = rs.getString(3);
                String password=rs.getString("password");
                user = new User(student_id, trainer_id, role,password);
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (user.getRole().equalsIgnoreCase("student")) {
                    Home.studentMenu();
                } else if (user.getRole().equalsIgnoreCase("trainer")) {
                    Home.trainerMenu();
                } else if (user.getRole().equalsIgnoreCase("admin")) {
                    Home.adminMenu();
                } else if (user.getRole().equalsIgnoreCase("admin")) {
                    Home.trainerMenu();
                } else {

                    try {
                        Home.headmasterMenu();
                    } catch (ParseException ex) {
                        Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } else {
                System.err.println("This password does not exist. please try again: ");
                checkPassword();
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return s;

    }
    
// I DID'T USE IT - NO SETTING PASSWORD MENU FOR USER - IT IS NOT REQUIRED FROM THE ASSIGNMENT
//
//    public static int findUserId(String username) {
//        Connection con = DBUtils.getConnection();
//        PreparedStatement pst = null;
//        String sql = "SELECT user_id FROM user WHERE username='" + username + "'";
//        try {
//            pst = con.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery(sql);
//            while (rs.next()) {
//
//                user_id = rs.getInt("user_id");
//                System.out.println("USER ID " + user_id);
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//
//            try {
//                pst.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                con.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return user_id;
//    }
//
//    public static void resetPassToDefault() {
//
//    }
    
}
