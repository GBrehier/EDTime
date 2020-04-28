package com.example.edtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                if (!isEmpty(editTextNom)&& !isEmpty(editTextTemps)) {
                    int temps = Integer.valueOf(editTextTemps.getText().toString());
                    if (temps > 0 && temps < 60) { // si le temps est positif

                        bd.open();

                        String nom = editTextNom.getText().toString();

                        bd.insertUser(nom, temps);
                        bd.close();

                        Intent intent = new Intent(AccueilNonConnecte.this, AccueilConnecte.class);
                        startActivity(intent);
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Le temps doit être entre 0 et 59", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Un ou des champs sont vides", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


    }
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
