package com;

import java.time.LocalDate;

public class MasinaTableEntry {
    private int id;
    private String nume;
    private int numarPunctControl;
    private LocalDate date;

    public MasinaTableEntry(int id, String nume, int numarPunctControl, LocalDate date) {
        this.id = id;
        this.nume = nume;
        this.numarPunctControl = numarPunctControl;
        this.date = date;
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

    public int getNumarPunctControl() {
        return numarPunctControl;
    }

    public void setNumarPunctControl(int numarPunctControl) {
        this.numarPunctControl = numarPunctControl;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
