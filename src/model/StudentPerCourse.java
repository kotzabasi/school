/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author liana
 */
public class StudentPerCourse {
    
    private int enrol_id;
    private int student_id;
    private int course_id;
    private int secondCourse_id;

    public StudentPerCourse(int enrol_id, int student_id, int course_id) {
        this.enrol_id = enrol_id;
        this.student_id = student_id;
        this.course_id = course_id;
    }
    public StudentPerCourse(){
        
    }

    public int getEnrol_id() {
        return enrol_id;
    }

    public void setEnrol_id(int enrol_id) {
        this.enrol_id = enrol_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getSecondCourse_id() {
        return secondCourse_id;
    }

    public void setSecondCourse_id(int secondCourse_id) {
        this.secondCourse_id = secondCourse_id;
    }

  
    
    
    
    
}
