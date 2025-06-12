package com.zumba.model;

import jakarta.servlet.http.Part;

public class Participant {
    int pid;
    String name;
    String phone;
    String email;
    String batchTime;

    public Participant(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    public Participant(int pid, String name, String phone, String email) {
        this.pid = pid;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Participant(int pid, String name, String phone, String email, String batchTime) {
        this.pid = pid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.batchTime = batchTime;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String  getBatchTime() {
        return batchTime;
    }

    public void setBatchTime(String batchTime) {
        this.batchTime = batchTime;
    }
}
