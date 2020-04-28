package com.example.edtime;

public class Cours {

    private int id;
    private String nomCours;
    private String salle;
    private int jour;
    private int heures;
    private int minutes;

    public Cours() {
    }

    public Cours(String nomCours, String salle, int jour, int heures, int minutes) {
        this.nomCours = nomCours;
        this.salle = salle;
        this.jour = jour;
        this.heures = heures;
        this.minutes = minutes;
    }

    public String getNomCours() {
        return nomCours;
    }

    public void setNomCours(String nomCours) {
        this.nomCours = nomCours;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }



    public int getHeures() {
        return heures;
    }

    public void setHeures(int heures) {
        this.heures = heures;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return transformJour(jour) +"         "+ heures+"h"  +  minutes +"         "+ nomCours +"         " + salle;
    }

    public String transformJour(int jour){
        switch (jour){
            case 2: return "Lundi";

            case 3: return "Mardi";

            case 4: return "Mercredi";

            case 5: return "Jeudi";

            case 6: return "Vendredi";

        }
        return "";
    }

    public int transformJourEnInt(String jour){
        switch (jour){
            case "Lundi": return 2;

            case "Mardi": return 3;

            case "Mercredi": return 4;

            case "Jeudi": return 5;

            case "Vendredi": return 6;

        }
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
