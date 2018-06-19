package com.model;

import com.util.IgnoreTable;

import java.util.List;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class Masina {
    private int id;
    private String nume;

    public Masina() {}
    public Masina(String nume) {

        this.nume = nume;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return "Masina{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                '}';
    }
}
