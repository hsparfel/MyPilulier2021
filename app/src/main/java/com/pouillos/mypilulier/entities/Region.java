package com.pouillos.mypilulier.entities;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

import java.io.Serializable;

public class Region extends SugarRecord implements Serializable, Comparable<com.pouillos.mypilulier.entities.Region> {

    @NotNull
    private String nom;

    public Region() {
    }

    public Region(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

        @Override
    public int compareTo(com.pouillos.mypilulier.entities.Region o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public String toString() {
        return nom;
    }
}
