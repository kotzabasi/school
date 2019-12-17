/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.time.LocalDate;
import java.util.Scanner;


/**
 *
 * @author liana
 */
public class Course {

    private int course_id;
    private String title;
    private LocalDate start_date;
    private LocalDate end_date;
    private String stream;
    private String type;
    private boolean schedule;

    public Course(int course_id, String title, LocalDate start_date, LocalDate end_date, String stream, String type, boolean schedule) {
        this.course_id = course_id;
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.stream = stream;
        this.type = type;
        this.schedule = schedule;
    }

  

  

    public Course() {

    }

    public Course(int course_id, String title, String stream, String type) {
        this.course_id = course_id;
        this.title = title;
        this.stream = stream;
        this.type = type;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

 
  
    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSchedule() {
        return schedule;
    }

    public void setSchedule(boolean schedule) {
        this.schedule = schedule;
    }
    

    @Override
    public String toString() {
        return "Title: " + title + "\n"
                + "Start Date: " + start_date + "\n"
                + "End Date: " + end_date + "\n"
                + "Stream: " + stream + "\n"
                + "Type: " + type + "\n";
    }

    public static String insertType() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Type (full or part):");
        String s = sc.nextLine().toLowerCase();

        do {
            if (s.equalsIgnoreCase("full") || s.equalsIgnoreCase("part")) {
                break;
            } else {
                System.err.println("Please insert FULL or PART ");
                s = sc.nextLine().toLowerCase();
            }
        } while (true);
        return s;
    }

    public static String insertStream() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Stream (You have to choose between java, python,front):");
        String s = sc.nextLine().toUpperCase();

        do {
            if (s.equalsIgnoreCase("JAVA") || s.equalsIgnoreCase("PYTHON")|| s.equalsIgnoreCase("FRONT")){
                break;
            } else {
                System.err.println("You have to choose between java, python,front");
                s = sc.nextLine().toUpperCase();
            }

        } while (true);
        return s;

    }

}
