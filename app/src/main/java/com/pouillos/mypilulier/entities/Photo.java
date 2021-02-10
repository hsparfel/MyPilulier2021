package com.pouillos.mypilulier.entities;

import com.orm.SugarRecord;
import com.pouillos.mypilulier.activities.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;


public class Photo extends SugarRecord implements Serializable, Comparable<Photo>{

    private String type;
    private String path;
    private Long itemId;
    private Date date;

    public Photo() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(Photo o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public String toString() {
        String reponse = "";
        reponse += type + " - ";
        switch (type) {
            case "Ordonnance":
                RdvContact rdvContact = RdvContact.findById(RdvContact.class, itemId);
                reponse += rdvContact.getContact().toStringShort();
                break;
            case "Resultat Analyse":
                RdvAnalyse rdvAnalyse = RdvAnalyse.findById(RdvAnalyse.class, itemId);
                reponse += rdvAnalyse.getAnalyse().getName();
                break;
            case "Resultat Examen":
                RdvExamen rdvExamen = RdvExamen.findById(RdvExamen.class, itemId);
                reponse += rdvExamen.getExamen().getName();
                break;
            default:
                break;
        }


        reponse += " - " + DateUtils.ecrireDate(date);



        return  reponse;
    }
}
