package com.pouillos.mypilulier.entities;

import com.orm.SugarRecord;
import com.pouillos.mypilulier.activities.utils.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Profil extends SugarRecord implements Serializable, Comparable<Profil> {

    private Utilisateur utilisateur;
    private float poids;
    private int taille;
    private float imc;
    private Date date;

    public Profil() {
    }

    public Profil( Utilisateur utilisateur, float poids, int taille, Date date) {

        this.utilisateur = utilisateur;
        this.poids = poids;
        this.taille = taille;
        this.date = date;
        this.imc = calculerImc();
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public float getPoids() {
        return floatArrondi(poids,2);
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public float getImc() {
        return floatArrondi(imc,2);
    }

    public void setImc(float imc) {
        this.imc = imc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getImcArrondi() {
        //DecimalFormat df = new DecimalFormat("#.##");
        //System.out.println(df.format(imc));

       // return df.format(imc);
        return ((float) Math.round(imc*10))/10;

    }

    @Override
    public int compareTo(Profil o) {
        return o.date.compareTo(this.date);
    }

    public float calculerImc() {
        float poids = this.getPoids();
        float taille = ((float) this.getTaille())/100;
        float tailleCarre = taille*taille;
        float imc = ((float) Math.round(poids/tailleCarre*10))/10;
        return imc;
    }

    @Override
    public String toString() {
        return DateUtils.ecrireDate(date)+ " - " + poids + " kgs / " + taille + " cm";
    }

    protected static float floatArrondi(float number, int decimalPlace) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
