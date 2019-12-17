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
public class AssignmentPerStudent {
    private int assignment_id;
    private int student_id;
    boolean submitted;
    private int oral_mark;
    private int total_mark;

    public AssignmentPerStudent(int assignment_id, int student_id, boolean submitted, int oral_mark, int total_mark) {
        this.assignment_id = assignment_id;
        this.student_id = student_id;
        this.submitted = submitted;
        this.oral_mark = oral_mark;
        this.total_mark = total_mark;
    }
    
    public AssignmentPerStudent(){
        
    }

    public int getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(int assignment_id) {
        this.assignment_id = assignment_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public int getOral_mark() {
        return oral_mark;
    }

    public void setOral_mark(int oral_mark) {
        this.oral_mark = oral_mark;
    }

    public int getTotal_mark() {
        return total_mark;
    }

    public void setTotal_mark(int total_mark) {
        this.total_mark = total_mark;
    }
    
    
    
    
    
}
