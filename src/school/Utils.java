/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school;

import com.mysql.cj.util.StringUtils;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author liana
 */
public class Utils {

//    public static List<Date> getDatesBetweenUsingJava7(
//            Date startDate, Date endDate) {
//        List<Date> datesInRange = new ArrayList<>();
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(startDate);
//
//        Calendar endCalendar = new GregorianCalendar();
//        endCalendar.setTime(endDate);
//
//        while (calendar.before(endCalendar)) {
//            Date result = calendar.getTime();
//            datesInRange.add(result);
//            calendar.add(Calendar.DATE, 1);
//        }
//        for (Date date : datesInRange) {
//            System.out.println(date);
//
//        }
//        return datesInRange;
//    }

    public static int onlyInteger() {

        Scanner sc = new Scanner(System.in);
        boolean continueInput = true;
        int i = 0;

        do {
            try {
                System.out.print("Enter an integer: ");
                i = sc.nextInt();
                continueInput = false;

            } catch (InputMismatchException e) {
                System.err.println("You may only enter integers . Try again: ");
                sc.nextLine();

            }

        } while (continueInput);
        return i;

    }

//    public static String notNull(String s) {
//        Scanner sc = new Scanner(System.in);
//        while (s.isEmpty() || s.matches(".*\\d.*")) {
//            System.err.println("Null values not allowed!"
//                    + "\n" + "Please try again:" + "\n");
//            s = sc.nextLine();
//
//        }
//
//        return s;
//    }

//    inserting a range of dates to schedule table
    
    public static List<String> getDates(
            Date startDate, Date endDate) {
        List<Date> datesInRange = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);

            SimpleDateFormat format = new SimpleDateFormat("EEE-MMM d-yyyy");
            String date = format.format(result);

            dates.add(date);

            System.out.println(date);
        }

        return dates;
    }

    public static String sumbitted(boolean submitted) {
        String result = submitted ? "YES" : "NO";

        return result;
    }
// a simple input format tranforms to another, more complex format
    
    public static String changeFormatString(String s) {
        Scanner sc = new Scanner(System.in);
        String s1 = null;
        SimpleDateFormat format2 = null;
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            format2 = new SimpleDateFormat("EEE-MMM d-yyyy");
            Date date = format1.parse(s);
            s1 = format2.format(date);
            System.out.println(s1);

        } catch (ParseException ex) {
            System.err.println("You have to use dd-MM-yyy format!");
            s = sc.nextLine();
            changeFormatString(s);

        }
        return s1;
    }

    public static String answerYesOrNo(String s) {
        Scanner sc = new Scanner(System.in);
        while (!s.equalsIgnoreCase("yes") && !s.equalsIgnoreCase("no")) {
            System.out.println("Please answer with yes or no");
            s = sc.nextLine().toLowerCase();
        }
        return s;
    }
// database show correct time but java don't - this method solves this problem
    
    public static Timestamp subtractTime(Timestamp t) {

        Timestamp t2 = null;
        try {
            t2 = new Timestamp(t.getTime() - (1000 * 60 * 60 * 2));
        } catch (NullPointerException e) {
            System.out.println();
        }
        return t2;
    }
//    for Student Menu - counting days between current date and expiration date (of assingment)

    public static long getDaysBetweenDates(Timestamp ts) {
        long dif = 0;

        try {
            Date date = new Date(ts.getTime());
            Date current = new Date();
            long diff = Math.abs(date.getTime() - current.getTime());
            dif = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            System.err.println("YOU HAVE " + dif + " DAYS UNTIL EXPIRATION DATE!");
            return dif;
        } catch (NullPointerException e) {
            System.err.println("THERE IS NO EXPIRATION DATE YET!");
        }
        return dif;
    }
// database show correct time but java don't - this method solves this problem
    
    public static Timestamp addTime(Timestamp t) {
        Timestamp t2 = null;
        try {
            t2 = new Timestamp(t.getTime() + (1000 * 60 * 60 * 2));
        } catch (NullPointerException e) {
            System.out.println();
        }
        return t2;
    }
       public static String notNull(String s) {
        Scanner sc = new Scanner(System.in);
        while (StringUtils.isNullOrEmpty(s)) {
            System.err.println("Null values not allowed!"
                    + "\n" + "Please try again:" + "\n");
            s = sc.nextLine();

        }

        return s;
    }

}
