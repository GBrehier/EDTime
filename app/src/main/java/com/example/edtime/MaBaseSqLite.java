package com.example.edtime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MaBaseSqLite extends SQLiteOpenHelper {

    private static final String TABLE_USER = "user";
    private static final String COL_NOM = "nom";
    private static final String COL_TEMPS = "temps";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " ("
            + COL_NOM + " TEXT NOT NULL, "
            + COL_TEMPS + " TEXT NOT NULL );";

    private static final String TABLE_COURS = "cours";
    private static final String COL_ID = "id";
    private static final String COL_JOUR = "jour";
    private static final String COL_HEURE = "heure";
    private static final String COL_MINUTES = "minutes";
    private static final String COL_NOMCOURS = "nomCours";
    private static final String COL_SALLE = "salle";

    private static final String CREATE_TABLE_COURS = "CREATE TABLE " + TABLE_COURS + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_JOUR + " INTEGER NOT NULL, "
            + COL_HEURE + " INTEGER NOT NULL, "
            + COL_MINUTES + " INTEGER NOT NULL, "
            + COL_NOMCOURS + " TEXT NOT NULL, "
            + COL_SALLE + " TEXT NOT NULL );";

    public MaBaseSqLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//on crée la table à partir de la requête écrite dans la variable CREATE_TABLE_USER
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_COURS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//On écrit ici ce qu’il faut faire lors d’une mise à jour de l’application. Par exemple supprimer la table et recommencer
        db.execSQL("DROP TABLE " + TABLE_USER + ";");
        db.execSQL("DROP TABLE " + TABLE_COURS + ";");
        onCreate(db);
    }
}