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
public class TrainerPerCourse {
    private int course_id;
    private int trainer_id;

    public TrainerPerCourse(int course_id, int trainer_id) {
        this.course_id = course_id;
        this.trainer_id = trainer_id;
    }
    public TrainerPerCourse(){
        
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(int trainer_id) {
        this.trainer_id = trainer_id;
    }
    
    
}
