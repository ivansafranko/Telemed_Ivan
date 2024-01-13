package com.telemed.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name="APP_USER")
public class User {
    @Id
    @GeneratedValue
    private int id;
    static int idCounter = 0;
    private int type = 0;
    private String fname;
    private String lname;
    private String birthday;
    private int mbo;
    private String email;
    private String password;
    @Column(columnDefinition="BOOLEAN default 'FALSE'")
    private boolean passwordUpdated;

    public User() {
    }
    public User(String fname, String lname, String birthday, int mbo, String email, String password, boolean passwordUpdated) {
        this.fname = fname;
        this.lname = lname;
        this.birthday = birthday;
        this.mbo = mbo;
        this.email = email;
        this.password = password;
        this.passwordUpdated = passwordUpdated;

        id = idCounter++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    public static void setIdCounter(int idCounter) {
        User.idCounter = idCounter;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getMbo() {
        return mbo;
    }

    public void setMbo(int mbo) {
        this.mbo = mbo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordChanged() {
        this.passwordUpdated = true;
    }

    public boolean hasUpdatedPassword() {
        return this.passwordUpdated;
    }
}
