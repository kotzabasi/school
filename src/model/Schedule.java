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
public class Schedule {

    private int id;
    private String date;
    private String schedule;
    private String time;

    public Schedule(int id, String date, String schedule, String time) {
        this.id = id;
        this.date = date;
        this.schedule = schedule;
        this.time = time;
    }

    public Schedule(int id, String date) {
        this.id = id;
        this.date = date;
        
    }

    public Schedule(int id, String date, String schedule) {
        this.id = id;
        this.date = date;
        this.schedule = schedule;
    }
    
    

    public Schedule() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return    "ID: " + id + "\n"
                + "DATE: " + date + "\n"
                + "SCHEDULE: " + schedule + "\n"
                + "TIME: " + time + "\n";

    }
}
