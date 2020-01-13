/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import menus.Home;
import utils.DBUtils;

/**
 *
 * @author liana
 */
public class HeadmasterDao {
    
//    first course is a new insert, second course is a field update (same enroll_id)

    public static void setSecondcourse(int enroll_id) throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("LIST OF COURSES:" + "\n");
        CourseDao.getAllCourses();
        System.out.println("Please type the title of the course you want to enroll the student in: ");
        CourseDao.checkIfCourseExists();
        String title = CourseDao.title;
        CourseDao.fetchCourseId(title);
        System.out.println("Are  you sure you want to enroll this student in " + title + "?" + "\n");
        System.out.println("IF A STUDENT HAS TWO COURSES ALREADY YOU WILL CHANGE THE SECOND ONE");
        System.out.println("Please answer with yes or no" + "\n");
        String answer = school.Utils.answerYesOrNo(sc.nextLine());
        boolean change = false;
        switch (answer) {
            case "yes":

                Connection con = DBUtils.getConnection();
                PreparedStatement pst = null;
                String sql = "UPDATE student_per_course SET secondcourse_id=" + CourseDao.course_id + " where enroll_id="
                        + StudentPerCourseDao.enroll_id;

                try {
                    pst = con.prepareStatement(sql);
                    pst.executeUpdate();
                    change = true;
                } catch (SQLException ex) {
                    Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
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

        }
        if (change) {
            System.err.println("SUCCESS!");
            System.out.println("\n");
        }
        Home.headmasterMenu();
    }
//delete Assignment Per Student Per course
    
    public static void deleteAssPerStPerCour() throws ParseException {
        Scanner sc = new Scanner(System.in);
        AssignmentPerStudentDao.showAssignmentPerStudentPerCourse();
        System.out.println("CHOOSE STUDENT: ");
        StudentDao.checkIfStudentExists();
        int student_id = StudentDao.student_id;
        System.out.println("CHOOSE ASSIGNMENT TO DELETE: ");
        AssignmentDao.checkIfAssignmentExists();
        AssignmentDao.fetchAssignmentId(AssignmentDao.title);

        System.out.println("Are you sure? - please answer with yes or no");
        String answerDelete = school.Utils.answerYesOrNo(sc.nextLine());
        if (answerDelete.equalsIgnoreCase("yes")) {
            AssignmentPerStudentDao.deleteAssignmentPerStudent(AssignmentDao.assignment_id, student_id);
        } else if (answerDelete.equalsIgnoreCase("no")) {
            Home.headmasterMenu();
        } else {
            System.out.println("You have to answer with yes or no");
        }

    }
}
