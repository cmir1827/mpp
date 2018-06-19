package com.model;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class TestCultura {
    private Integer id;
    private String nume;

    public TestCultura() {
    }

    public TestCultura(String nume) {
        this.nume = nume;
    }

    public TestCultura(Integer id, String nume) {
        this.id = id;
        this.nume = nume;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return nume;
    }
}
