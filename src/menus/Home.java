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

    public static void studentMenu() throws ParseException {
        System.err.println(current);
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

    public static void subMenu() throws ParseException {
        Scanner sc = new Scanner(System.in);
        int student_id = StudentDao.student_id;
        System.out.println("\n" + "MAIN MENU:" + "\n" + "1.Enroll to a course" + "\n" + "2.See your daily schedule" + "\n"
                + "3.See dates of submission for  your assignments and submit assignment"
                + "\n" + "4.Exit" + "\n" + "PLEASE CHOOSE A NUMBER FROM 1 to 4: ");
        int choice = 0;

        do {
            try {
                choice = sc.nextInt();
                if ((choice < 1) || (choice > 4)) {
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

    public static void trainerSubmenu() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("");
        System.out.println("MAIN MENU:" + "\n"
                + "1.View students of your courses" + "\n"
                + "2.Mark assignments" + "\n" + "3.Exit" + "\n" + "PLEASE CHOOSE A NUMBER FROM 1 to 3: ");
        int choice = 0;
        do {
            try {
                choice = sc.nextInt();
                if ((choice < 1) || (choice > 3)) {
                    System.err.print("You must enter a number from 1 to 3!");

                } else {
                    switch (choice) {

                        case 1:
                            TrainerDao.getAllStudentsPerCourse();
                            trainerSubmenu();
                            break;
                        case 2:
                            TrainerPerCourseDao.markAssignments();
                            trainerMenu();
                            break;
                        case 3:
                            userLogin();
                    }

                }
            } catch (InputMismatchException e) {
                System.err.println("You may only enter integers . Try again.");
                sc.next();

            }
        } while (sc.hasNext());
        

    }

    public static void headmasterMenu() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.print("MAIN MENU:" + "\n" + "1.Courses" + "\n" + "2.Students" + "\n" + "3.Trainers" + "\n" + "4.Assignments" + "\n"
                + "5.Students per Course" + "\n" + "6.Trainers per Course" + "\n" + "7.Assignments per Course" + "\n"
                + "8.Schedule per Course" + "\n" + "9.Exit" + "\n" + "PLEASE CHOOSE A NUMBER FROM 1 to 9: ");
        int choiceMenu = 0;
        do {
            try {
                choiceMenu = sc.nextInt();
                if ((choiceMenu < 1) || (choiceMenu > 9)) {
                    System.err.print("You must enter a number from 1 to 9!");

                } else {

                    switch (choiceMenu) {
                        case 1:
                            System.out.println("COURSE MENU:" + "\n" + "1.Create Course" + "\n" + "2.Update Course" + "\n" + "3.Delete Course" + "\n" + "4.See List of all courses" + "\n"
                                    + "5.Return to Main Menu" + "\n" + "PLEASE CHOOSE A NUMBER FROM 1 TO 5: ");
                            int courseChoice = 0;
                            do {
                                try {
                                    courseChoice = sc.nextInt();
                                    if ((courseChoice < 1) || (courseChoice > 5)) {
                                        System.err.print("You must enter a number from 1 to 5!");

                                    } else {

                                        switch (courseChoice) {
                                            case 1:
                                                CourseDao.insertCourse();
                                                headmasterMenu();
                                                break;
                                            case 2:
                                                CourseDao.updateCourse();
                                                headmasterMenu();
                                                break;
                                            case 3:
                                                CourseDao.deleteCourse();
                                                headmasterMenu();
                                                break;
                                            case 4:
                                                CourseDao.getAllCourses();
                                                System.out.println();
                                            case 5:
                                                headmasterMenu();
                                                break;
                                        }
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("You may only enter integers . Try again.");
                                    sc.next();

                                }
                            } while (sc.hasNext());

                        case 2:
                            System.out.println("STUDENT MENU:" + "\n" + "1.Create student" + "\n" + "2.Update Student" + "\n" + "3.Delete Student" + "\n" + "4.See List of ALL students" + "\n"
                                    + "5.Return to Main Menu" + "\n" + "PLEASE CHOOSE A NUMBER FROM 1 TO 5: ");

                            int studentChoice = 0;
                            do {
                                try {
                                    studentChoice = sc.nextInt();
                                    if ((studentChoice < 1) || (studentChoice > 5)) {
                                        System.err.print("You must enter a number from 1 to 5!");

                                    } else {

                                        switch (studentChoice) {
                                            case 1:
                                                StudentDao.insertStudent();
                                                headmasterMenu();
                                                break;
                                            case 2:
                                                StudentDao.updateStudent();
                                                headmasterMenu();
                                                break;
                                            case 3:
                                                StudentDao.deleteStudent();
                                                headmasterMenu();
                                                break;
                                            case 4:
                                                StudentDao.getAllStudents();
                                                headmasterMenu();
                                            case 5:
                                                headmasterMenu();

                                                break;
                                        }
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("You may only enter integers . Try again.");
                                    sc.next();

                                }
                            } while (sc.hasNext());

                        case 3:
                            System.out.println("\n");
                            System.out.println("TRAINER MENU:" + "\n" + "1.Create Trainer" + "\n" + "2.Update Trainer" + "\n" + "3.Delete Trainer" + "\n" + "4.See a list of ALL trainers" + "\n"
                                    + "4.Return to Main Menu" + "\n" + "PLEASE CHOOSE A NUMBER FROM 1 TO 5: ");

                            int trainerChoice = 0;
                            do {
                                try {
                                    trainerChoice = sc.nextInt();
                                    if ((trainerChoice < 1) || (trainerChoice > 5)) {
                                        System.err.print("You must enter a number from 1 to 5!");

                                    } else {

                                        switch (trainerChoice) {
                                            case 1:
                                                TrainerDao.insertTrainer();
                                                headmasterMenu();
                                                break;
                                            case 2:
                                                TrainerDao.updateTrainer();
                                                headmasterMenu();
                                                break;
                                            case 3:
                                                TrainerDao.deleteTrainer();
                                                headmasterMenu();
                                                break;
                                            case 4:
                                                TrainerDao.getAllTrainers();
                                                headmasterMenu();
                                            case 5:
                                                headmasterMenu();
                                                break;
                                        }
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("You may only enter integers . Try again.");
                                    sc.next();

                                }
                            } while (sc.hasNext());

                        case 4:
                            System.out.println("ASSIGNMENT MENU:" + "\n" + "1.Create Assignment" + "\n" + "2.Update Assignment" + "\n" + "3.Delete Assignment" + "\n" + "4.See ALL assignments" + "\n"
                                    + "5.Return to Main Menu" + "\n" + "PLEASE CHOOSE A NUMBER FROM 1 TO 5: ");

                            int assignmentChoice = 0;
                            do {
                                try {
                                    assignmentChoice = sc.nextInt();
                                    if ((assignmentChoice < 1) || (assignmentChoice > 5)) {
                                        System.err.print("You must enter a number from 1 to 5!");

                                    } else {

                                        switch (assignmentChoice) {
                                            case 1:
                                                AssignmentDao.insertAssignment();
                                                headmasterMenu();
                                                break;
                                            case 2:
                                                AssignmentDao.upadateAssignment();
                                                headmasterMenu();
                                                break;
                                            case 3:
                                                AssignmentDao.deleteAssignment();
                                                headmasterMenu();
                                                break;
                                            case 4:
                                                AssignmentDao.getAssignments();
                                                headmasterMenu();
                                            case 5:
                                                headmasterMenu();
                                                break;
                                        }
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("You may only enter integers . Try again.");
                                    sc.next();

                                }

                            } while (sc.hasNext());

                        case 5:
                            System.out.println("STUDENTS PER COURSE MENU:" + "\n" + "1.See students per course" + "\n" + "2.Assign a student to a course" + "\n" + "3.Delete a student from a course" + "\n"
                                    + "4.Return to Main Menu" + "\n" + "PLEASE CHOOSE A NUMBER FROM 1 TO 4: ");

                            int studentPerCourseChoice = 0;
                            do {
                                try {
                                    studentPerCourseChoice = sc.nextInt();
                                    if ((studentPerCourseChoice < 1) || (studentPerCourseChoice > 4)) {
                                        System.err.print("You must enter a number from 1 to 4!");

                                    } else {

                                        switch (studentPerCourseChoice) {
                                            case 1:
                                                StudentPerCourseDao.getAllStudentsPerCourse();
                                                headmasterMenu();
                                                break;
                                            case 2:
                                                StudentPerCourseDao.createStudentPerCourse();
                                                headmasterMenu();
                                                break;
                                            case 3:
                                                StudentPerCourseDao.deleteStudentPerCourse();
                                                headmasterMenu();
                                                break;
                                            case 4:
                                                StudentPerCourseDao.deleteStudentPerCourse();
                                                headmasterMenu();
                                                break;
                                        }
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("You may only enter integers . Try again.");
                                    sc.next();

                                }

                            } while (sc.hasNext());

                        case 6:
                            System.out.println("TRAINERS PER COURSE MENU:" + "\n" + "1.See trainers and their courses" + "\n" + "2.Assign a trainer to a course" + "\n" + "3.Delete a trainer from a course" + "\n"
                                    + "4.Return to Main Menu" + "\n" + "PLEASE CHOOSE A NUMBER FROM 1 TO 4: ");

                            int trainerPerCourseChoice = 0;
                            do {
                                try {
                                    trainerPerCourseChoice = sc.nextInt();
                                    if ((trainerPerCourseChoice < 1) || (trainerPerCourseChoice > 4)) {
                                        System.err.print("You must enter a number from 1 to 4!");

                                    } else {
                                        switch (trainerPerCourseChoice) {
                                            case 1:
                                                TrainerDao.getAllTrainers();
                                                headmasterMenu();
                                                break;
                                            case 2:
                                                TrainerPerCourseDao.createTrainerPerCourse();
                                                headmasterMenu();
                                                break;
                                            case 3:
                                                TrainerPerCourseDao.deleteTrainerPerCourse();
                                                headmasterMenu();
                                                break;
                                            case 4:
                                                headmasterMenu();
                                                break;
                                        }
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("You may only enter integers . Try again.");
                                    sc.next();

                                }

                            } while (sc.hasNext());
                        case 7:
                            System.out.println("ASSIGNMENTS PER COURSE MENU:" + "\n" + "1.See assignment per course" + "\n" + "2.See assignment per student(per course)" + "\n" + "3.Assign an assignment to a course" + "\n"
                                    + "4.Assign or update an assignment to student(per course)" + "\n"
                                    + "5.Delete assignment per course" + "\n" + "6.Delete assignment per student(per course)" + "\n" + "7.Exit" + "\n"
                                    + "PLEASE CHOOSE A NUMBER FROM 1 TO 7: ");

                            int assignmentPerCourseChoice = 0;

                            do {
                                try {
                                    assignmentPerCourseChoice = sc.nextInt();
                                    if ((assignmentPerCourseChoice < 1) || (assignmentPerCourseChoice > 7)) {
                                        System.err.print("You must enter a number from 1 to 7!");

                                    } else {

                                        switch (assignmentPerCourseChoice) {
                                            case 1:
                                                AssignmentsPerCourseDao.getAssignmentsPerCourse();
                                                headmasterMenu();
                                                break;
                                            case 2:
                                                AssignmentPerStudentDao.showAssignmentPerStudentPerCourse();
                                                headmasterMenu();
                                                break;
                                            case 3:
                                                AssignmentsPerCourseDao.assignAssignmentToCourse();
                                                headmasterMenu();
                                                break;
                                            case 4:
                                                AssignmentPerStudentDao.assignAssignmentPerStudentPerCourse();
                                                headmasterMenu();
                                                break;
                                            case 5:
                                                System.err.println("CHOOSE COURSE AND SEE THE ASSIGNMENTS PER COURSE");
                                                AssignmentsPerCourseDao.getAssignmentsPerCourse();
                                                int course_id = CourseDao.course_id;
                                                String title3 = AssignmentDao.title;
                                                System.err.println("CHOOSE ASSIGNMENT TO DELETE:");
                                                AssignmentDao.checkIfAssignmentExists();
                                                int assignment_id = AssignmentDao.fetchAssignmentId(title3);
                                                AssignmentsPerCourseDao.deleteAssignmentPerCourse(course_id, assignment_id);
                                                headmasterMenu();
                                                break;
                                            case 6:
                                                HeadmasterDao.deleteAssPerStPerCour();
                                                headmasterMenu();
                                                break;
                                            case 7:
                                                headmasterMenu();
                                                break;

                                        }
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("You may only enter integers . Try again.");
                                    sc.next();

                                }

                            } while (sc.hasNext());
                        case 8:
                            System.out.println("SCHEDULE MENU:" + "\n" + "1.See schedule per Course" + "\n" + "2.Create Shedule per course" + "\n" + "3.Update Schedule per course"
                                    + "\n" + "4.Delete Schedule per course" + "\n" + "5.Return to Main Menu" + "\n" + "PLEASE CHOOSE A NUMBER FROM 1 TO 5: ");

                            int schedulePerCourseChoice = 0;
                            do {
                                try {
                                    schedulePerCourseChoice = sc.nextInt();
                                    if ((schedulePerCourseChoice < 1) || (schedulePerCourseChoice > 5)) {
                                        System.err.print("You must enter a number from 1 to 5!");

                                    } else {
                                        switch (schedulePerCourseChoice) {
                                            case 1:
                                                ScheduleDao.getSchedule();
                                                headmasterMenu();
                                                break;
                                            case 2:
                                                ScheduleDao.createScheduleTable();
                                                headmasterMenu();
                                                break;
                                            case 3:
                                                ScheduleDao.updateSchedule();
                                                headmasterMenu();
                                                break;
                                            case 4:
                                                ScheduleDao.deleteSchedule();
                                                headmasterMenu();
                                                break;
                                            case 5:
                                                headmasterMenu();
                                                break;
                                        }
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("You may only enter integers . Try again.");
                                    sc.next();

                                }

                            } while (sc.hasNext());
                        case 9:
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

    public static void adminMenu() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("MAIN MENU:" + "\n" + "1.See all users" + "\n" + "2.Exit" + "\n");
        int choiceMenu = 0;
        do {
            try {
                choiceMenu = sc.nextInt();
                if ((choiceMenu < 1) || (choiceMenu > 2)) {
                    System.err.print("You must enter a number from 1 to 2");

                } else {
                    switch (choiceMenu) {
                        case 1:
                            UserDao.getAllUsers();
                            adminMenu();
                            break;
                        case 2:
                            Home.userLogin();
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
