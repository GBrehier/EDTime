package com.example.edtime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

public class EDTimeBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "edtime.db";
    private static final String TABLE_USER = "user";
    private static final String COL_NOM = "nom";
    private static final int NUM_COL_NOM = 0;
    private static final String COL_TEMPS = "temps";
    private static final int NUM_COL_TEMPS = 1;
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

    public long insertUser(String nom, int temps){

        ContentValues values = new ContentValues();

        values.put(COL_TEMPS, temps);
        values.put(COL_NOM, nom);

        return bdd.insert(TABLE_USER, null, values);
    }

    /*
    public int updateLivre(int id, Livre livre){


        ContentValues values = new ContentValues();
        values.put(COL_TEMPS, livre.getIsbn());
        values.put(COL_TITRE, livre.getTitre());
        return bdd.update(TABLE_USER, values, COL_NOM + " = " +id, null);
    }

    public int removeLivreWithID(int id){
        return bdd.delete(TABLE_USER, COL_NOM + " = " +id, null);
    }

     */

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
    //Cette méthode permet de convertir un cursor en un livre
    private Livre cursorToLivre(Cursor c){



        c.moveToFirst();

        Livre livre = new Livre();

        livre.setId(c.getInt(NUM_COL_NOM));
        livre.setIsbn(c.getString(NUM_COL_TEMPS));
        livre.setTitre(c.getString(NUM_COL_TITRE));

        c.close();

        return livre;
    }

     */
}