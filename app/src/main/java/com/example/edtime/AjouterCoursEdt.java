package com.example.edtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AjouterCoursEdt extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_cours_edt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EDTimeBDD bd = new EDTimeBDD(this);

        final Spinner spinnerJour = findViewById(R.id.spinnerJour);
        initSpinnerJour(spinnerJour);

        final EditText editTextHeure = findViewById(R.id.editTextHeure);

        final EditText editTextMinute = findViewById(R.id.editTextMinute);

        final EditText editTextMatiere = findViewById(R.id.editTextMatiere);

        final EditText editTextSalle = findViewById(R.id.editTextSalle);

        final Button buttonAjouter = findViewById(R.id.buttonAjouter);

        final CheckBox checkBoxReveil =  findViewById(R.id.checkBoxReveil);




        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int heures =Integer.valueOf(editTextHeure.getText().toString());
                int minutes = Integer.valueOf(editTextMinute.getText().toString());

                if(!isEmpty(editTextHeure)&&!isEmpty(editTextMinute)&&!isEmpty(editTextMatiere)&&!isEmpty(editTextSalle)&&heures<19&&heures>7&&minutes<60&&minutes>-1){

                    Cours cours = new Cours();
                    cours.setJour(cours.transformJourEnInt(spinnerJour.getSelectedItem().toString()));
                    cours.setHeures(heures);
                    cours.setMinutes(minutes);

                    cours.setNomCours(editTextMatiere.getText().toString());
                    cours.setSalle(editTextSalle.getText().toString());

                    bd.open();
                    bd.insertCours(cours);
                    bd.close();



                    Intent intent2 = new Intent(AjouterCoursEdt.this, Edt.class);
                    startActivity(intent2);
                    if(checkBoxReveil.isChecked()){
                        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);

                        calculHeureReveil(cours, bd);


                        intent.putExtra(AlarmClock.EXTRA_HOUR, cours.getHeures());
                        intent.putExtra(AlarmClock.EXTRA_MINUTES,cours.getMinutes());
                        intent.putExtra(AlarmClock.EXTRA_VIBRATE,true);
                        intent.putExtra(AlarmClock.EXTRA_MESSAGE, spinnerJour.getSelectedItem().toString());
                        ArrayList<Integer> alarmDays= new ArrayList<Integer>();

                        switch(cours.getJour()){
                            case 2: alarmDays.add(Calendar.MONDAY);
                            break;

                            case 3: alarmDays.add(Calendar.TUESDAY);
                            break;

                            case 4: alarmDays.add(Calendar.WEDNESDAY);
                            break;

                            case 5: alarmDays.add(Calendar.THURSDAY);
                            break;

                            case 6: alarmDays.add(Calendar.FRIDAY);
                            break;
                        }

                        intent.putExtra(AlarmClock.EXTRA_DAYS, alarmDays);
                        startActivity(intent);
                    }
                }

                    if(isEmpty(editTextHeure)||isEmpty(editTextMinute)||isEmpty(editTextMatiere)||isEmpty(editTextSalle)){
                        Toast toast = Toast.makeText(getApplicationContext(), "Un ou plusieurs champs sont vides", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    if(!isEmpty(editTextHeure)&&(heures>18||heures<8)){
                        Toast toast = Toast.makeText(getApplicationContext(), "Heure incorrecte, tu n'as pas cours avant 8h ou après 19h si ?", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                    if(!isEmpty(editTextMinute)&&!(minutes<60&&minutes>-1)){
                        Log.d("test",String.valueOf(minutes));
                        Toast toast = Toast.makeText(getApplicationContext(), "Les minutes sont comprises entre 0 et 59", Toast.LENGTH_SHORT);
                        toast.show();
                    }


            }
        });




    }



    private void calculHeureReveil(Cours cours, EDTimeBDD bd) {
        bd.open();
        String[] user = bd.getUser();
        bd.close();

        if(Integer.valueOf(user[1])<=cours.getMinutes()){
            cours.setMinutes(cours.getMinutes()-Integer.valueOf(user[1]));
        }else{
            cours.setHeures(cours.getHeures()-1);
            cours.setMinutes(60 +cours.getMinutes()-Integer.valueOf(user[1]));
        }
    }

    private void initSpinnerJour(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.jours,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }


}
