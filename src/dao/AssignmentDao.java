/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import school.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.ir.BreakNode;
import menus.Home;
import model.Assignment;
import utils.DBUtils;

/**
 *
 * @author liana
 */
public class AssignmentDao {

    public static int assignment_id;
    public static String title;

    public static ArrayList<Assignment> getAssignments() {
        ArrayList<Assignment> list = new ArrayList<>();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select * from assignment";
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                int assignment_id = rs.getInt(1);
                String title = rs.getString(2);
                String description_of_assignment = rs.getString(3);
                Timestamp submission_date = rs.getTimestamp(4);
                Timestamp expiration_date = rs.getTimestamp(5);
                String stream = rs.getString(6);
                if (rs.wasNull()) {
                    submission_date = null;
                    expiration_date = null;
                    Assignment a = new Assignment(assignment_id, title, description_of_assignment, stream);
                    list.add(a);
                    System.out.println(a.toString());

                } else {
                    Assignment assignment = new Assignment(assignment_id, title, description_of_assignment, submission_date, expiration_date, stream);
                    list.add(assignment);
                    System.out.println(assignment.toString());

                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(Assignment.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Assignment.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Assignment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return list;

    }

    public static void showByAssignmentId() {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT*FROM assignment WHERE assignment_id='" + assignment_id + "'";
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                assignment_id = rs.getInt(1);
                title = rs.getString(2);
                String description_of_assignment = rs.getString(3);
                Timestamp submission_date = rs.getTimestamp(4);
                Timestamp expiration_date = rs.getTimestamp(5);
                String stream = rs.getString(6);
                Assignment assignment = new Assignment(assignment_id, title, description_of_assignment, submission_date, expiration_date, stream);
                System.out.println(assignment.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void checkIfAssignmentExists() {
        Connection con = DBUtils.getConnection();
        Scanner sc = new Scanner(System.in);
        PreparedStatement pst = null;
        try {
            System.out.println("TITLE OF ASSIGNMENT:");
            title = sc.nextLine();
            String sql = "select assignment_id,title,stream from assignment where title ='" + title + "'";
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            if (rs.next()) {
                assignment_id = rs.getInt(1);
                System.err.println("Assignment ID: " + assignment_id + "\n" + "Title: " + title + "\n"
                        + "Stream: " + rs.getString("stream"));
            } else {
                System.err.println("this assignment does not exist. please try again");
                checkIfAssignmentExists();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void deleteAssignment() throws ParseException {
        getAssignments();
        System.out.println("You have to type the title of the assignment you want to delete");
        checkIfAssignmentExists();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "DELETE FROM assignment WHERE assignment_id=?";
        boolean result = false;
        try {
            pst = con.prepareStatement(sql);
            Scanner sc = new Scanner(System.in);
            System.out.println("type the assignment_id if  you want to delete this assignment");
            assignment_id = Utils.onlyInteger();
            checkByTitleAndId();
            System.out.println("Are you sure?");
            System.out.println("Please answer with yes or no");
            String answer = Utils.answerYesOrNo(sc.nextLine());
            switch (answer) {
                case "yes":
                    pst.setInt(1, assignment_id);
                    pst.executeUpdate();
                    result = true;
                    break;
                case "no":
                    Home.headmasterMenu();
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDao.class.getName()).log(Level.SEVERE, null, ex);
            result = false;

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (result) {
            System.err.println("ASSIGNMENT HAS BEEN DELETED");
            System.out.println();
        }
    }

    public static void insertAssignment() {
        System.out.println("Would you like to set up specific submission and expiration dates for this assignment? (Please answer with yes/no)");
        Scanner sc = new Scanner(System.in);
        String answer = Utils.answerYesOrNo(sc.nextLine());
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "INSERT INTO assignment(title,description_of_assignment,submission_date,expiration_date,stream)"
                + "values(?,?,?,?,?)";
        Assignment assignment = new Assignment();
        switch (answer) {

            case "yes":
                try {
                    pst = con.prepareStatement(sql);
                    System.out.println("Title:");
                    title = Utils.notNull(sc.nextLine());
                    assignment.setTitle(title);
                    System.out.println("Description:");
                    String description_of_assignment = Utils.notNull(sc.nextLine());
                    assignment.setDescription_of_assignment(description_of_assignment);

                    boolean subinput = false;
                    while (!subinput) {
                        try {
                            System.out.println("Submission Date(yyyy-mm-dd hh:mm:ss):");
                            String subDate = sc.nextLine();

                            Timestamp subm_date = Timestamp.valueOf(subDate);
                            Timestamp submission_date = Utils.addTime(subm_date);
                            assignment.setSubmission_date(submission_date);

                            subinput = true;
                        } catch (Exception e) {
                            subinput = false;
                            System.err.println("Input Error: ");

                        }
                    }
                    boolean exinput = false;
                    while (!exinput) {
                        try {
                            System.out.println("Expiration Date(yyyy-mm-dd hh:mm:ss):");
                            String expDate = sc.nextLine();

                            Timestamp exp_date = Timestamp.valueOf(expDate);
                            Timestamp expiration_date = Utils.addTime(exp_date);
                            assignment.setExpiration_date(expiration_date);

                            exinput = true;
                        } catch (Exception e) {
                            exinput = false;
                            System.err.println("Input Error: ");

                        }
                    }

                    String stream = Assignment.insertStream();
                    assignment.setStream(stream);

                    pst.setString(1, assignment.getTitle());
                    pst.setString(2, assignment.getDescription_of_assignment());
                    pst.setTimestamp(3, assignment.getSubmission_date());
                    pst.setTimestamp(4, assignment.getExpiration_date());
                    pst.setString(5, assignment.getStream());
                    pst.executeUpdate();
                    System.out.println("YOU HAVE INSERTED AN ASSIGNMENT" + "\n" + assignment.toString());

                } catch (SQLException ex) {
                    Logger.getLogger(AssignmentDao.class
                            .getName()).log(Level.SEVERE, null, ex);

                } finally {
                    try {
                        pst.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(AssignmentDao.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        con.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(AssignmentDao.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

            case "no":
                String sql1 = "insert into assignment (title,description_of_assignment,stream) values(?,?,?)";
                try {
                    pst = con.prepareStatement(sql1);
                    System.out.println("Title:");
                    title = sc.nextLine();
                    assignment.setTitle(title);
                    System.out.println("Description:");
                    String description_of_assignment = sc.nextLine();
                    assignment.setDescription_of_assignment(description_of_assignment);
                    System.out.println("Stream:");
                    String stream = sc.nextLine();
                    assignment.setStream(stream);
                    pst.setString(1, assignment.getTitle());
                    pst.setString(2, assignment.getDescription_of_assignment());
                    pst.setString(3, assignment.getStream());
                    pst.executeUpdate();
                    System.out.println("YOU HAVE INSERTED AN ASSIGNMENT" + "\n" + assignment.toString());

                } catch (SQLException ex) {
                    Logger.getLogger(AssignmentDao.class
                            .getName()).log(Level.SEVERE, null, ex);

                } finally {
                    try {
                        pst.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(AssignmentDao.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        con.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(AssignmentDao.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

        }
    }

    public static void updateAssignmentTitle() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Please type the new title of the assignment");
        title = Utils.notNull(sc.nextLine());
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE assignment SET title='" + title + "'" + "WHERE assignment_id='" + assignment_id + "'";
        boolean change = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;

        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDao.class
                    .getName()).log(Level.SEVERE, null, ex);
            change = false;

        } finally {
            try {
                pst.close();

            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (change) {
            System.err.println("\n" + "ASSIGNMENT HAS BEEN UPDATED" + "\n");
        }
    }

    public static void updateAssignmentDescription() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please type the new description of the assignment");
        String description = Utils.notNull(sc.nextLine());
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE assignment SET description_of_assignment='" + description + "'" + "WHERE assignment_id='" + assignment_id + "'";
        boolean change = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;

        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDao.class
                    .getName()).log(Level.SEVERE, null, ex);
            change = false;

        } finally {
            try {
                pst.close();

            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (change) {
            System.err.println("\n" + "ASSIGNMENT HAS BEEN UPDATED" + "\n");
        }
    }

    public static void updateAssignmentSubDate() {

        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Please type the new submission date of the assignment (yyyy-mm-dd hh:mm:ss)");
            String subDate = sc.nextLine();
            Timestamp submission_date = Timestamp.valueOf(subDate);

            Connection con = DBUtils.getConnection();
            PreparedStatement pst = null;
            String sql = "UPDATE assignment SET submission_date='" + submission_date + "'" + "WHERE assignment_id='" + assignment_id + "'";
            boolean change = false;
            try {
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
                change = true;

            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class
                        .getName()).log(Level.SEVERE, null, ex);
                change = false;

            } finally {
                try {
                    pst.close();

                } catch (SQLException ex) {
                    Logger.getLogger(AssignmentDao.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(AssignmentDao.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (change) {
                System.err.println("ASSIGNMENT HAS BEEN UPDATED!");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("YOU HAVE TO USE THE FORMAT (yyyy-mm-dd hh:mm:ss)");
            System.err.println("Try again");
            updateAssignmentSubDate();
        }
    }

    public static void updateAssignmentExpDate() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Please type the new expiration date of the assignment (yyyy-mm-dd hh:mm:ss)");
            String expDate = sc.nextLine();
            Timestamp expiration_date = Timestamp.valueOf(expDate);

            Connection con = DBUtils.getConnection();
            PreparedStatement pst = null;
            String sql = "UPDATE assignment SET expiration_date='" + expiration_date + "'" + "WHERE assignment_id='" + assignment_id + "'";
            boolean change = false;
            try {
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
                change = true;

            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class
                        .getName()).log(Level.SEVERE, null, ex);
                change = false;

            } finally {
                try {
                    pst.close();

                } catch (SQLException ex) {
                    Logger.getLogger(AssignmentDao.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    con.close();

                } catch (SQLException ex) {
                    Logger.getLogger(AssignmentDao.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (change) {
                System.err.println("ASSIGNMENT HAS BEEN UPDATED!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("YOU HAVE TO USE THE FORMAT (yyyy-mm-dd hh:mm:ss)");
            System.err.println("Try again");
            updateAssignmentExpDate();
        }

    }

    public static void updateAssignmentStream() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please type the new stream of the assignment");
        String stream = Assignment.insertStream();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE assignment SET stream='" + stream + "'" + "WHERE assignment_id='" + assignment_id + "'";
        boolean change = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;

        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDao.class
                    .getName()).log(Level.SEVERE, null, ex);
            change = false;

        } finally {
            try {
                pst.close();

            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (change) {
            System.err.println("ASSIGNMENT HAS BEEN UPDATED");
        }
    }

    public static void upadateAssignment() throws ParseException {
        getAssignments();
        System.out.println("Please type the title of the assignment you want to update");
        checkIfAssignmentExists();
        Scanner sc = new Scanner(System.in);
        System.out.println("Please type the assignment_id of the assignment you want to update");
        assignment_id = Utils.onlyInteger();
        Assignment assignment = new Assignment();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select * from assignment where assignment_id=?";

        checkByTitleAndId();
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, assignment_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                assignment.setAssignment_id(rs.getInt(1));
                assignment.setTitle(rs.getString(2));
                assignment.setDescription_of_assignment(rs.getString(3));
                assignment.setSubmission_date(rs.getTimestamp(4));
                assignment.setExpiration_date(rs.getTimestamp(5));
                assignment.setStream(rs.getString(6));

            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDao.class
                    .getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();

            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }
        System.out.println("Which field do you want to update?" + "\n" + "1.Title" + "\n" + "2.Description" + "\n"
                + "3.Submission Date" + "\n" + "4.Expiration Date" + "\n" + "5.Stream" + "\n" + "6.Exit" + "\n");
        int fieldUpdate = 0;

        do {
            try {
                fieldUpdate = sc.nextInt();
                if ((fieldUpdate < 1) || (fieldUpdate > 6)) {
                    System.err.print("You must enter a number from 1 to 6!");

                } else {
                    switch (fieldUpdate) {
                        case 1:
                            updateAssignmentTitle();
                            Home.headmasterMenu();
                            break;
                        case 2:
                            updateAssignmentDescription();
                            Home.headmasterMenu();
                            break;
                        case 3:
                            updateAssignmentSubDate();
                            Home.headmasterMenu();
                            break;
                        case 4:
                            updateAssignmentExpDate();
                            Home.headmasterMenu();
                            break;
                        case 5:
                            updateAssignmentStream();
                            Home.headmasterMenu();
                            break;
                        case 6:
                            Home.headmasterMenu();
                            break;

                    }
                }
            } catch (InputMismatchException e) {
                System.err.println("You may only enter integers . Try again.");
                sc.next();

            }
        } while (sc.hasNext());

    }

    public static int fetchAssignmentId(String title) {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT assignment_id FROM assignment WHERE title='" + title + "'";

        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {

                assignment_id = rs.getInt("assignment_id");

            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentDao.class
                    .getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();

            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(AssignmentDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return assignment_id;
    }

    public static void checkByTitleAndId() {
        Scanner sc = new Scanner(System.in);
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        try {

            String sql = "SELECT assignment_id,title from assignment where title='" + title + "'"
                    + "AND assignment_id='" + assignment_id + "'";

            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            if (rs.next()) {
                assignment_id = rs.getInt("assignment_id");
                title = rs.getString("title");

            } else {
                System.err.println("This assignment does not exist. please try again: ");
                assignment_id = Utils.onlyInteger();
                checkByTitleAndId();

            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDao.class
                    .getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();

            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
