package com.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class MasinaPunctControl {
    private int id;
    private Masina masina;
    private PunctControl punctControl;
    private LocalDate timpTrecere;


    public LocalDate getTimpTrecere() {
        return timpTrecere;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimpTrecere(LocalDate timpTrecere) {
        this.timpTrecere = timpTrecere;
    }

    public MasinaPunctControl(int id, Masina masina, PunctControl punctControl, LocalDate timpTrecere) {
        this.id = id;
        this.masina = masina;
        this.punctControl = punctControl;
        this.timpTrecere = timpTrecere;
    }

    public MasinaPunctControl(Masina masina, PunctControl punctControl, LocalDate timpTrecere) {
        this.masina = masina;
        this.punctControl = punctControl;
        this.timpTrecere = timpTrecere;
    }

    public MasinaPunctControl(Masina masina, PunctControl punctControl) {
        this.masina = masina;
        this.punctControl = punctControl;
    }

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
}