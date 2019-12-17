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
public class AssignmentsPerCourse {
    
    private int course_id;
    private int assignment_id;

    public AssignmentsPerCourse(int course_id, int assignment_id) {
        this.course_id = course_id;
        this.assignment_id = assignment_id;
    }
    
    public AssignmentsPerCourse(){
        
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(int assignment_id) {
        this.assignment_id = assignment_id;
    }
    
}
