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
public class Trainer {
    
    private int trainer_id;
    private String firstname;
    private String lastname;
    private String subject;

    public Trainer(int trainer_id, String firstname, String lastname, String subject) {
        this.trainer_id = trainer_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.subject = subject;
    }
    
    public Trainer(){
        
    }

    public int getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(int trainer_id) {
    this.trainer_id = trainer_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
      this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
       this.lastname = lastname;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
      this.subject = subject;
    }
    @Override
    public String toString(){
       return    
                 "First Name: " + firstname + "\n"
                + "Last Name: " + lastname + "\n"
                + "Subject: " + subject;
                

    }
    
}
