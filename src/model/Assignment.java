/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import school.Utils;
import java.sql.Timestamp;
import java.util.Scanner;


/**
 *
 * @author liana
 */
public class Assignment {

    private int assignment_id;
    private String title;
    private String description_of_assignment;
    private Timestamp submission_date;
    private Timestamp expiration_date;
    private String stream;

    public Assignment(int assignment_id, String title, String description_of_assignment, Timestamp submission_date, Timestamp expiration_date, String stream) {
        this.assignment_id = assignment_id;
        this.title = title;
        this.description_of_assignment = description_of_assignment;
        this.submission_date = submission_date;
        this.expiration_date = expiration_date;
        this.stream = stream;
    }

    public Assignment(int assignment_id, String title, String description_of_assignment, String stream) {
        this.assignment_id = assignment_id;
        this.title = title;
        this.description_of_assignment = description_of_assignment;
        this.stream = stream;
    }

    
    public Assignment(){
        
    }

    public int getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(int assignment_id) {
        this.assignment_id = assignment_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription_of_assignment() {
        return description_of_assignment;
    }

    public void setDescription_of_assignment(String description_of_assignment) {
        this.description_of_assignment = description_of_assignment;
    }

    public Timestamp getSubmission_date() {
        return submission_date;
    }

    public void setSubmission_date(Timestamp submission_date) {
        this.submission_date = submission_date;
    }

    public Timestamp getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Timestamp expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

   
    
    @Override
    public String toString(){
        return "TITLE: "+title+"\n"+
                "Description: "+description_of_assignment+"\n"+
                "Submission Date: "+Utils.substractTime(submission_date)+"\n"+
                "Expiration Date: "+Utils.substractTime(expiration_date)+"\n"+
                "Stream: "+stream+"\n";
    

    }
    public static String insertStream() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Stream (You have to choose between java, python,css,react & javascript):");
        String s = sc.nextLine().toUpperCase();

        do {
            if (s.equalsIgnoreCase("JAVA") || s.equalsIgnoreCase("PYTHON")|| s.equalsIgnoreCase("CSS")
                    || s.equalsIgnoreCase("REACT")|| s.equalsIgnoreCase("JAVASCRIPT")){
                break;
            } else {
                System.err.println("You have to choose between java, python,css,react,javascript");
                s = sc.nextLine().toUpperCase();
            }

        } while (true);
        return s;

    }

}
