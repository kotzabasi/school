/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import school.Utils;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import menus.Home;
import model.Student;
import utils.DBUtils;
import model.Trainer;
import model.User;

/**
 *
 * @author liana
 */
public class TrainerDao {

    public static int trainer_id;
    public static String firstname;
    public static String lastname;

    public static ArrayList<Trainer> getAllTrainers() {
        ArrayList<Trainer> list = new ArrayList<>();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select * from trainer";

        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {

                int trainer_id = rs.getInt("trainer_id");
                String first_name = rs.getString("firstname");
                String last_name = rs.getString("lastname");
                String subject = rs.getString("subject_");

                Trainer trainer = new Trainer(trainer_id, first_name, last_name, subject);
                list.add(trainer);
                System.out.println(trainer.toString());
                TrainerPerCourseDao.showCoursePerTrainer(rs.getInt("trainer_id"));
                System.out.println();

            }
        } catch (SQLException ex) {
            Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return list;

    }

    public static void insertTrainer() {
        Connection con = DBUtils.getConnection();
        PreparedStatement insertTrainer = null;
        PreparedStatement insertUser = null;

        String trainer = "INSERT into trainer (firstname,lastname,subject_) VALUES (?,?,?)";
        String user = "INSERT into user (username,password,role) VALUES (?,?,'trainer')";
        Trainer t = new Trainer();
        User u = new User();
         Scanner sc = new Scanner(System.in);
            checkCreatedTrainer(firstname, lastname);

        try {
            con.setAutoCommit(false);
            insertTrainer = con.prepareStatement(trainer);
            insertUser = con.prepareStatement(user);
            
            t.setFirstname(firstname);
            t.setLastname(lastname);
            u.setUsername(lastname);
            u.setPassword(lastname);
            
            System.out.println("Subject:");
            String subject = Utils.notNull(sc.nextLine());
            t.setSubject(subject);

            insertTrainer.setString(1, t.getFirstname());
            insertTrainer.setString(2, t.getLastname());
            insertTrainer.setString(3, t.getSubject());

            insertUser.setString(1, u.getUsername());
            insertUser.setString(2, u.getPassword());

            insertTrainer.execute();
            insertUser.execute();
            con.commit();

            System.err.println("YOU HAVE INSERTED A TRAINER:");
            System.out.println("\n");
            System.out.println(t.toString());

        } catch (SQLException ex) {
            Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                insertTrainer.close();
                insertUser.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        fetchTrainerId(firstname, lastname);
        setTrainerIdToUser(trainer_id);

    }

    public static void checkIfTrainerExists() {
        Connection con = DBUtils.getConnection();
        Scanner sc = new Scanner(System.in);
        PreparedStatement pst = null;
        try {
            System.out.println("First name:");
            firstname = Utils.notNull(sc.nextLine());
            System.out.println("Last name:");
            lastname = Utils.notNull(sc.nextLine());

            String sql = "SELECT trainer_id,firstname,lastname from trainer where firstname='" + firstname + "'"
                    + "AND lastname='" + lastname + "'";

            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            if (rs.next()) {
                trainer_id = rs.getInt("trainer_id");
                System.out.println("\n" + "Trainer ID: " + trainer_id + "\n" + "First Name: " + firstname + "\n" + "Last Name: " + lastname);
            } else {
                System.err.println("this name does not exist. please try again");
                checkIfTrainerExists();
            }

        } catch (SQLException ex) {
            Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void setTrainerIdToUser(int trainer_id) {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE user SET trainer_id='" + trainer_id + "'" + "WHERE username='" + lastname + "'" + "AND password='" + lastname + "'";
        boolean change = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void deleteTrainer() {
        getAllTrainers();
        System.out.println("Please type the first and the last name of the entry you want to delete");
        checkIfTrainerExists();
        Connection con = DBUtils.getConnection();

        PreparedStatement deleteTrainer = null;
        PreparedStatement deleteUser = null;

        String trainer = "DELETE FROM trainer WHERE trainer_id=?";
        String user = "DELETE FROM user WHERE trainer_id=?";
        boolean result = false;
        try {
            con.setAutoCommit(false);
            deleteTrainer = con.prepareStatement(trainer);
            deleteUser = con.prepareStatement(user);
            Scanner sc = new Scanner(System.in);
            System.out.println("Please type the trainer_id if  you want to delete this trainer");
            trainer_id = sc.nextInt();
            checkByIdAndLastname();

            deleteTrainer.setInt(1, trainer_id);
            deleteUser.setInt(1, trainer_id);
            deleteTrainer.execute();
            deleteUser.execute();
            con.commit();
            result = true;

        } catch (SQLException ex) {
            Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            result = false;

        } finally {
            try {
                deleteTrainer.close();
                deleteUser.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (result) {
                System.err.println("TRAINER HAS BEEN DELETED");
            }
        }

    }

    public static void showByTrainerId() {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT*FROM trainer WHERE trainer_id='" + trainer_id + "'";
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {

                trainer_id = rs.getInt("trainer_id");
                firstname = rs.getString("firstname");
                lastname = rs.getString("lastname");
                String subject = rs.getString("subject_");
                Trainer trainer = new Trainer(trainer_id, firstname, lastname, subject);
                System.out.println(trainer.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static int fetchTrainerId(String firstname, String lastname) {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT * FROM trainer WHERE firstname='" + firstname + "'" + "AND lastname='" + lastname + "'";
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {

                trainer_id = rs.getInt("trainer_id");

            }
        } catch (SQLException ex) {
            Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return trainer_id;
    }

    public static void updateTrainerFirstName(Trainer trainer) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please type the new first name");
        firstname = Utils.notNull(sc.nextLine());
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE trainer SET firstname='" + firstname + "'" + "WHERE trainer_id='" + trainer_id + "'";
        boolean change = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;
        } catch (SQLException ex) {
            Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            change = false;
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (change) {
            System.err.println("\n" + "TRAINER HAS BEEN UPDATED" + "\n");
            showByTrainerId();

        }

    }

    public static void updateTrainerLastName(Trainer trainer) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please type the new last name");
        lastname = Utils.notNull(sc.nextLine());
        Connection con = DBUtils.getConnection();
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;
        PreparedStatement pst3 = null;
        String sql1 = "UPDATE trainer SET lastname='" + lastname + "'" + "WHERE trainer_id='" + trainer_id + "'";
        String sql2 = "UPDATE user SET username='" + lastname + "'" + "WHERE trainer_id='" + trainer_id + "'";
        String sql3 = "UPDATE user SET password='" + lastname + "'" + "WHERE trainer_id='" + trainer_id + "'";
        boolean change = false;
        try {
            con.setAutoCommit(false);
            pst1 = con.prepareStatement(sql1);
            pst2 = con.prepareStatement(sql2);
            pst3 = con.prepareStatement(sql3);
            pst1.execute();
            pst2.execute();
            pst3.execute();
            con.commit();

            change = true;
        } catch (SQLException ex) {
            Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            change = false;
        } finally {
            try {
                pst1.close();
                pst2.close();
                pst3.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (change) {
            System.err.println("\n" + "TRAINER HAS BEEN UPDATED" + "\n");
            showByTrainerId();

        }
    }

    public static void updateTrainerSubject(Trainer trainer) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please type the new subject: ");
        String subject = Utils.notNull(sc.nextLine());
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE trainer SET subject_='" + subject + "'" + "WHERE trainer_id='" + trainer_id + "'";
        boolean change = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;
        } catch (SQLException ex) {
            Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            change = false;
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (change) {
            System.err.println("\n" + "TRAINER HAS BEEN UPDATED" + "\n");
            showByTrainerId();

        }
    }

    public static void updateTrainer() throws ParseException {
        getAllTrainers();
        System.out.println("Please type the first name and  last name of the entry you want to update: ");
        checkIfTrainerExists();
        Scanner sc = new Scanner(System.in);
        System.out.println("Please type the trainer_id of the entry you want to update: ");
        checkByIdAndLastname();
        Trainer trainer = new Trainer();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select * from trainer where trainer_id=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, trainer_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                trainer.setTrainer_id(rs.getInt(1));
                trainer.setFirstname(rs.getString(2));
                trainer.setLastname(rs.getString(3));
                trainer.setSubject(rs.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        System.out.println("Which field do you want to update?" + "\n" + "1.First Name" + "\n" + "2.Last Name" + "\n"
                + "3.Subject" + "\n" + "4.Exit" + "\n");

        int fieldUpdate = 0;
        do {
            try {
                fieldUpdate = sc.nextInt();
                if ((fieldUpdate < 1) || (fieldUpdate > 4)) {
                    System.err.print("You must enter a number from 1 to 4!");

                } else {

                    switch (fieldUpdate) {
                        case 1:
                            updateTrainerFirstName(trainer);
                            Home.headmasterMenu();
                            break;
                        case 2:
                            updateTrainerLastName(trainer);
                            Home.headmasterMenu();
                            break;
                        case 3:
                            updateTrainerSubject(trainer);
                            Home.headmasterMenu();
                            break;
                        case 4:
                            Home.headmasterMenu();

                    }
                }
            } catch (InputMismatchException e) {
                System.err.println("You may only enter integers . Try again.");
                sc.next();

            }
        } while (sc.hasNext());

    }

    public static void checkByIdAndLastname() {

        Scanner sc = new Scanner(System.in);
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        try {
            trainer_id = Utils.onlyInteger();
            String sql = "SELECT trainer_id,lastname from trainer where lastname='" + lastname + "'"
                    + "AND trainer_id='" + trainer_id + "'";

            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            if (rs.next()) {
                trainer_id = rs.getInt("trainer_id");
                lastname = rs.getString("lastname");

            } else {
                System.err.println("This user does not exist. please try again: ");
                checkByIdAndLastname();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }


    public static ArrayList<Student> getAllStudentsPerCourse() {
        System.out.println("For which course you want to see a list of students?");
        CourseDao.checkIfCourseExists();
        String title = CourseDao.title;
        CourseDao.fetchCourseId(title);
        ArrayList<Student> list = new ArrayList<>();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT distinct student.student_id,student.first_name,student.last_name from (course,student) "
                + "inner join student_per_course on student_per_course.student_id=student.student_id\n"
                + "where student_per_course.course_id=" + CourseDao.course_id;
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {

                Student student = new Student();
                student.setStudent_id(rs.getInt(1));
                int student_id = rs.getInt(1);
                student.setFirst_name(rs.getString(2));
                String fname = rs.getString(2);
                student.setLast_name(rs.getString(3));
                String lname = rs.getString(3);

                list.add(student);
                System.out.println("STUDENT ID:" + student_id + "\n"
                        + "FIRST NAME: " + fname + "\n" + "LAST NAME:" + lname + "\n");

            }

        } catch (SQLException ex) {
            Logger.getLogger(TrainerPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (list.isEmpty()) {
            System.out.println("THERE ARE NO STUDENTS IN THIS COURSE YET!" + "\n");
        }
        return list;

    }

    public static void checkCreatedTrainer(String firstname, String lastname) {
        Connection con = DBUtils.getConnection();
        Scanner sc = new Scanner(System.in);
        System.out.println("FIRST NAME: ");
        TrainerDao.firstname = Utils.notNull(sc.nextLine());
        System.out.println("LAST NAME: ");
        TrainerDao.lastname = Utils.notNull(sc.nextLine());

        PreparedStatement pst = null;
        String sql = "SELECT trainer_id,firstname,lastname from trainer where firstname='" + TrainerDao.firstname + "'"
                + "AND lastname='" + TrainerDao.lastname + "'";
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                trainer_id = rs.getInt("trainer_id");
                System.err.print("This trainer already exists!");
                System.out.print("Trainer ID: " + trainer_id + "\n");
                System.out.println("\n");
                System.err.println("Try again: ");
                checkCreatedTrainer(firstname, lastname);

            }
        } catch (SQLException ex) {
            Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(TrainerDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}