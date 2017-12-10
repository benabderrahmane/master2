package com.example.yassine.tp1;

/**
 * Created by yassine on 08/12/17.
 */

public final class Evenement {
    private int id;
    private String typeE;
    private String titreE;
    private String dateE;
    private String heureE;
    private String descE;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeE() {
        return typeE;
    }

    public void setTypeE(String typeE) {
        this.typeE = typeE;
    }

    public String getTitreE() {
        return titreE;
    }

    public void setTitreE(String titreE) {
        this.titreE = titreE;
    }

    public String getDateE() {
        return dateE;
    }

    public void setDateE(String dateE) { this.dateE = dateE; }

    public String getHeureE() {
        return heureE;
    }

    public void setHeureE(String heureE) {
        this.heureE = heureE;
    }

    public String getDescE() {
        return descE;
    }

    public void setDescE(String descE) {
        this.descE = descE;
    }

    public Evenement() {}

    public Evenement(int id, String titreE, String typeE, String dateE, String heureE, String descE) {
        this.id = id;
        this.typeE = typeE;
        this.titreE = titreE;
        this.dateE = dateE;
        this.heureE = heureE;
        this.descE = descE;
    }

    @Override
    public String toString() {
        return "L'evenement " + titreE + " ( " + typeE + " ) est prévu le " + dateE + " à " + heureE + "\n Description de l'evenement :\n" + descE;
    }
}
