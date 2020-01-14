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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import menus.HeadmasterMenu;
import menus.Home;
import menus.TrainerMenu;
import model.Trainer;
import model.TrainerPerCourse;
import utils.DBUtils;

/**
 *
 * @author liana
 */
public class TrainerPerCourseDao {

//    List of trainers - select course first
    public static ArrayList<Trainer> getTrainersPerCourse() throws ParseException {
        CourseDao.getAllCourses();
        System.out.println("For which course you want to see a list of trainers?");
        CourseDao.checkIfCourseExists();
        String title = CourseDao.title;
        CourseDao.fetchCourseId(title);
        ArrayList<Trainer> list = new ArrayList<>();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT distinct * from trainer "
                + "inner join trainer_per_course on trainer_per_course.trainer_id=trainer.trainer_id\n"
                + "where trainer_per_course.course_id=" + CourseDao.course_id;
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                Trainer trainer = new Trainer();
                trainer.setTrainer_id(rs.getInt(1));
                int trainer_id = rs.getInt("trainer_id");
                trainer.setFirstname(rs.getString(2));
                String firstname = rs.getString("firstname");
                trainer.setLastname(rs.getString(3));
                String lastname = rs.getString("lastname");
                trainer.setSubject(rs.getString(4));
                String subject = rs.getString("subject_");
                list.add(trainer);
                System.out.println("TRAINER ID:" + trainer_id + "\n"
                        + "FIRST NAME: " + firstname + "\n" + "LAST NAME:" + lastname + "\n" + "SUBJECT: " + subject + "\n");

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
            System.out.println("THERE ARE NO TRAINERS IN THIS COURSE YET!" + "\n");
            try {
                HeadmasterMenu.headmasterMenu();
            } catch (ParseException ex) {
                Logger.getLogger(StudentPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return list;

    }

    public static void createTrainerPerCourse() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.err.println("LIST OF TRAINERS: " + "\n");
        TrainerDao.getAllTrainers();
        System.out.println("Type the name of the trainer you want to assign to a course");
        TrainerDao.checkIfTrainerExists();
        String firstname = TrainerDao.firstname;
        String lastname = TrainerDao.lastname;
        TrainerDao.fetchTrainerId(firstname, lastname);
        int trainer_id = TrainerDao.trainer_id;

        System.err.println("LIST OF COURSES: ");
        System.out.println("\n");
        CourseDao.getAllCourses();
        CourseDao.checkIfCourseExists();
        String title = CourseDao.title;
        CourseDao.fetchCourseId(title);
        int course_id = CourseDao.course_id;
        System.out.println("Are  you sure you want to assign " + firstname + " " + lastname + " to " + title + "? Please answer with yes or no");
        String answer = Utils.answerYesOrNo(sc.nextLine());
        PreparedStatement pst = null;
        Connection con = DBUtils.getConnection();
        switch (answer) {
            case "yes":
                String sql = "INSERT INTO trainer_per_course (course_id,trainer_id) VALUES (?,?)";
                try {
                    TrainerPerCourse ttp = new TrainerPerCourse();
                    pst = con.prepareStatement(sql);
                    ttp.setCourse_id(course_id);
                    ttp.setTrainer_id(trainer_id);

                    pst.setInt(1, ttp.getCourse_id());
                    pst.setInt(2, ttp.getTrainer_id());
                    pst.executeUpdate();
                    System.err.println("You have assigned " + firstname + " " + lastname + " to " + title);

                } catch (SQLException ex) {
                    System.err.println("THIS IS A DUPLICATE ENTRY!");
                    System.err.println("PLEASE TRY AGAIN:");
                    createTrainerPerCourse();
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
                break;
            case "no": {
                try {
                    HeadmasterMenu.headmasterMenu();
                } catch (ParseException ex) {
                    Logger.getLogger(TrainerPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
        }
    }

    public static void deleteTrainerPerCourse() throws ParseException {
        Scanner sc = new Scanner(System.in);
        getTrainersPerCourse();
        int course_id = CourseDao.course_id;
        System.out.println("Select a trainer to delete." + "\n" + "Please type first name and last name");
        TrainerDao.checkIfTrainerExists();
        String firstname = TrainerDao.firstname;
        String lastname = TrainerDao.lastname;
        TrainerDao.fetchTrainerId(firstname, lastname);
        int trainer_id = TrainerDao.trainer_id;
        System.out.println("Are  you sure you want to delete " + firstname + " " + lastname + " from this course?");
        String answer = Utils.answerYesOrNo(sc.nextLine());
        switch (answer) {
            case "yes":
                Connection con = DBUtils.getConnection();
                PreparedStatement pst = null;
                String sql = "DELETE FROM trainer_per_course WHERE course_id=" + course_id
                        + " AND trainer_id=" + trainer_id;
                boolean result = false;
                try {
                    pst = con.prepareStatement(sql);
                    pst.executeUpdate();
                    result = true;
                } catch (SQLException ex) {
                    Logger.getLogger(TrainerPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
                    result = false;
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
                if (result) {
                    System.err.println("TRAINER " + TrainerDao.firstname + " "
                            + TrainerDao.lastname + " HAS BEEN DELETED FROM " + CourseDao.title);
                }

                break;
            case "no": {
                try {
                    HeadmasterMenu.headmasterMenu();
                } catch (ParseException ex) {
                    Logger.getLogger(TrainerPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
        }
    }
//show courses per trainer

    public static String showCoursePerTrainer(int trainer_id) {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT distinct title as COURSE from course inner join trainer_per_course on "
                + "course.course_id=trainer_per_course.course_id\n"
                + "where trainer_per_course.trainer_id=" + trainer_id;
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {
                String title = rs.getString(1);
                System.err.println("COURSE: " + title);
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
        return "";
    }

//    see submitted assignments  (Trainer Menu)
    public static void seeStudentsSubmittedAssignmentsPerCourse() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("CHOOSE COURSE");
        CourseDao.checkIfCourseExists();
        Connection con = DBUtils.getConnection();
        boolean bool = true;
        PreparedStatement pst = null;
        String sql = "select title as ASSIGNMENT, first_name,last_name from (assignment,student) "
                + "inner join assignment_per_student on "
                + "assignment_per_student.assignment_id=assignment.assignment_id "
                + "AND assignment_per_student.student_id=student.student_id "
                + "inner join assignments_per_course on "
                + "assignments_per_course.assignment_id=assignment.assignment_id and assignments_per_course.course_id=" + CourseDao.course_id
                + " WHERE assignment_per_student.submitted=1";
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                String title = rs.getString(1);
                String first_name = rs.getString(2);
                String last_name = rs.getString(3);
                System.out.println("ASSIGNMENT: " + title + "\n" + "FIRST NAME: " + first_name
                        + "\n" + "LAST NAME: " + last_name + "\n");
                bool = rs.wasNull();
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
        if (bool) {
            System.out.println("THERE ARE NO SUBMITTED ASSIGNMENTS!!");
            TrainerMenu.trainerSubmenu();

        }

    }
//  for Trainer Menu

    public static void markAssignments() throws ParseException {
        Scanner sc = new Scanner(System.in);
        seeStudentsSubmittedAssignmentsPerCourse();
        System.out.println("CHOOSE STUDENT: ");
        StudentDao.checkIfStudentExists();
        System.out.println("CHOOSE ASSIGNMENT: ");
        AssignmentDao.checkIfAssignmentExists();
        fetchOralAndTotalMark();
        System.out.println("WOULD YOU LIKE TO SET ORAL MARK,TOTAL MARK OR BOTH?");
        System.out.println("PLEASE TYPE ORAL,TOTAL OR BOTH");
        String answer = Utils.notNull(sc.nextLine().toLowerCase());
        while (!answer.equalsIgnoreCase("total") && !answer.equalsIgnoreCase("oral")
                && !answer.equalsIgnoreCase("both")) {
            System.out.println("Please type ORAL,TOTAL or BOTH");
            answer = sc.nextLine().toLowerCase();
        }
        switch (answer) {
            case "oral":
                AssignmentPerStudentDao.updateOralMark();
                TrainerMenu.trainerSubmenu();
                break;
            case "total":
                AssignmentPerStudentDao.updateTotalMark();
                TrainerMenu.trainerSubmenu();
                break;
            case "both":
                System.out.println("ORAL MARK:");
                AssignmentPerStudentDao.updateOralMark();
                System.out.println("TOTAL MARK: ");
                AssignmentPerStudentDao.updateTotalMark();
                TrainerMenu.trainerSubmenu();

                break;
        }
    }

    public static void fetchOralAndTotalMark() {

        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        boolean bool = true;
        String sql = "SELECT oral_mark,total_mark from assignment_per_student where assignment_id="
                + AssignmentDao.assignment_id + " and student_id=" + StudentDao.student_id;
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                int oral_mark = rs.getInt(1);
                int total_mark = rs.getInt(2);
                bool = rs.wasNull();
                if (bool) {
                    System.out.println("ORAL MARK = null" + "\n" + "TOTAL MARK = null");
                } else {
                    System.out.println("ORAL MARK = null " + oral_mark + "\n" + "TOTAL MARK =" + total_mark + "\n");
                }
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

    }
}
