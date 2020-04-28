package com.example.edtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Edt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView textViewNbCours = findViewById(R.id.textViewNbCours);

        final TextView textViewDesCours = findViewById(R.id.textViewDesCours);

        final Button buttonAjouterCours = findViewById(R.id.buttonAjouterCours);

        final Button buttonSupprimer = findViewById(R.id.buttonSupprimer);

        final EDTimeBDD bd = new EDTimeBDD(this);

        bd.open();
        int nbCours = bd.getNbCours();


        if(nbCours==0){
            textViewNbCours.setText("Vous n'avez aucun cours enregistré");
        }else{
            String pluriel = "sont enregistrés";
            if(nbCours==1) pluriel = "est enregistré";
            textViewNbCours.setText("Actuellement, "+nbCours+" cours "+pluriel);
            Cours[] listeCours = bd.getCours();
            String texteCours ="";
            for ( int i =0; i<listeCours.length; i++){
                texteCours+=listeCours[i].toString()+"\n";
            }
            buttonSupprimer.setVisibility(View.VISIBLE);
            textViewDesCours.setText(texteCours);
            textViewDesCours.setVisibility(View.VISIBLE);
        }

        bd.close();

        buttonAjouterCours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Edt.this, AjouterCoursEdt.class);
                startActivity(intent);

            }
        });

        buttonSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bd.open();
                bd.removeCours();
                bd.close();
                startActivity(getIntent());
            }
        });
    }
}
