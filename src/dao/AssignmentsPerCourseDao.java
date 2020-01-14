/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import menus.HeadmasterMenu;
import menus.Home;
import model.Assignment;
import model.AssignmentsPerCourse;
import utils.DBUtils;

/**
 *
 * @author liana
 */
public class AssignmentsPerCourseDao {

    public static ArrayList<Assignment> getAssignmentsPerCourse() throws ParseException {
        CourseDao.getAllCourses();
        System.out.println("For which course you want to see the assignments?");
        CourseDao.checkIfCourseExists();
        String title1 = CourseDao.title;
        CourseDao.fetchCourseId(title1);
        ArrayList<Assignment> list = new ArrayList<>();

        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT * from assignment "
                + "inner join assignments_per_course on assignments_per_course.assignment_id=assignment.assignment_id\n"
                + "where assignments_per_course.course_id=" + CourseDao.course_id;
        boolean bool = true;
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            System.out.println("ASSIGNMENTS:" + "\n");
            while (rs.next()) {
                Assignment assignment = new Assignment();
                assignment.setAssignment_id(rs.getInt("assignment_id"));
                int assignment_id = rs.getInt("assignment_id");
                assignment.setTitle(rs.getString("title"));
                String title = rs.getString("title");
                assignment.setDescription_of_assignment(rs.getString(3));
                String description_of_assignment = rs.getString(3);
                assignment.setSubmission_date(rs.getTimestamp(4));
                Timestamp submission_date = rs.getTimestamp(4);
                assignment.setExpiration_date(rs.getTimestamp(5));
                Timestamp expiration_date = rs.getTimestamp(5);
                assignment.setStream(rs.getString("stream"));
                String stream = rs.getString("stream");
                if (rs.wasNull()) {
                    submission_date = null;
                    expiration_date = null;
                    Assignment a = new Assignment(assignment_id, title, description_of_assignment, stream);
                    list.add(a);
                    System.out.println(a.toString());
                } else {
                    list.add(assignment);
                    System.out.println(assignment.toString());
                }
                bool = rs.wasNull();

            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (bool) {
            System.err.println("THERE ARE NO ASSIGNMENTS YET!");
            HeadmasterMenu.headmasterMenu();
        }

        return list;

    }

    public static void assignAssignmentToCourse() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.err.println("LIST OF COURSES: " + "\n");
        CourseDao.getAllCourses();
        CourseDao.checkIfCourseExists();
        int course_id = CourseDao.course_id;
        System.out.println("LIST OF ASSIGNMENTS:" + "\n");
        AssignmentDao.getAssignments();
        AssignmentDao.checkIfAssignmentExists();
        int assignment_id = AssignmentDao.assignment_id;

        System.out.println("Are you sure you want to assign " + AssignmentDao.title + " to " + CourseDao.title + "?");
        String answer = sc.nextLine().toLowerCase();
        while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
            System.out.println("Please answer with yes or no");
            answer = sc.nextLine().toLowerCase();
        }
        switch (answer) {
            case "yes":
                Connection con = DBUtils.getConnection();
                PreparedStatement pst = null;
                String sql = "INSERT INTO assignments_per_course (course_id,assignment_id)"
                        + "values(?,?)";
                AssignmentsPerCourse ap = new AssignmentsPerCourse(course_id, assignment_id);
                try {
                    pst = con.prepareStatement(sql);
                    ap.setCourse_id(course_id);
                    ap.setAssignment_id(assignment_id);

                    pst.setInt(1, ap.getCourse_id());
                    pst.setInt(2, ap.getAssignment_id());
                    pst.executeUpdate();
                    System.err.println("SUCCESS!");

                } catch (SQLException ex) {
                    Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
                } finally {

                    try {
                        pst.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

        }
    }
//Students who have a particular assignment
    
    public static void studentPerAssignment() throws ParseException {
        boolean bool = true;
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select student.student_id,first_name,last_name,enroll_id from student "
                + "inner join assignment_per_student on "
                + "student.student_id=assignment_per_student.student_id\n"
                + "where assignment_per_student.assignment_id=" + AssignmentDao.assignment_id;
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                int student_id = rs.getInt("student_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                int enroll_id = rs.getInt("enroll_id");

                System.out.println("STUDENT ID: " + student_id + "\n"
                        + "FIRST NAME: " + first_name + "\n" + "LAST NAME: " + last_name + "\n"
                        + "ENROLL ID: " + enroll_id + "\n");
                bool = rs.wasNull();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (bool) {
            System.err.println("THERE ARE NO STUDENTS WHO HAVE BEEN ASSIGNED THIS!");
            HeadmasterMenu.headmasterMenu();
        }

    }
//    Assignments per course

    public static ArrayList<Assignment> showAssignmentForOneCourse(int course_id) throws ParseException {

        ArrayList<Assignment> list = new ArrayList<>();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        boolean bool = true;

        String sql = "SELECT * from assignment "
                + "inner join assignments_per_course on assignments_per_course.assignment_id=assignment.assignment_id\n"
                + "where assignments_per_course.course_id=" + course_id;
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            System.err.println("ASSIGNMENTS FOR THIS COURSE:"+"\n");
            while (rs.next()) {
                Assignment assignment = new Assignment();
                assignment.setAssignment_id(rs.getInt("assignment_id"));
                int assignment_id = rs.getInt("assignment_id");
                assignment.setTitle(rs.getString("title"));
                String title = rs.getString("title");
                assignment.setDescription_of_assignment(rs.getString(3));
                String description_of_assignment = rs.getString(3);
                assignment.setSubmission_date(rs.getTimestamp(4));
                Timestamp submission_date = rs.getTimestamp(4);
                assignment.setExpiration_date(rs.getTimestamp(5));
                Timestamp expiration_date = rs.getTimestamp(5);
                assignment.setStream(rs.getString("stream"));
                String stream = rs.getString("stream");
                list.add(assignment);
                System.out.println(assignment.toString());
                AssignmentPerStudentDao.showStudentsByAssignmentId(assignment_id);
                bool = rs.wasNull();

            }
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (bool) {
            System.out.println("THERE ARE NO ASSIGNMENTS YET!"+"\n");
            HeadmasterMenu.headmasterMenu();

        }
        return list;

    }

    public static void deleteAssignmentPerCourse(int course_id, int assignment_id) {
        Scanner sc = new Scanner(System.in);
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "delete from assignments_per_course where course_id=" + course_id
                + " and assignment_id=" + assignment_id;
               boolean change=false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;
        } catch (SQLException ex) {
            change=false;
            Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(AssignmentsPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(change){
            System.err.println("ASSIGNMENT HAS BEEN DELTED FROM THIS COURSE!");
        }
    }

}
