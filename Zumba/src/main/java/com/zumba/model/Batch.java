package com.zumba.model;

public class Batch {
    private int id;
    private String time;

    public Batch (){}

    public Batch(String time) {
        this.time = time;
    }

    public Batch(int id, String time) {
        this.id = id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
