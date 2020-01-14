/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

import dao.AssignmentDao;
import dao.AssignmentPerStudentDao;
import dao.AssignmentsPerCourseDao;
import dao.CourseDao;
import dao.HeadmasterDao;
import dao.ScheduleDao;
import dao.StudentDao;
import dao.StudentPerCourseDao;
import dao.TrainerDao;
import dao.TrainerPerCourseDao;
import dao.UserDao;
import java.text.ParseException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author liana
 */
public class Home {
    
    //Home login page username & password the last name of the user (except admin, headmaster)

    public static String username;
    static Date current = new Date();

    public static void userLogin() throws ParseException {
        System.err.println(current);
        Scanner sc = new Scanner(System.in);
        System.err.println("LOG IN PAGE");
        System.out.println("\n");
        System.out.println("WELCOME" + "\n" + "USERNAME: ");
        username = sc.nextLine();
        UserDao.checkIfUsernameExists(username);
        UserDao.checkPassword();

    }
}
 



   