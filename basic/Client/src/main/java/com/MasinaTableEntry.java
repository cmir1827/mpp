package com;

import com.model.Masina;
import com.model.PunctControl;
import com.util.IgnoreTable;

import java.time.LocalDate;

public class MasinaTableEntry {
    private int id;
    private String nume;
    private int numarPunctControl;
    private LocalDate date;

    @IgnoreTable
    Masina masina;

    @IgnoreTable
    PunctControl punctControl;

    public Masina getMasina() {
        return masina;
    }

    public void setMasina(Masina masina) {
        this.masina = masina;
    }

    public PunctControl getPunctControl() {
        return punctControl;
    }

    public void setPunctControl(PunctControl punctControl) {
        this.punctControl = punctControl;
    }

    public MasinaTableEntry(int id, String nume, int numarPunctControl, LocalDate date, Masina masina, PunctControl punctControl) {
        this.id = id;
        this.nume = nume;
        this.numarPunctControl = numarPunctControl;
        this.date = date;
        this.masina = masina;
        this.punctControl = punctControl;
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
