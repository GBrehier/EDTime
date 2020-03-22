package com.example.edtime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MaBaseSqLite extends SQLiteOpenHelper {

    private static final String TABLE_USER = "user";
    private static final String COL_NOM = "nom";
    private static final String COL_TEMPS = "temps";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_USER + " ("
            + COL_NOM + " TEXT NOT NULL, "
            + COL_TEMPS + " TEXT NOT NULL );";

    public MaBaseSqLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//On écrit ici ce qu’il faut faire lors d’une mise à jour de l’application. Par exemple supprimer la table et recommencer
        db.execSQL("DROP TABLE " + TABLE_USER + ";");
        onCreate(db);
    }
}