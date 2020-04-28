package com.example.edtime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class EDTimeBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "edtime.db";

    private static final String TABLE_USER = "user";
    private static final String COL_NOM = "nom";
    private static final int NUM_COL_NOM = 0;
    private static final String COL_TEMPS = "temps";
    private static final int NUM_COL_TEMPS = 1;

    private static final String TABLE_COURS = "cours";
    private static final String COL_ID = "id";
    private static final int NUM_COL_ID = 0;
    private static final String COL_JOUR = "jour";
    private static final int NUM_COL_JOUR = 1;
    private static final String COL_HEURE = "heure";
    private static final int NUM_COL_HEURE = 2;
    private static final String COL_MINUTES = "minutes";
    private static final int NUM_COL_MINUTES = 3;
    private static final String COL_NOMCOURS = "nomCours";
    private static final int NUM_COL_NOMCOURS = 4;
    private static final String COL_SALLE = "salle";
    private static final int NUM_COL_SALLE = 5;

    private SQLiteDatabase bdd;
    private MaBaseSqLite maBaseSQLite;

    public EDTimeBDD(Context context){
        maBaseSQLite = new MaBaseSqLite(context, NOM_BDD, null, VERSION_BDD);
    }
    public void open(){
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }
    /*
    PARTIE REQUETE TABLE USER
     */

    public long insertUser(String nom, int temps){

        ContentValues values = new ContentValues();

        values.put(COL_TEMPS, temps);
        values.put(COL_NOM, nom);

        return bdd.insert(TABLE_USER, null, values);
    }


    public int updateUser(int temps, String nom){

        ContentValues values = new ContentValues();

        values.put(COL_TEMPS, temps);

        return bdd.update(TABLE_USER, values, COL_NOM + " = '" +nom+"'", null);
    }

    public String[] getUser(){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_USER);
        Cursor c = qb.query(bdd, new String[] {COL_NOM, COL_TEMPS}, null, null, null,
                null, null);
        if (c.getCount() == 0) return null;

        c.moveToFirst();

        return new String[] {c.getString(NUM_COL_NOM), String.valueOf(c.getInt(NUM_COL_TEMPS))};
    }

    /*
    PARTIE REQUETE TABLE COURS
     */

    public Cours[] getCours(){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_COURS);
        Cursor c = qb.query(bdd, new String[] {COL_ID,COL_JOUR,COL_HEURE,COL_MINUTES,COL_NOMCOURS, COL_SALLE}, null, null, null,
                null, null);
        if (c.getCount() == 0) return null;

        Cours[] listeCours = new Cours[c.getCount()];
        c.moveToFirst();
        for( int i=0;i<c.getCount();i++){
            listeCours[i]= cursorToCours(c);
            if(!c.isLast())c.moveToNext();
        }
        c.close();
        return listeCours;
    }

    public int getNbCours(){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_COURS);
        Cursor c = qb.query(bdd, new String[] {COL_JOUR,COL_HEURE,COL_MINUTES,COL_NOMCOURS, COL_SALLE}, null, null, null,
                null, null);

        return c.getCount();
    }

    public Cours[] getCoursDuJour(int jour){
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_COURS);
        Cursor c = qb.query(bdd, new String[] {COL_ID,COL_JOUR,COL_HEURE,COL_MINUTES,COL_NOMCOURS, COL_SALLE}, COL_JOUR +" = "+jour, null, null,
                null, null);
        if (c.getCount() == 0) return null;

        Cours[] listeCours = new Cours[c.getCount()];
        c.moveToFirst();
        for( int i=0;i<c.getCount();i++){
            listeCours[i]= cursorToCours(c);
            if(!c.isLast())c.moveToNext();
        }
        c.close();
        return listeCours;
    }

    public long insertCours(Cours cours){

        ContentValues values = new ContentValues();

        values.put(COL_JOUR, cours.getJour());
        values.put(COL_HEURE, cours.getHeures());
        values.put(COL_MINUTES, cours.getMinutes());
        values.put(COL_NOMCOURS, cours.getNomCours());
        values.put(COL_SALLE, cours.getSalle());

        return bdd.insert(TABLE_COURS, null, values);
    }


    public int removeCours(){
        return bdd.delete(TABLE_COURS, "1", null);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Cours cursorToCours(Cursor c){

        Cours cours = new Cours();
        cours.setId(c.getInt(NUM_COL_ID));
        cours.setJour(c.getInt(NUM_COL_JOUR));
        cours.setHeures(c.getInt(NUM_COL_HEURE));
        cours.setMinutes(c.getInt(NUM_COL_MINUTES));
        cours.setNomCours(c.getString(NUM_COL_NOMCOURS));
        cours.setSalle(c.getString(NUM_COL_SALLE));

        return cours;
    }


}