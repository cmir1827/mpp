package com.model;

import java.util.List;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class MasinaPunctControl {
    private Masina masina;
    private PunctControl punctControl;

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