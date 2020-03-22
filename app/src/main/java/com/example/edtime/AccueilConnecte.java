package com.example.edtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AccueilConnecte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_connecte);

        final EDTimeBDD bd = new EDTimeBDD(this);
        bd.open();

        TextView texte = findViewById(R.id.texteUser);
        String[] user = bd.getUser();
        texte.setText(user[0] +" : "+ user[1]);
        bd.close();
    }
}
