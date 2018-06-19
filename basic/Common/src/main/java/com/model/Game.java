package com.model;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class Game {
    int id;
    TSUser user;
    TestCultura testCultura;
    int punctaj;

    public Game(int id, TSUser user, TestCultura testCultura, int punctaj) {
        this.id = id;
        this.user = user;
        this.testCultura = testCultura;
        this.punctaj = punctaj;
    }

    public Game(TSUser user, TestCultura testCultura, int punctaj) {
        this.user = user;
        this.testCultura = testCultura;
        this.punctaj = punctaj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TSUser getUser() {
        return user;
    }

    public void setUser(TSUser user) {
        this.user = user;
    }

    public TestCultura getTestCultura() {
        return testCultura;
    }

    public void setTestCultura(TestCultura testCultura) {
        this.testCultura = testCultura;
    }

    public int getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(int punctaj) {
        this.punctaj = punctaj;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", user=" + user +
                ", testCultura=" + testCultura +
                ", punctaj=" + punctaj +
                '}';
    }
}
