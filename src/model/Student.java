/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author liana
 */
public class Student {

    private int student_id;
    private String first_name;
    private String last_name;
     private java.time.LocalDate date_of_birth;
    private int tuition_fees;
    private int enroll_id;

    public Student(int student_id, String first_name, String last_name, LocalDate date_of_birth, int tuition_fees, int enroll_id) {
        this.student_id = student_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.tuition_fees = tuition_fees;
        this.enroll_id = enroll_id;
    }

  

    
    
    

    public Student() {

    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getTuition_fees() {
        return tuition_fees;
    }

    public void setTuition_fees(int tuition_fees) {
        this.tuition_fees = tuition_fees;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    

    public int getEnroll_id() {
        return enroll_id;
    }

    public void setEnroll_id(int enroll_id) {
        this.enroll_id = enroll_id;
    }

    @Override
    public String toString() {
        return   "STUDENT ID: "+student_id+"\n"
                +"First Name: " + first_name + "\n"
                + "Last Name: " + last_name + "\n"
                + "Date Of Birth: " + date_of_birth + "\n"
                + "Tuition Fees: " + tuition_fees + "\n"
                +"Enroll ID: "+enroll_id;
                        

    }

}
