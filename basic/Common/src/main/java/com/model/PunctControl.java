package com.model;

import java.util.List;

public class PunctControl {
    private Integer id;
    private Integer numarControl;
    TSUser user;

    public PunctControl() {
    }

    public PunctControl(Integer numarControl) {
        this.numarControl = numarControl;
    }

    public PunctControl(Integer id, Integer numarControl,  TSUser user) {
        this.id = id;
        this.user = user;
        this.numarControl = numarControl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumarControl() {
        return numarControl;
    }

    public void setNumarControl(Integer numarControl) {
        this.numarControl = numarControl;
    }

    public TSUser getUser() {
        return user;
    }

    public void setUser(TSUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PunctControl{" +
                "id=" + id +
                ", numarControl=" + numarControl +
                ", user=" + user +
                '}';
    }
}
