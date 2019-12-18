/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import school.Utils;
import static dao.StudentDao.student_id;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import menus.Home;
import model.AssignmentPerStudent;
import utils.DBUtils;

/**
 *
 * @author liana
 */
public class AssignmentPerStudentDao {

    public static void assignAssignmentToStudent(int assignment_id, int student_id) {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        boolean bool = true;
        String sql = "insert into assignment_per_student (assignment_id,student_id) values (?,?)";
        try {
            AssignmentPerStudent aps = new AssignmentPerStudent();
            pst = con.prepareStatement(sql);
            aps.setAssignment_id(assignment_id);
            aps.setStudent_id(student_id);

            pst.setInt(1, aps.getAssignment_id());
            pst.setInt(2, aps.getStudent_id());
            pst.executeUpdate();
            System.out.println("YOU HAVE ASSIGNED AN ASSIGNMENT TO STUDENT!");

        } catch (SQLException ex) {
            bool = false;
            System.err.println("The student has been already assigned to this assignment! Try again");
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void getAssignmentPerStudent(int student_id) {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        boolean bool = true;
        String sql = "SELECT title,submission_date,expiration_date,submitted from assignment inner join "
                + "assignment_per_student\n"
                + "on assignment_per_student.assignment_id=assignment.assignment_id "
                + "where assignment_per_student.student_id=" + student_id;
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {
                String title = rs.getString("title");
                Timestamp submission_date = rs.getTimestamp("submission_date");
                Timestamp expiration_date = rs.getTimestamp("expiration_date");
                Utils.getDaysBetweenDates(expiration_date);
                boolean submitted = rs.getBoolean("submitted");
                System.out.println("\n");
                System.out.println("ASSIGNMENT: " + title + "\n" + "SUBMISSION DATE: " + Utils.substractTime(submission_date) + "\n"
                        + "EXPIRATION DATE: " + Utils.substractTime(expiration_date) + "\n" + "SUBMITTED: "
                        + Utils.sumbitted(submitted) + "\n");
                bool = rs.wasNull();

            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (bool) {
            System.out.println("THERE ARE NO ASSIGNMENTS YET!");
            try {
                Home.headmasterMenu();
            } catch (ParseException ex) {
                Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void showStudentsByAssignmentId(int assignment_id) {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        boolean bool = true;
        String sql = "select first_name,last_name from student inner join assignment_per_student on "
                + "assignment_per_student.student_id=student.student_id \n"
                + "where assignment_per_student.assignment_id=" + assignment_id;
        System.err.println("STUDENTS WHO HAVE BEEN ASSIGNED THIS ASSIGNMENT: ");
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                bool = rs.wasNull();
                System.out.println("FIRST NAME: " + firstname + "\n" + "LAST NAME: " + lastname + "\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (bool) {
            System.err.println("THIS ASSIGNMENT HAS NOT BEEN ASSIGNED TO ANY STUDENT YET!" + "\n");

        }

    }

    public static void assignAssignmentPerStudentPerCourse() throws ParseException {

        Scanner sc = new Scanner(System.in);
        System.err.println("CHOOSE A COURSE TO SEE ITS LIST OF STUDENTS AND ASSIGNMENTS" + "\n"
                + "AND THEN TYPE THE NAME OF THE STUDENT YOU WANT TO ASSIGN THE ASSIGNMENT TO");
        CourseDao.getAllCourses();
        CourseDao.checkIfCourseExists();
        StudentPerCourseDao.showStudentsByCourseId(CourseDao.course_id);
        AssignmentsPerCourseDao.showAssignmentForOneCourse(CourseDao.course_id);
        System.out.println("SELECT A STUDENT TO ASSIGN AN ASSIGNMENT. " + "\n"
                + "TYPE THE NAME OF THE STUDENT:");

        StudentDao.checkIfStudentExists();
        String first_name = StudentDao.first_name;
        String last_name = StudentDao.last_name;
        int student_id = StudentDao.fetchStudentId(first_name, last_name);
        System.out.println("\n");
        System.err.println("CHOOSE WHICH ASSIGNMENT YOU WILL ASSIGN");
        AssignmentDao.checkIfAssignmentExists();
        String titlea = AssignmentDao.title;
        int assignment_id = AssignmentDao.fetchAssignmentId(titlea);
        AssignmentPerStudentDao.showStudentsByAssignmentId(assignment_id);

        System.out.println("ARE YOU SURE?");
        String answer1 = sc.nextLine().toLowerCase();

        while (!answer1.equalsIgnoreCase("yes") && !answer1.equalsIgnoreCase("no")) {
            System.out.println("Please answer with yes or no");
            answer1 = sc.nextLine().toLowerCase();
        }
        if (answer1.equalsIgnoreCase("yes")) {
            AssignmentPerStudentDao.assignAssignmentToStudent(assignment_id, student_id);
        } else {
            try {
                Home.headmasterMenu();
            } catch (ParseException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void showAssignmentPerStudentPerCourse() {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT distinct first_name,last_name,assignment.title as ASSIGNMENT,course.title as COURSE from (student,assignment,course) \n"
                + "inner join assignment_per_student on assignment_per_student.assignment_id=assignment.assignment_id\n"
                + "and assignment_per_student.student_id=student.student_id inner join assignments_per_course on assignments_per_course.course_id=course.course_id\n"
                + "and assignments_per_course.assignment_id=assignment.assignment_id\n"
                + "inner join student_per_course on student_per_course.student_id=student.student_id and student_per_course.course_id=course.course_id\n"
                + "order by last_name;";
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            System.err.println("ALL STUDENTS WHO HAVE ONE OR MORE ASSIGNMENTS "
                    + "AND THE RELEVANT COURSES: ");
            System.out.println();

            while (rs.next()) {
                String firstname = rs.getString(1);
                String lastname = rs.getString(2);
                String assignment = rs.getString(3);
                String course = rs.getString(4);
                System.out.println("FIRST NAME: " + firstname + "\n" + "LAST NAME: " + lastname + "\n"
                        + "ASSIGNMENT: " + assignment + "\n" + "COURSE: " + course + "\n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void deleteAssignmentPerStudent(int assignment_id, int student_id) {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "DELETE from assignment_per_student where assignment_id=" + assignment_id + " and student_id=" + student_id;
        boolean result = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            result = true;

        } catch (SQLException ex) {
            Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (result) {
            System.err.println("ASSIGNMENT HAS BEEN DELETED");
        }

    }

    public static void submitAssignmet() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("WOULD YOU LIKE TO SUBMIT AN ASSIGNMENT?");
        String answer = Utils.answerYesOrNo(sc.nextLine());
        boolean submit = false;
        switch (answer) {
            case "yes":
                System.out.println("PLEASE TYPE THE TITLE");
                AssignmentDao.checkIfAssignmentExists();
                Connection con = DBUtils.getConnection();
                PreparedStatement pst = null;
                String sql = "UPDATE assignment_per_student set submitted=1 where student_id=" + StudentDao.student_id + " and assignment_id="
                        + AssignmentDao.assignment_id;
                assignmentPerStExists(StudentDao.student_id, AssignmentDao.assignment_id);
                try {
                    pst = con.prepareStatement(sql);
                    pst.executeUpdate();
                    submit = true;

                } catch (SQLException ex) {
                    Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
                    Home.subMenu();

                } finally {
                    try {
                        pst.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AssignmentPerStudentDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (submit) {
                    System.err.println("YOU HAVE SUBMITTED AN ASSIGNMENT!");
                }
                break;
            case "no":
                Home.subMenu();
                break;
        }
    }

    public static void assignmentPerStExists(int student_id, int assignment_id) throws ParseException {
        Connection con = DBUtils.getConnection();
        Scanner sc = new Scanner(System.in);
        PreparedStatement pst = null;
        String sql = "SELECT submitted from assignment_per_student where student_id=" + student_id + " and assignment_id="
                + assignment_id;
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            if (rs.next()) {
                boolean submitted = rs.getBoolean("submitted");
            } else {
                System.err.println("The information you inserted is incorrect.Please try again");
                submitAssignmet();
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void updateOralMark() {
        Scanner sc = new Scanner(System.in);
        System.out.println("PLEASE TYPE THE MARK:");
        int oral_mark = Utils.onlyInteger();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE assignment_per_student set oral_mark =" + oral_mark
                + " WHERE student_id=" + StudentDao.student_id + " and assignment_id=" + AssignmentDao.assignment_id;
        boolean change = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;
        } catch (SQLException ex) {
            Logger.getLogger(StudentPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            change = false;

        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (change) {
            System.err.println("SUCCESS!");
        }
    }

    public static void updateTotalMark() {
        Scanner sc = new Scanner(System.in);
        System.out.println("PLEASE TYPE THE MARK:");
        int total_mark = Utils.onlyInteger();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE assignment_per_student set total_mark =" + total_mark
                + " WHERE student_id=" + StudentDao.student_id + " and assignment_id=" + AssignmentDao.assignment_id;
        boolean change = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;
        } catch (SQLException ex) {
            Logger.getLogger(StudentPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            change = false;

        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (change) {
            System.err.println("SUCCESS!");
        }
    }

}
