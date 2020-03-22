package com.example.edtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AccueilNonConnecte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_non_connecte);

        final EditText editTextNom = findViewById(R.id.editTextNom);

        final EditText editTextTemps = findViewById(R.id.editTextTemps);

        final Button boutonConfirmer = findViewById(R.id.buttonRenseignement);

        final EDTimeBDD bd = new EDTimeBDD(this);


        boutonConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bd.open();

                String nom = editTextNom.getText().toString();
                int temps = Integer.valueOf(editTextTemps.getText().toString());

                bd.insertUser(nom,temps);
                bd.close();

                Intent intent = new Intent(AccueilNonConnecte.this, AccueilConnecte.class);
                startActivity(intent);

            }
        });


    }
}
