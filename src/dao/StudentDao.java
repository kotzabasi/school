/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import menus.Home;
import model.Student;
import model.User;
import utils.DBUtils;

/**
 *
 * @author liana
 */
public class StudentDao {

    public static int student_id;
    public static  String first_name;
    public static String last_name;
    static LocalDate date_of_birth;
    static int enroll_id;

    public static ArrayList<Student> getAllStudents() {
        ArrayList<Student> list = new ArrayList<Student>();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select * from student";

        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {

                int student_id = rs.getInt("student_id");
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                LocalDate date_of_birth = rs.getDate("date_of_birth").toLocalDate();
                int tuition_fees = rs.getInt("tuition_fees");
                int enroll_id = rs.getInt("enroll_id");
                Student student = new Student(student_id, firstname, lastname, date_of_birth, tuition_fees, enroll_id);

                list.add(student);
                System.out.println(student.toString());

            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return list;

    }

    public static void insertStudent() {
        Connection con = DBUtils.getConnection();
        PreparedStatement st = null;
        PreparedStatement us = null;
        
        String insertStudent = "insert into student (first_name,last_name,date_of_birth,tuition_fees) values(?,?,?,?)";
        String insertUser = "INSERT into user (username,password,role) VALUES (?,?,'student')";
        Student student = new Student();
        User user = new User();
        Scanner sc = new Scanner(System.in);
            checkCreatedStudent(first_name, last_name);

        try {
            con.setAutoCommit(false);
            st = con.prepareStatement(insertStudent);
            us = con.prepareStatement(insertUser);
            student.setFirst_name(first_name);
            student.setLast_name(last_name);
            user.setUsername(last_name);
            user.setPassword(last_name);

            boolean dateInput = false;
            while (!dateInput) {
                try {
                    System.out.println("DATE OF BIRTH(yyyy-MM-dd): ");
                    String date = sc.nextLine();
                    LocalDate date_of_birth = LocalDate.parse(date);
                    LocalDate local = date_of_birth.plusDays(1);
                    student.setDate_of_birth(local);
                    dateInput = true;
                } catch (DateTimeParseException e) {
                    dateInput = false;
                    System.err.println("Input Error!");

                }
            }
            System.out.println("Tuition Fees: ");
            int tuition_fees = UtilsDao.onlyInteger();
            student.setTuition_fees(tuition_fees);

            st.setString(1, student.getFirst_name());
            st.setString(2, student.getLast_name());
            st.setObject(3, student.getDate_of_birth());
            st.setInt(4, student.getTuition_fees());

            us.setString(1, user.getUsername());
            us.setString(2, user.getPassword());

            st.execute();
            us.execute();
            con.commit();

            System.err.println("YOU HAVE INSERTED A STUDENT:");
            System.out.println("\n");

        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                st.close();
                us.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        fetchStudentId(first_name, last_name);
        setStudentIdToUser(student_id);

    }

    public static int fetchStudentId(String first_name, String last_name) {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT student_id FROM student WHERE first_name='" + first_name + "'" + "AND last_name='" + last_name + "'";

        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {

                student_id = rs.getInt("student_id");

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
        return student_id;
    }

    public static void setStudentIdToUser(int student_id) {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE user SET student_id='" + student_id + "'" + "WHERE username='" + last_name + "'" + "AND password='" + last_name + "'";

        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();

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

    }

    public static void insertEnrollId(int enroll_id) {

        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE student SET enroll_id='" + enroll_id + "'" + "WHERE student_id='" + student_id + "'";

        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();

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

    }

    public static void deleteEnrollId(int enroll_id) {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE student SET enroll_id= null WHERE student_id='" + student_id + "'";
        boolean change = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
            change = false;
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
        if (change) {
            System.err.println("\n" + "enroll ID has been set to null for " + first_name + " " + last_name);

        }

    }

    public static void deleteStudent() {
        getAllStudents();
        checkIfStudentExists();
        Connection con = DBUtils.getConnection();

        PreparedStatement deleteStudent = null;
        PreparedStatement deleteUser = null;
        PreparedStatement deleteStPerCourse = null;

        String student = "DELETE FROM student WHERE student_id=?";
        String user = "DELETE FROM user WHERE student_id=?";
        String perCourse = "DELETE FROM student_per_course WHERE student_id=?";
        boolean result = false;
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please type the student_id if  you want to delete this student: ");
            student_id = UtilsDao.onlyInteger();
            checkByIdAndLastname();

            System.out.println("Are you sure you want to delete this student?");
            System.out.println("Please answer with yes or no");
            String answer = sc.nextLine();

            while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
                System.out.println("Please answer with yes or no");
                answer = sc.nextLine();
            }
            switch (answer) {
                case "yes":

                    con.setAutoCommit(false);
                    deleteStudent = con.prepareStatement(student);
                    deleteUser = con.prepareStatement(user);
                    deleteStPerCourse = con.prepareStatement(perCourse);
                    deleteStudent.setInt(1, student_id);
                    deleteUser.setInt(1, student_id);
                    deleteStPerCourse.setInt(1, student_id);
                    deleteStudent.execute();
                    deleteUser.execute();
                    deleteStPerCourse.execute();
                    con.commit();
                    System.err.println("STUDENT HAS BEEN DELETED");
                    break;
                case "no":
                    Home.headmasterMenu();
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
            result = false;

        } catch (ParseException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                deleteStudent.close();
                deleteUser.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (result) {
                System.err.println("STUDENT HAS BEEN DELETED");
            }
        }

    }

    public static void checkIfStudentExists() {

        Connection con = DBUtils.getConnection();
        Scanner sc = new Scanner(System.in);
        PreparedStatement pst = null;
        try {
            System.out.println("FIRST NAME: ");
            first_name = UtilsDao.notNull(sc.nextLine());
            System.out.println("LAST NAME: ");
            last_name = UtilsDao.notNull(sc.nextLine());

            String sql = "SELECT student_id,enroll_id,first_name,last_name from student where first_name='" + first_name + "'"
                    + "AND last_name='" + last_name + "'";

            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            if (rs.next()) {
                student_id = rs.getInt("student_id");
                enroll_id = rs.getInt("enroll_id");
                System.out.println("\n" + "Student ID: " + student_id + "\n" + "Enroll ID: " + enroll_id + "\n"
                        + "First Name: " + first_name + "\n" + "Last Name: " + last_name);
            } else {
                System.err.println("this name does not exist. please try again: ");
                checkIfStudentExists();
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

    }

    public static void checkCreatedStudent(String firstname, String lastname) {
        Connection con = DBUtils.getConnection();
        Scanner sc = new Scanner(System.in);
        System.out.println("FIRST NAME: ");
            first_name = UtilsDao.notNull(sc.nextLine());
            System.out.println("LAST NAME: ");
            last_name = UtilsDao.notNull(sc.nextLine());
  
        PreparedStatement pst = null;
        String sql = "SELECT student_id,enroll_id,first_name,last_name from student where first_name='" + first_name + "'"
                + "AND last_name='" + last_name + "'";
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {
                student_id = rs.getInt("student_id");
                System.err.print("This student already exists!");
                System.out.print("Student ID: " + student_id + "\n");
                System.out.println("\n");
                System.err.println("Try again: ");
                checkCreatedStudent(firstname, lastname);
                
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

    }

    public static void checkByIdAndLastname() {

        Scanner sc = new Scanner(System.in);
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        try {

            String sql = "SELECT student_id,first_name,last_name from student where last_name='" + last_name + "'"
                    + "AND student_id='" + student_id + "'";

            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            if (rs.next()) {
                student_id = rs.getInt("student_id");
                first_name = rs.getString("first_name");
                last_name = rs.getString("last_name");

            } else {
                System.err.println("This user does not exist. please try again: ");
                student_id = UtilsDao.onlyInteger();
                checkByIdAndLastname();
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

    }

    public static void updateStudent() throws ParseException {
        getAllStudents();
        System.out.println("Please type the first and the last name of the entry you want to update: ");
        checkIfStudentExists();
        Scanner sc = new Scanner(System.in);
        System.out.println("Please type the student_id of the entry you want to update: ");

        Student student = new Student();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select * from student where student_id=?";
        student_id = UtilsDao.onlyInteger();
        checkByIdAndLastname();

        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, student_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                student.setStudent_id(rs.getInt(1));
                student.setFirst_name(rs.getString(2));
                student.setLast_name(rs.getString(3));
                student.setTuition_fees(rs.getInt(4));
                student.setDate_of_birth(rs.getDate(5).toLocalDate());
                System.out.println(student.toString());
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
        System.out.println("Which field do you want to update?" + "\n" + "1.First Name" + "\n" + "2.Last Name" + "\n"
                + "3.Tuition Fees" + "\n" + "4.Birth Date" + "\n" + "5.Exit" + "\n");
        int fieldUpdate = 0;

        do {
            try {
                fieldUpdate = sc.nextInt();
                if ((fieldUpdate < 1) || (fieldUpdate > 5)) {
                    System.err.print("You must enter a number from 1 to 5!");

                } else {
                    switch (fieldUpdate) {
                        case 1:
                            updateStudentFirstName(student);
                            Home.headmasterMenu();
                            break;
                        case 2:
                            updateStudentLastName(student);
                            Home.headmasterMenu();
                            break;
                        case 3:
                            updateStudentTuitionFees(student);
                            Home.headmasterMenu();
                            break;
                        case 4:
                            updateStudentDateOfBirth(student);
                            Home.headmasterMenu();
                            break;
                        case 5:
                            Home.headmasterMenu();
                    }

                }
            } catch (InputMismatchException e) {
                System.err.println("You may only enter integers . Try again.");
                sc.next();

            }
        } while (sc.hasNext());

    }

    public static void updateStudentFirstName(Student student) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please type the new first name: ");
        first_name = UtilsDao.notNull(sc.nextLine());
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE student SET first_name='" + first_name + "'" + "WHERE student_id='" + student_id + "'";
        boolean change = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
            change = false;
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

        if (change) {
            System.err.println("\n" + "STUDENT HAS BEEN UPDATED" + "\n");
            showByStudentId();

        }

    }

    public static void showByStudentId() {
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT*FROM student WHERE student_id='" + student_id + "'";
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            while (rs.next()) {

                student_id = rs.getInt("student_id");
                first_name = rs.getString("first_name");
                last_name = rs.getString("last_name");
                LocalDate date_of_birth = rs.getDate("date_of_birth").toLocalDate();
                int tuition_fees = rs.getInt("tuition_fees");
                enroll_id = rs.getInt("enroll_id");
                Student student = new Student(student_id, first_name, last_name, date_of_birth, tuition_fees, enroll_id);
                System.out.println(student.toString());
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

    }

    public static void updateStudentLastName(Student student) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please type the new last name: ");
        last_name = UtilsDao.notNull(sc.nextLine());
        Connection con = DBUtils.getConnection();

        PreparedStatement pstStudent = null;
        PreparedStatement pstUserU = null;
        PreparedStatement pstUserP = null;

        String sql = "UPDATE student SET last_name='" + last_name + "'" + "WHERE student_id='" + student_id + "'";
        String sqlL = "UPDATE user SET username='" + last_name + "'" + "WHERE student_id='" + student_id + "'";
        String sqlP = "UPDATE user SET password='" + last_name + "'" + "WHERE student_id='" + student_id + "'";
        boolean change = false;
        try {
            con.setAutoCommit(false);
            pstStudent = con.prepareStatement(sql);
            pstUserU = con.prepareStatement(sqlL);
            pstUserP = con.prepareStatement(sqlP);
            pstStudent.execute();
            pstUserU.execute();
            pstUserP.execute();
            con.commit();
            change = true;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
            change = false;
        } finally {
            try {
                pstStudent.close();
                pstUserU.close();
                pstUserP.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (change) {
            System.err.println("\n" + "STUDENT HAS BEEN UPDATED" + "\n");
            showByStudentId();

        }

    }

    public static void updateStudentTuitionFees(Student student) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please type the new tuition fees: ");
        int tuition_fees = UtilsDao.onlyInteger();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE student SET tuition_fees='" + tuition_fees + "'" + "WHERE student_id='" + student_id + "'";
        boolean change = false;
        try {
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
            change = false;
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

        if (change) {
            System.err.println("\n" + "STUDENT HAS BEEN UPDATED" + "\n");
            showByStudentId();

        }

    }

    public static void updateStudentDateOfBirth(Student student) throws ParseException {
        Scanner sc = new Scanner(System.in);
        boolean bDate = false;
        boolean change;
        String date = null;

        while (!bDate) {
            try {

                System.out.println("Please type the new date of birth (yyyy-MM-DD): ");
                date = sc.nextLine();
                date_of_birth = LocalDate.parse(date);
                bDate = true;
            } catch (DateTimeParseException e) {
                bDate = false;
                System.err.println("Input Error: ");
                date = sc.nextLine();
            }
        }

        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;

        try {
            String sql = "UPDATE student SET date_of_birth='" + date_of_birth + "'" + "WHERE student_id='" + student_id + "'";
            pst = con.prepareStatement(sql);
            pst.executeUpdate();
            change = true;
        } catch (SQLException ex) {
            Logger.getLogger(StudentDao.class.getName()).log(Level.SEVERE, null, ex);
            change = false;
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
        if (change) {
            System.err.println("STUDENT'S BIRTH DATE HAS BEEN UPDATED");
        }
    }

    public static ArrayList<Student> studentsNotEnrolled() {
        ArrayList<Student> list = new ArrayList<Student>();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select * from student where enroll_id is null";

        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {

                int student_id = rs.getInt("student_id");
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                LocalDate date_of_birth = rs.getDate("date_of_birth").toLocalDate();
                int tuition_fees = rs.getInt("tuition_fees");
                int enroll_id = rs.getInt("enroll_id");
                Student student = new Student(student_id, firstname, lastname, date_of_birth, tuition_fees, enroll_id);

                list.add(student);
                System.out.println(student.toString());

            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();

            } catch (SQLException ex) {
                Logger.getLogger(Student.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(Student.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        return list;

    }

    public static ArrayList<Student> studentsEnrolled() {
        ArrayList<Student> list = new ArrayList<Student>();
        Connection con = DBUtils.getConnection();
        PreparedStatement pst = null;
        String sql = "select * from student where enroll_id is not null";

        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);

            while (rs.next()) {

                int student_id = rs.getInt("student_id");
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                LocalDate date_of_birth = rs.getDate("date_of_birth").toLocalDate();
                int tuition_fees = rs.getInt("tuition_fees");
                int enroll_id = rs.getInt("enroll_id");
                Student student = new Student(student_id, firstname, lastname, date_of_birth, tuition_fees, enroll_id);

                list.add(student);
                System.out.println(student.toString());
                StudentPerCourseDao.showCourseEnrolled(rs.getInt("student_id"));
                System.out.println("\n");

            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pst.close();

            } catch (SQLException ex) {
                Logger.getLogger(Student.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(Student.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        return list;

    }

}
