/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DBUtils;

/**
 *
 * @author liana
 */
public class User {

    public int user_id;
    private String username;
    private String password;
    private int student_id;
    private int trainer_id;
    private String role;

    public User(int user_id, String username, String password, int student_id, int trainer_id, String role) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.student_id = student_id;
        this.trainer_id = trainer_id;
        this.role = role;
    }

    public User() {

    }

    public User(int student_id, int trainer_id, String role, String password) {
        this.student_id = student_id;
        this.trainer_id = trainer_id;
        this.role = role;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(int trainer_id) {
        this.trainer_id = trainer_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "USER ID: " + user_id + "\n"
                + "USERNAME: " + username + "\n"
                + "STUDENT ID: " + student_id + "\n"
                + "TRAINER ID: " + trainer_id + "\n"
                + "ROLE: " + role + "\n";

    }

}
