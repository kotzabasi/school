/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import menus.Home;
import model.Course;

import utils.DBUtils;

/**
 *
 * @author liana
 */
public class CourseDao {

    public static int course_id;
    public static String title;

    public static ArrayList<Course> getAllCourses() {
        ArrayList<Course> list = new ArrayList<Course>();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select * from course";
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {
                Course s = new Course();
                int course_id = rs.getInt(1);
                String title = rs.getString(2);
                LocalDate start_date = rs.getDate(3).toLocalDate();
                LocalDate end_date = rs.getDate(4).toLocalDate();
                String stream = rs.getString(5);
                String type = rs.getString(6);
                boolean schedule = rs.getBoolean(7);
                Course course = new Course(course_id, title, start_date, end_date, stream, type, schedule);
                list.add(course);
                System.out.println(course.toString());

            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return list;

    }

    public static ArrayList<Course> getCoursesWithNoSchedule() {
        ArrayList<Course> list = new ArrayList<Course>();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select*from course where schedule is null or schedule=0";
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            System.err.println("THESE ARE THE COURSES WITH NO SCHEDULE: ");
            while (rs.next()) {
                Course s = new Course();
                int course_id = rs.getInt(1);
                String title = rs.getString(2);
                LocalDate start_date = rs.getDate(3).toLocalDate();
                LocalDate end_date = rs.getDate(4).toLocalDate();
                String stream = rs.getString(5);
                String type = rs.getString(6);
                boolean schedule = rs.getBoolean(7);
                Course course = new Course(course_id, title, start_date, end_date, stream, type, schedule);
                list.add(course);
                System.out.println(course.toString());

            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return list;

    }

    public static ArrayList<Course> getCoursesWithSchedule() {
        ArrayList<Course> list = new ArrayList<Course>();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select*from course where schedule =1";
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            System.err.println("THESE ARE THE COURSES WITH SCHEDULE: ");
            while (rs.next()) {
                Course s = new Course();
                int course_id = rs.getInt(1);
                String title = rs.getString(2);
                LocalDate start_date = rs.getDate(3).toLocalDate();
                LocalDate end_date = rs.getDate(4).toLocalDate();
                String stream = rs.getString(5);
                String type = rs.getString(6);
                boolean schedule = rs.getBoolean(7);
                Course course = new Course(course_id, title, start_date, end_date, stream, type, schedule);
                list.add(course);
                System.out.println(course.toString());

            }
        } catch (SQLException ex) {
            Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Course.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return list;

    }

    public static void checkIfCourseExists() {
        Connection con = DBUtils.getConnection();
        Scanner sc = new Scanner(System.in);
        PreparedStatement pst = null;
        try {
            System.out.println("Please type the course title: ");
            title = UtilsDao.notNull(sc.nextLine());
            String sql = "select course_id,title from course where title ='" + title + "'";
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            if (rs.next()) {
                course_id = rs.getInt("course_id");
                System.err.println("Course ID: " + course_id + "\n" + "Title: " + title);
            } else {
                System.err.println("This name does not exist. please try again: ");
                checkIfCourseExists();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void showByCourseId(int course_id) {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT*FROM course WHERE course_id='" + course_id + "'";
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {

                course_id = rs.getInt("course_id");
                title = rs.getString(2);
                LocalDate start_date = rs.getDate(3).toLocalDate();
                LocalDate end_date = rs.getDate(4).toLocalDate();
                String stream = rs.getString(5);
                String type = rs.getString(6);
                boolean schedule = rs.getBoolean(7);
                Course course = new Course(course_id, title, start_date, end_date, stream, type, schedule);
                System.out.println(course.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static int fetchCourseId(String title) {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT course_id FROM course WHERE title='" + title + "'";

        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {

                course_id = rs.getInt("course_id");

            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return course_id;
    }

    public static void insertCourse() {
        Scanner sc = new Scanner(System.in);
        System.out.println("You have to insert title,start date, end date, stream and type");
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "insert into course (title,start_date,end_date,stream,type) values(?,?,?,?,?)";
        Course course = new Course();

        try {
            pst = con.prepareStatement(sql);
            System.out.println("Title:");
            title = UtilsDao.notNull(sc.nextLine());
            course.setTitle(title);

            boolean startdateinput = false;
            while (!startdateinput) {

                try {
                    System.out.println("Start Date(yyyy-MM-dd):");
                    String startDate = sc.nextLine();

                    LocalDate start_date = LocalDate.parse(startDate);
                    LocalDate localS = start_date.plusDays(1);

                    course.setStart_date(localS);
                    startdateinput = true;
                } catch (DateTimeParseException e) {
                    startdateinput = false;
                    System.err.println("Input Error: ");

                }
            }
            boolean enddate = false;
            while (!enddate) {

                try {

                    System.out.println("End Date:(yyyy-MM-dd)");
                    String endDate = sc.nextLine();

                    LocalDate end_date = LocalDate.parse(endDate);
                    LocalDate localE = end_date.plusDays(1);

                    course.setEnd_date(localE);
                    enddate = true;

                } catch (DateTimeParseException e) {
                    enddate = false;
                    System.err.println("Input Error: ");

                }
            }

            String stream = Course.insertStream();
            course.setStream(stream);
            String type = Course.insertType();
            course.setType(type);

            pst.setString(1, course.getTitle());
            pst.setObject(2, course.getStart_date());
            pst.setObject(3, course.getEnd_date());
            pst.setString(4, course.getStream());
            pst.setString(5, course.getType());

            pst.executeUpdate();
            System.out.println("YOU HAVE INSERTED THE COURSE " + "\n" + title);

        } catch (SQLException ex) {
            Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void deleteCourse() {
        getAllCourses();
        System.out.println("You have to type the title of the course you want to delete ");
        checkIfCourseExists();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "DELETE FROM course WHERE course_id=?";
        boolean result = false;
        try {
            pst = con.prepareStatement(sql);
            Scanner sc = new Scanner(System.in);
            System.out.println("type the course_id if  you want to delete this course");
            course_id = UtilsDao.onlyInteger();
            checkByTitleAndId();
            pst.setInt(1, course_id);
            pst.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            result = false;

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (result) {
            System.err.println("COURSE HAS BEEN DELETED");
        }

    }

    public static void updateCourseTitle(int course_id) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please type the new title: ");
        title = UtilsDao.notNull(sc.nextLine());
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE course SET title='" + title + "'" + "WHERE course_id='" + course_id + "'";
        boolean change = false;
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
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (change) {
            System.err.println("\n" + "COURSE HAS BEEN UPDATED" + "\n");
            showByCourseId(course_id);

        }
    }

    public static void updateCourseType(int course_id) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please insert the new type");
        String type = Course.insertType();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE course SET type='" + type + "'" + "WHERE course_id='" + course_id + "'";
        boolean change = false;
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
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (change) {
            System.err.println("\n" + "COURSE HAS BEEN UPDATED" + "\n");
            showByCourseId(course_id);

        }

    }

    public static void updateCourseBooleanTrue(int course_id) {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE course SET schedule=1 where course_id=" + course_id;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void updateCourseBooleanFalse(int course_id) {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE course SET schedule=0 where course_id=" + course_id;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void updateCourseStream(int course_id) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Please type the new stream ");
        String stream = Course.insertStream();

        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE course SET stream='" + stream + "'" + "WHERE course_id='" + course_id + "'";
        boolean change = false;
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
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (change) {
            System.err.println("\n" + "COURSE HAS BEEN UPDATED" + "\n");
            showByCourseId(course_id);

        }

    }

    public static void updateCourseStartDate(int course_id) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Please type the new start date (yyyy-MM-DD) ");
            String startDate = sc.nextLine();
            LocalDate start_date = Date.valueOf(startDate).toLocalDate();

            Connection con = DBUtils.getConnection();
            PreparedStatement pst = null;
            String sql = "UPDATE course SET start_date='" + start_date + "'" + "WHERE course_id='" + course_id + "'";
            boolean change = false;
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
                    Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (change) {
                System.err.println("\n" + "COURSE HAS BEEN UPDATED" + "\n");
                showByCourseId(course_id);

            }

        } catch (IllegalArgumentException e) {
            System.out.println("YOU HAVE TO USE THE FORMAT (yyyy-MM-DD)");
            System.err.println("Try again");
            updateCourseStartDate(course_id);

        }
    }

    public static void updateCourseEndDate(int course_id) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please type the new end date (yyyy-MM-DD) ");
            String endDate = sc.nextLine();
            Date end_date = Date.valueOf(endDate);

            Connection con = DBUtils.getConnection();
            PreparedStatement pst = null;
            String sql = "UPDATE course SET end_date='" + end_date + "'" + "WHERE course_id='" + course_id + "'";
            boolean change = false;
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
                    Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (change) {
                System.err.println("\n" + "COURSE HAS BEEN UPDATED" + "\n");
                showByCourseId(course_id);

            }
        } catch (IllegalArgumentException e) {
            System.out.println("YOU HAVE TO USE THE FORMAT (yyyy-MM-DD)");
            System.err.println("Try again");
            updateCourseEndDate(course_id);

        }

    }

    public static void updateCourse() throws ParseException {
        getAllCourses();
        System.out.println("Please type the title of the course you want to update: ");
        checkIfCourseExists();
        Scanner sc = new Scanner(System.in);
        System.out.println("Please type the course_id of the course you want to update: ");
        course_id = UtilsDao.onlyInteger();
        Course course = new Course();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select * from course where course_id=?";

        checkByTitleAndId();

        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, course_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                course.setCourse_id(rs.getInt(1));
                course.setTitle(rs.getString(2));
                course.setStart_date(rs.getDate(3).toLocalDate());
                course.setEnd_date(rs.getDate(4).toLocalDate());
                course.setStream(rs.getString(5));
                course.setTitle(rs.getString(6));
                System.out.println(course.toString());

            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Which field do you want to update?" + "\n" + "1.Title" + "\n" + "2.Start Date" + "\n"
                + "3.End Date" + "\n" + "4.Stream" + "\n" + "5.Type" + "\n" + "6.Exit" + "\n");
        int fieldUpdate = 0;

        do {
            try {
                fieldUpdate = sc.nextInt();
                if ((fieldUpdate < 1) || (fieldUpdate > 6)) {
                    System.out.print("You must enter a number from 1 to 6!");

                } else {
                    switch (fieldUpdate) {
                        case 1:
                            updateCourseTitle(course_id);
                            Home.headmasterMenu();
                            break;
                        case 2:
                            updateCourseStartDate(course_id);
                            Home.headmasterMenu();
                            break;
                        case 3:
                            updateCourseEndDate(course_id);
                            Home.headmasterMenu();
                            break;
                        case 4:
                            updateCourseStream(course_id);
                            break;
                        case 5:
                            updateCourseType(course_id);
                            Home.headmasterMenu();
                            break;
                        case 6:
                            Home.headmasterMenu();

                    }

                }

            } catch (InputMismatchException e) {
                System.out.println("You may only enter integers . Try again.");
                sc.next();

            }
        } while (sc.hasNext());

    }

    public static void checkByTitleAndId() {
        Scanner sc = new Scanner(System.in);
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        try {

            String sql = "SELECT course_id,title from course where title='" + title + "'"
                    + "AND course_id='" + course_id + "'";

            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            if (rs.next()) {
                course_id = rs.getInt("course_id");
                title = rs.getString("title");

            } else {
                System.err.println("This user does not exist. please try again: ");
                checkByTitleAndId();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CourseDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
