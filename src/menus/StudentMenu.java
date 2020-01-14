/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

import dao.AssignmentDao;
import dao.AssignmentPerStudentDao;
import dao.StudentDao;
import dao.StudentPerCourseDao;
import dao.TrainerPerCourseDao;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import static menus.Home.userLogin;

/**
 *
 * @author liana
 */
public class StudentMenu {

    // Student menu: first name & last name are required to open the submenu (in order to keep in memory student_id)
    public static void studentMenu() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.err.println("INSERTING STUDENT MENU....");
        System.out.println("PLEASE TYPE YOUR NAME");
        StudentDao.checkIfStudentExists();
        String firstname = StudentDao.first_name;
        String lastname = StudentDao.last_name;
        StudentDao.fetchStudentId(firstname, lastname);
        int student_id = StudentDao.student_id;
        StudentPerCourseDao.getEnrollId(student_id);
        StudentPerCourseDao.showCourseEnrolled(student_id);
        subMenu();
    }
//Student submenu

    public static void subMenu() throws ParseException {
        Scanner sc = new Scanner(System.in);
        int student_id = StudentDao.student_id;

        System.out.println("\n" + "MAIN MENU:" + "\n" + "1.Enroll to a course" + "\n" + "2.See your daily schedule" + "\n"
                + "3.See dates of submission for  your assignments and submit assignment" + "\n"
                + "4.See grades of your assignments" + "\n"
                + "5.Exit" + "\n" + "PLEASE CHOOSE A NUMBER FROM 1 to 5: ");
        int choice = 0;

        do {
            try {
                choice = sc.nextInt();
                if ((choice < 1) || (choice > 5)) {
                    System.err.print("You must enter a number from 1 to 5!");

                } else {
                    switch (choice) {
                        case 1:
                            StudentPerCourseDao.enrollToCourse(student_id);
                            subMenu();
                            break;
                        case 2:
                            StudentPerCourseDao.studentSchedule();
                            subMenu();
                            break;
                        case 3:
                            AssignmentPerStudentDao.getAssignmentPerStudent(student_id);
                            AssignmentPerStudentDao.submitAssignmet();
                            subMenu();
                            break;
                        case 4:
                            AssignmentDao.checkIfAssignmentExists();
                            int assignment_id = AssignmentDao.assignment_id;
                            StudentPerCourseDao.showMarksOfAssignment(assignment_id, student_id);
                            subMenu();
                            break;
                        case 5:
                            userLogin();
                            break;
                    }
                }
            } catch (InputMismatchException e) {
                System.err.println("You may only enter integers . Try again.");
                sc.next();

            }
        } while (sc.hasNext());

    }

}
