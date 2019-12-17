/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.sun.javafx.font.freetype.HBGlyphLayout;
import dao.AssignmentDao;
import static dao.AssignmentDao.title;
import dao.AssignmentPerStudentDao;
import dao.CourseDao;
import dao.ScheduleDao;
import dao.StudentDao;
import dao.StudentPerCourseDao;
import dao.UtilsDao;
import dao.TrainerDao;
import dao.TrainerPerCourseDao;
import static dao.UserDao.password;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import menus.Home;
import model.Assignment;
import utils.DBUtils;

/**
 *
 * @author liana
 */
public class School {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
//        String dateBeforeString = "07 12 2019";
//        String dateAfterString = "10 01 2020";
////Liana change the format of the dateformat
//        try {
//            Date dateBefore = myFormat.parse(dateBeforeString);
//            Date dateAfter = myFormat.parse(dateAfterString);
//            long difference = dateAfter.getTime() - dateBefore.getTime();
//            float daysBetween = (difference / (1000 * 60 * 60 * 24));
//            /* You can also convert the milliseconds to days using this method
//                * float daysBetween = 
//                *         TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
//             */
//            System.out.println("Number of Days between dates: " + daysBetween);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



Home.userLogin();
    }
}
