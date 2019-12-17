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
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import menus.Home;
import model.Course;
import model.Schedule;
import utils.DBUtils;

/**
 *
 * @author liana
 */
public class ScheduleDao {

    static String title;

    public static ArrayList<Schedule> getSchedule() {
        System.err.println("For which course do you want to see the schedule?" + "\n");
        CourseDao.getCoursesWithSchedule();
        CourseDao.checkIfCourseExists();
        title = CourseDao.title.replaceAll("\\s", "") + "schedule";
        ArrayList<Schedule> list = new ArrayList<>();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select * from " + title;
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {

                int id = rs.getInt("id");
                String date = rs.getString("date");
                String schedule = rs.getString("schedule");
                String time = rs.getString("time");
                if (rs.wasNull()) {
                    schedule = null;
                    Schedule sch1 = new Schedule(id, date, time);
                    list.add(sch1);
                    System.out.println(sch1.toString());
                } else {

                    Schedule sch = new Schedule(id, date, schedule, time);
                    list.add(sch);
                    System.out.println(sch.toString());
                }

            }
        } catch (SQLException ex) {
            System.err.println("THERE IS NO SUCH SCHEDULE! TRY AGAIN");
            getSchedule();
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(ScheduleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ScheduleDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return list;

    }

    public static void createScheduleTable() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.err.println("For which course do you want to create a schedule?" + "\n");
        CourseDao.getCoursesWithNoSchedule();
        CourseDao.checkIfCourseExists();
        CourseDao.updateCourseBooleanTrue(CourseDao.fetchCourseId(CourseDao.title));
        title = CourseDao.title.replaceAll("\\s", "") + "schedule";

        Connection con = DBUtils.getConnection();
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            String sql = "create table " + title
                    + " (id int auto_increment,date varchar (45),"
                    + "schedule varchar (255),time varchar(45),primary key(id));";
            stmt.executeUpdate(sql);
            System.err.println("Schedule table has been created!");
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
            }
            setDateColumn(title);
            System.err.println("PLEASE WAIT....");
            scheduleId(title);
            System.out.println("Type the id of the date for which you want to set a schedule: ");
            int id = UtilsDao.onlyInteger();
            setSchedule(id);

        }
    }

    public static void deleteSchedule() {
        Scanner sc = new Scanner(System.in);
        System.err.println("For which course do you want to delete the schedule?" + "\n");
        CourseDao.getCoursesWithSchedule();
        CourseDao.checkIfCourseExists();
        title = CourseDao.title.replaceAll("\\s", "") + "schedule";

        Connection con = DBUtils.getConnection();
        Statement stmt = null;

        System.out.println("Are you sure? (Please answer with yes or no)");
        String answer = sc.nextLine().toLowerCase();
        while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
            System.out.println("Please answer with yes or no");
            answer = sc.nextLine().toLowerCase();
        }
        switch (answer) {
            case "yes":
                try {
                    stmt = con.createStatement();
                    String sql = "DROP table " + title;
                    stmt.executeUpdate(sql);
                    System.err.println("Schedule table has been deleted!");
                    CourseDao.updateCourseBooleanFalse(CourseDao.fetchCourseId(CourseDao.title));
                } catch (SQLException ex) {
                    Logger.getLogger(ScheduleDao.class.getName()).log(Level.SEVERE, null, ex);
                } finally {

                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
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

    public static void setDateColumn(String title) {
        boolean input;

        try {
            Scanner sc = new Scanner(System.in);
            Connection con = DBUtils.getConnection();
            PreparedStatement pst = null;

            System.out.println("Please type the start date in form: dd/MM/yyyy ");
            String start = sc.nextLine();
            Date start1 = new SimpleDateFormat("dd/MM/yyyy").parse(start);
            System.out.println("Please type the end date in form: dd/MM/yyyy");
            String end = sc.nextLine();
            Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(end);
            List<String> list = UtilsDao.getDates(start1, endDate);

            String sql = "INSERT INTO " + title + " (date) VALUES (?)";
            try {
                pst = con.prepareStatement(sql);
                for (String item : list) {
                    for (int i = 0; i < 1; ++i) {
                        pst.setString(i + 1, item);

                        pst.addBatch();
                    }
                }

                int[] result = pst.executeBatch();
                input = true;

            } catch (SQLException ex) {
                Logger.getLogger(ScheduleDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

                try {
                    pst.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (ParseException e) {
            input = false;
            System.err.println("WRONG INPUT, PLEASE TRY AGAIN:");
            setDateColumn(title);
        }
    }

    public static void setSchedule(int id) throws ParseException {
        Scanner sc = new Scanner(System.in);
        title = CourseDao.title.replaceAll("\\s", "") + "schedule";
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        System.out.println("Write the schedule: ");
        String schedule = UtilsDao.notNull(sc.nextLine());
        String sql = "update " + title + " set schedule='" + schedule + "' where id=" + id;
        boolean change = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;
        } catch (SQLException ex) {
            change = false;
            Logger.getLogger(ScheduleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (change) {
            System.err.println("SUCCESS!");
            System.out.println("CONTINUE? (please answer with yes or no)");
            String answer = sc.nextLine().toLowerCase();
            while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
                System.out.println("Please answer with yes or no");
                answer = sc.nextLine().toLowerCase();
            }
            switch (answer) {
                case "yes":
                    System.out.println("Please type the id:");
                    id = UtilsDao.onlyInteger();
                    setSchedule(id);
                    break;
                case "no":
                    System.out.println("Is the course of type full or part?");
                    String type = Course.insertType();
                    if (type.equalsIgnoreCase("full")) {
                        SetTimeScheduleFull(type);
                    } else {
                        SetTimeSchedulePart(type);
                    }
                    break;

            }
            System.err.println("SCHEDULE HAS BEEN CREATED!");
            System.out.println("\n");
            Home.headmasterMenu();
        }

    }

    public static int scheduleId(String title) {

        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT id,date from " + title;
        int id = 0;
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {

                id = rs.getInt("id");
                String date = rs.getString("date");

                System.out.println("ID: " + id + "\n" + "DATE: " + date + "\n");
            }
        } catch (SQLException ex) {

            Logger.getLogger(ScheduleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return id;
    }

    public static void SetTimeScheduleFull(String type) {
        Scanner sc = new Scanner(System.in);
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;

        String sql = "UPDATE " + title + " SET time='09:00-16.00' WHERE ID is not null";
        boolean change = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;
        } catch (SQLException ex) {
            change = false;
            Logger.getLogger(ScheduleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (change) {
            System.err.println("TIME HAS BEEN SET");
        }
    }

    public static void SetTimeSchedulePart(String type) {
        Scanner sc = new Scanner(System.in);
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE " + title + " SET time='17:00-21.00' WHERE ID is not null";
        boolean change = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;
        } catch (SQLException ex) {
            change = false;
            Logger
                    .getLogger(ScheduleDao.class
                            .getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();

            } catch (SQLException ex) {
                Logger.getLogger(Schedule.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(Schedule.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (change) {
            System.err.println("TIME HAS BEEN SET");
        }

    }

    public static void updateSchedule() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.println("BERORE UPDATING SEE THE EXISTING SCHEDULE ");
        getSchedule();
        System.out.println("Please choose the fields you want to update " + "\n"
                + "1. Date range "+"\n"+"2.Insert new date with schedule" + "\n" + "3.Schedule per date" + "\n" 
                + "4. Time" + "\n" + "5. Exit" + "\n");
        int fieldUpdate = 0;
        do {
            try {
                fieldUpdate = sc.nextInt();
                if ((fieldUpdate < 1) || (fieldUpdate > 5)) {
                    System.out.print("You must enter a number from 1 to 5!");
                } else {
                    switch (fieldUpdate) {
                        case 1:
                            System.out.println("IF YOU UPDATE DATE RANGE YOU WILL LOSE ALL TABLE'S DATA");
                            System.out.println("CONTINUE?");
                            String answer1=UtilsDao.answerYesOrNo(sc.nextLine());
                           switch(answer1){
                               case "yes":
                               truncateSchedule();
                               setDateColumn(title);
                               updateSchedule();
                               break;
                               case "no":
                                   updateSchedule();
                                   break;
                           }
                           break;
                           
                        case 2:
                            insertScheduleRow();
                            break;
                        case 3:
                            System.out.println("Please type the ID of the date to change the schedule: ");
                            int id = UtilsDao.onlyInteger();
                            setSchedule(id);
                            System.err.println("Exit? (yes or no)");
                            String answer = UtilsDao.answerYesOrNo(sc.nextLine());
                            switch (answer) {
                                case "yes":
                                    Home.headmasterMenu();
                                    break;
                                case "no":
                                    updateSchedule();
                                    break;
                            }

                            break;
                        case 4:
                            System.out.println("Is the course of type full or part?");
                            String type = Course.insertType();
                            if (type.equalsIgnoreCase("full")) {
                                SetTimeScheduleFull(type);
                            } else {
                                SetTimeSchedulePart(type);
                            }
                            System.err.println("Exit? (yes or no)");
                           String answer2 = UtilsDao.answerYesOrNo(sc.nextLine());
                            switch (answer2) {
                                case "yes":
                                    Home.headmasterMenu();
                                    break;
                                case "no":
                                    updateSchedule();
                                    break;
                            }

                            break;

                        case 5:
                            Home.headmasterMenu();
                            break;

                    }
                }

            } catch (InputMismatchException e) {
                System.out.println("You may only enter integers . Try again.");
                sc.next();

            }
        } while (sc.hasNext());
    }

    public static void truncateSchedule() {
        title = CourseDao.title.replaceAll("\\s", "") + "schedule";
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "TRUNCATE " + title;
        boolean change = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;
        } catch (SQLException ex) {
            change = false;
            Logger.getLogger(ScheduleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (change) {
            System.err.println("DATE TABLE HAS BEEN RESET!");
        }

    }

    public static void insertScheduleRow() throws ParseException {
        Scanner sc = new Scanner(System.in);
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "insert into " + CourseDao.title.replaceAll("\\s", "") + "schedule"
                + "(date,schedule) values(?,?)";
        Schedule schedule = new Schedule();
  
        try {
            pst = con.prepareStatement(sql);
            System.out.println("DATE: ");
            System.out.println("Please use dd-MM-yyyy form");
            String date = UtilsDao.changeFormatString(sc.nextLine());
            schedule.setDate(date);
            
            System.out.println("SCHEDULE:");
            String scheduleText = UtilsDao.notNull(sc.nextLine());
            schedule.setSchedule(scheduleText);
            
          

            pst.setString(1,schedule.getDate());
            pst.setString(2,schedule.getSchedule());
            pst.executeUpdate();
            
            System.out.println("YOU HAVE INSERTED A NEW ROW!");
            System.out.println("CONTINUE?");
            String answer = UtilsDao.answerYesOrNo(sc.nextLine());
            switch(answer){
                case "yes":
                    insertScheduleRow();
                    break;
                case "no":
                    Home.headmasterMenu();
                    break;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}

