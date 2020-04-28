package com.example.edtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AccueilConnecte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_connecte);

        final TextView texte = findViewById(R.id.texteUser);

        final Button boutonEDT = findViewById(R.id.buttonEDT);

        final TextView texteNumTemps = findViewById(R.id.textViewNumTemps);

        final TextView texteMinutes = findViewById(R.id.textViewMinutes);

        final Button boutonModifier = findViewById(R.id.buttonModifierTemps);
        final Button boutonValider = findViewById(R.id.buttonValiderTemps);

        final EditText editTextTemps = findViewById(R.id.editTextModifierTemps);

        final EDTimeBDD bd = new EDTimeBDD(this);

        String texteSansEDT;
        checkFinDeSemaine(bd);
        bd.open();
        //bd.insertCours(new Cours("M4104","R47",2,8,30));
        //bd.insertCours(new Cours("M4103","206",2,10,30));
        final String[] user = bd.getUser();
        if(bd.getCours()== null){
             texteSansEDT = ", vous n'avez pas renseigné d'emploi du temps";
        }else{
            texteSansEDT = ", vous avez actuellement renseigné un emploi du temps";
            boutonEDT.setText("Modifier");
        }
        bd.close();

        texte.setText("Bonjour "+user[0]+texteSansEDT);

        texteNumTemps.setText(user[1]);

        boutonEDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AccueilConnecte.this, Edt.class);
                startActivity(intent);

            }
        });

        boutonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                texteNumTemps.setVisibility(View.INVISIBLE);
                boutonModifier.setVisibility(View.INVISIBLE);

                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) texteMinutes.getLayoutParams();
                params.setMarginStart((int) convertDpToPixel(50));
                texteMinutes.setLayoutParams(params);

                bd.open();
                editTextTemps.setText(bd.getUser()[1]);
                bd.close();
                editTextTemps.setVisibility(View.VISIBLE);
                boutonValider.setVisibility(View.VISIBLE);
            }
        });

        boutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isEmpty(editTextTemps)) {
                    int newTemps = Integer.valueOf(editTextTemps.getText().toString());
                    if (newTemps >= 0 && newTemps < 60) {

                        editTextTemps.setVisibility(View.INVISIBLE);
                        boutonValider.setVisibility(View.INVISIBLE);

                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) texteMinutes.getLayoutParams();
                        params.setMarginStart((int) convertDpToPixel(10));
                        texteMinutes.setLayoutParams(params);

                        bd.open();
                        bd.updateUser(newTemps, user[0]);
                        texteNumTemps.setText(bd.getUser()[1]);
                        bd.close();

                        texteNumTemps.setVisibility(View.VISIBLE);
                        boutonModifier.setVisibility(View.VISIBLE);
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Le temps doit être entre 0 et 59", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Le champs ne doit pas être vide", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }




    public float convertDpToPixel(float dp){
        Resources r = getResources();
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
    }

    private void checkFinDeSemaine(EDTimeBDD bd) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int heure = calendar.get(Calendar.MINUTE);
        if(heure == 14){
       // if(day==Calendar.SUNDAY||day==Calendar.SATURDAY){
            bd.open();
            bd.removeCours();
            bd.close();
        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

}

