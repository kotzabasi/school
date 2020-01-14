/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menus;

import dao.TrainerDao;
import dao.TrainerPerCourseDao;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import static menus.Home.userLogin;

/**
 *
 * @author liana
 */
public class TrainerMenu {
    
    //    trainer menu: first and last names are required to keep in memory trainer_id

    public static void trainerMenu() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.err.println("INSERTING TRAINER MENU....");
        System.out.println("PLEASE TYPE YOUR NAME");
        TrainerDao.checkIfTrainerExists();
        String firstname = TrainerDao.firstname;
        String lastname = TrainerDao.lastname;
        int trainer_id = TrainerDao.fetchTrainerId(firstname, lastname);
        System.err.println("If you don't see COURSE, it means that you haven't been assigned to a course yet!");
        TrainerPerCourseDao.showCoursePerTrainer(trainer_id);
        trainerSubmenu();

    }
//    Trainer submenu

    public static void trainerSubmenu() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("");
        System.out.println("MAIN MENU:" + "\n"
                + "1.View students of your courses" + "\n"
                + "2.Mark assignments" + "\n" + "3.Assign an assignment to a student"
                + "\n" + "4.Exit" + "\n" + "PLEASE CHOOSE A NUMBER FROM 1 to 4: ");
        int choice = 0;
        do {
            try {
                choice = sc.nextInt();
                if ((choice < 1) || (choice > 4)) {
                    System.err.print("You must enter a number from 1 to 4!");

                } else {
                    switch (choice) {

                        case 1:
                            TrainerDao.getAllStudentsPerCourse();
                            trainerSubmenu();
                            break;
                        case 2:
                            TrainerPerCourseDao.markAssignments();
                            trainerSubmenu();
                            break;
                        case 3:
                            TrainerDao.assignAssignment();
                            trainerSubmenu();
                            break;
                        case 4:
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
