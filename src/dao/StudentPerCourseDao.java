/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.ScheduleDao.title;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import menus.Home;
import model.Schedule;
import model.Student;
import model.StudentPerCourse;
import school.Utils;

import utils.DBUtils;

/**
 *
 * @author liana
 */
public class StudentPerCourseDao {

    public static int enroll_id;
    static int secondCourse;

    public static ArrayList<Student> getAllStudentsPerCourse() throws ParseException {

        CourseDao.getAllCourses();
        System.out.println("For which course you want to see a list of students?");
        CourseDao.checkIfCourseExists();
        String title = CourseDao.title;
        CourseDao.fetchCourseId(title);
        ArrayList<Student> list = new ArrayList<>();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT distinct student.student_id,student.first_name,student.last_name from (course,student) "
                + "inner join student_per_course on student_per_course.student_id=student.student_id\n"
                + "where student_per_course.course_id=" + CourseDao.course_id
                + " or student_per_course.secondcourse_id=" + CourseDao.course_id;
        System.err.println("LIST OF STUDENTS FOR THIS COURSE:");
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
        if (list.isEmpty()) {
            System.out.println("THERE ARE NO STUDENTS IN THIS COURSE YET!" + "\n");
            try {
                Home.headmasterMenu();
            } catch (ParseException ex) {
                Logger.getLogger(StudentPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;

    }

    public static void enrollToCourse(int student_id) throws ParseException {
        Scanner sc = new Scanner(System.in);
        enroll_id = getEnrollId(StudentDao.student_id);

        if (enroll_id != 0) {
            getStudentPerCourse(enroll_id);
        } else {

            System.out.println("\n");
            CourseDao.getAllCourses();
            System.out.println("Please type the title of the course you want to enroll: ");
            CourseDao.checkIfCourseExists();
            String title = CourseDao.title;
            CourseDao.fetchCourseId(title);
            System.out.println("Are  you sure you want to enroll to course " + title + "?" + "\n");
            System.out.println("Please answer with yes or no" + "\n");
            String answer = sc.nextLine().toLowerCase();
            while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
                System.out.println("Please answer with yes or no");
                answer = sc.nextLine().toLowerCase();
            }
            switch (answer) {
                case "yes":

                    Connection con = DBUtils.getConnection();
                    PreparedStatement pst = null;
                    String sql = "INSERT into student_per_course (course_id,student_id) VALUES (?,?)";
                    StudentPerCourse stp = new StudentPerCourse();

                    try {
                        pst = con.prepareStatement(sql);
                        int course_id = CourseDao.course_id;
                        stp.setCourse_id(course_id);
                        student_id = StudentDao.student_id;
                        stp.setStudent_id(student_id);

                        pst.setInt(1, stp.getCourse_id());
                        pst.setInt(2, stp.getStudent_id());
                        pst.executeUpdate();
                        System.err.println("YOU HAVE BEEN ENROLLED TO  " + title + "!" + "\n");
                        enroll_id = getEnrollId(student_id);
                        StudentDao.insertEnrollId(enroll_id);
                        Home.subMenu();

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

                    break;
                case "no":
                    Home.subMenu();
                    break;

            }
        }
    }

    public static void createStudentPerCourse() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("1.Students not enrolled to a course" + "\n" + "2.Students already enrolled to one course" + "\n"
                + "3.Exit" + "\n");
        int answer = 0;

        do {
            try {
                answer = sc.nextInt();
                if ((answer < 1) || (answer > 3)) {
                    System.err.print("You must enter a number from 1 to 3!");

                } else {

                    switch (answer) {
                        case 1:
                            System.err.println("LIST OF STUDENTS NOT ENROLLED IN A COURSE:" + "\n");
                            StudentDao.studentsNotEnrolled();
                            System.out.println("Type the name of the student you want to enroll");
                            StudentDao.checkIfStudentExists();
                            String first_name = StudentDao.first_name;
                            String last_name = StudentDao.last_name;
                            StudentDao.fetchStudentId(first_name, last_name);
                            int student_id = StudentDao.student_id;
                            System.out.println("\n");
                            System.err.println("LIST OF COURSES: ");
                            CourseDao.getAllCourses();
                            CourseDao.checkIfCourseExists();
                            String title = CourseDao.title;
                            CourseDao.fetchCourseId(title);
                            int course_id = CourseDao.course_id;

                            System.out.println("Are  you sure you want to enroll "
                                    + first_name + " " + last_name + " in " + title + "?" + "\n");
                            String answer1 = sc.nextLine().toLowerCase();
                            while (!answer1.equalsIgnoreCase("yes") && !answer1.equalsIgnoreCase("no")) {
                                System.out.println("Please answer with yes or no");
                                answer1 = sc.nextLine().toLowerCase();
                            }
                            PreparedStatement pst = null;
                            Connection con = DBUtils.getConnection();
                            switch (answer1) {

                                case "yes":
                                    String sql = "INSERT INTO student_per_course (course_id,student_id) VALUES (?,?)";
                                    try {
                                        StudentPerCourse stp = new StudentPerCourse();
                                        pst = con.prepareStatement(sql);
                                        stp.setCourse_id(course_id);
                                        stp.setStudent_id(student_id);

                                        pst.setInt(1, stp.getCourse_id());
                                        pst.setInt(2, stp.getStudent_id());
                                        pst.executeUpdate();
                                        System.err.println("You have enrolled " + first_name + " " + last_name + " in " + "\n" + title);
                                        enroll_id = getEnrollId(student_id);
                                        StudentDao.insertEnrollId(enroll_id);

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
                                    Home.headmasterMenu();
                                    break;
                                case "no": {
                                    try {
                                        Home.headmasterMenu();
                                    } catch (ParseException ex) {
                                        Logger.getLogger(StudentPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                break;

                            }
                            break;
                        case 2:
                            StudentDao.studentsEnrolled();
                            System.out.println("PLEASE TYPE THE NAME OF THE STUDENT");
                            StudentDao.checkIfStudentExists();
                            HeadmasterDao.setSecondcourse(enroll_id);
                            Home.headmasterMenu();
                            break;
                        case 3:
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

    public static int getEnrollId(int student_id) {

        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT enroll_id from student_per_course where student_id='" + student_id + "'";

        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                enroll_id = rs.getInt("enroll_id");
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
        return enroll_id;
    }

    public static StudentPerCourse getStudentPerCourse(int enroll_id) throws ParseException {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT * from student_per_course where enroll_id='" + enroll_id + "'";
        StudentPerCourse spcr = new StudentPerCourse();
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                spcr.setStudent_id(rs.getInt(3));
                int student_id = rs.getInt(3);
                spcr.setCourse_id(rs.getInt(2));
                int course_id = rs.getInt(2);
                spcr.setSecondCourse_id(rs.getInt(4));
                secondCourse = rs.getInt(4);

                if (rs.wasNull()) {
                    secondCourse = 0;
                    System.out.println("\n");
                    System.out.println("YOU HAVE BEEN ENROLLED TO:");
                    showCourseEnrolled(student_id);
                    System.out.println("\n");
                    System.err.println("You can enroll to one more course");
                    System.out.println("\n");
                    setSecondcourse(enroll_id);
                    Home.subMenu();

                } else {
                    System.err.println("You already have been enrolled to two courses! For more come next year!");
                    System.err.println("Goodbye");
                    showCourseEnrolled(student_id);
                    System.out.println("\n");
                    Home.subMenu();
                    break;
                }

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

        return spcr;

    }

    public static String showCourseEnrolled(int student_id) {

        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT distinct title from course inner join student_per_course on course.course_id=student_per_course.course_id\n"
                + "or course.course_id=student_per_course.secondcourse_id\n"
                + "where student_per_course.student_id=" + student_id;
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {
                String title = rs.getString(1);
                System.err.println("COURSE: " + title);
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
        return "";
    }

    public static void setSecondcourse(int enroll_id) throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("LIST OF COURSES:" + "\n");
        CourseDao.getAllCourses();
        System.out.println("Please type the title of the course you want to enroll: ");
        CourseDao.checkIfCourseExists();
        String title = CourseDao.title;
        CourseDao.fetchCourseId(title);
        System.out.println("Are  you sure you want to enroll to course " + title + "?" + "\n");
        System.out.println("Please answer with yes or no" + "\n");
        String answer = Utils.answerYesOrNo(sc.nextLine());
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
            System.err.println("SUCCESS! YOU HAVE BEEN ENROLLED TO " + title);
            System.out.println("\n");
        }
        Home.subMenu();
    }

    public static void studentSchedule() throws ParseException {
        System.out.println("For which of your courses do you want to see the schedule?");
        CourseDao.checkIfCourseExists();
        title = CourseDao.title.replaceAll("\\s", "") + "schedule";
        ArrayList<Schedule> list = new ArrayList<>();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        boolean bool = true;
        String sql = "select * from " + title;
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {

                int id = rs.getInt("id");
                String date = rs.getString("date");
                String schedule = rs.getString("schedule");
                String time = rs.getString("time");

                Schedule sch = new Schedule(id, date, schedule, time);
                list.add(sch);
                System.out.println("DATE: " + date + "\n" + "SCHEDULE: " + schedule + "\n" + "TIME: " + time + "\n");
                bool = rs.wasNull();

            }

        } catch (SQLException ex) {
//            Logger.getLogger(StudentPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("THERE IS NO SCHEDULE YET!");
            Home.subMenu();

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

        if (bool) {
            Home.subMenu();
        }
    }

    public static void showStudentsByCourseId(int course_id) throws ParseException {
        boolean bool = true;
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select student.student_id, first_name,last_name from student inner join student_per_course on "
                + "student_per_course.student_id=student.student_id\n"
                + "where student_per_course.course_id=" + course_id;
        System.err.println("STUDENTS WHO ARE ENROLLED IN THIS COURSE:" + "\n");
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {
                int student_id = rs.getInt("student_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                System.out.println("FIRST NAME: " + first_name + "\n"
                        + "LAST NAME: " + last_name + "\n");

                bool = rs.wasNull();
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
        if (bool) {
            System.err.println("THERE ARE NO STUDENTS YET!");
            Home.headmasterMenu();
        }

    }

    public static void checkStudentPerCourseExists(int course_id, int student_id) throws ParseException {
        boolean bool = true;
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT enroll_id from student_per_course where course_id=" + course_id + " and student_id=" + student_id
                + " or secondcourse_id=" + course_id + " and student_id=" + student_id;
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {
                int enroll_id = rs.getInt("enroll_id");
                bool = rs.wasNull();
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
        if (bool) {
            System.err.println("THIS STUDENT IS NOT ENROLLED IN THIS COURSE!");
            Home.headmasterMenu();
        }

    }

    public static void deleteStudentPerCourse() throws ParseException {
        Scanner sc = new Scanner(System.in);
        StudentDao.getAllStudents();
        System.out.println("Select a student to delete." + "\n" + "Please type first name and last name");
        StudentDao.checkIfStudentExists();
        String first_name = StudentDao.first_name;
        String last_name = StudentDao.last_name;
        StudentDao.fetchStudentId(first_name, last_name);
        int student_id = StudentDao.student_id;
        CourseDao.checkIfCourseExists();
        int course_id = CourseDao.course_id;
        checkStudentPerCourseExists(course_id, student_id);

        System.out.println("Are  you sure you want to delete " + first_name + " " + last_name + " from this course?");
        String answer = Utils.answerYesOrNo(sc.nextLine());
        switch (answer) {
            case "yes":

                Connection con = DBUtils.getConnection();
                PreparedStatement pst = null;
                PreparedStatement pst2 = null;
                String sql = "update student_per_course set course_id=null where course_id=" + course_id
                        + " and student_id=" + student_id;
                String sql1="update student_per_course set secondcourse_id=null where secondcourse_id=" + course_id
                        + " and student_id=" + student_id;
                boolean result = false;
                try {
                    con.setAutoCommit(false);
                    pst = con.prepareStatement(sql);
                    pst2=con.prepareStatement(sql1);
                    pst.execute();
                    pst2.execute();
                    con.commit();
                    result = true;
                } catch (SQLException ex) {
                    Logger.getLogger(StudentPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
                    result = false;
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
                if (result) {
                    System.err.println("STUDENT " + StudentDao.first_name + " " + StudentDao.last_name + " HAS BEEN DELETED FROM " + CourseDao.title);
                }
                Home.headmasterMenu();
                break;
            case "no": {
                try {
                    Home.headmasterMenu();
                } catch (ParseException ex) {
                    Logger.getLogger(StudentPerCourseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            break;
        }
    }

}


