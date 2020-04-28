package com.example.edtime;

import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EDTimeBDD bd = new EDTimeBDD(this);

        final Button bouton = findViewById(R.id.boutonDebut);

        //updateHello(this); essai de mise à jour du widget

        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bd.open();

                if(bd.getUser() == null){
                    bd.close();
                    Intent intent = new Intent(MainActivity.this, AccueilNonConnecte.class);
                    startActivity(intent);
                }else{
                    bd.close();
                    Intent intent = new Intent(MainActivity.this, AccueilConnecte.class);
                    startActivity(intent);
                }



            }
        });
    }
    protected void updateHello(Context context) {
        String cours = getCoursDuJour();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, Salle_heure_widget.class);
        int[] appWidgetId = appWidgetManager.getAppWidgetIds(thisWidget);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.salle_heure_widget);
        views.setTextViewText(R.id.textViewCoursDuJour, cours);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId[0], views);
    }

    public String getCoursDuJour(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        EDTimeBDD bd = new EDTimeBDD(this);
        bd.open();
        Cours[] listeCours = bd.getCoursDuJour(day);
        bd.close();

        String texteCours ="";
        if(listeCours==null) {
            texteCours = "Aucun cours enregistré";
        }else {
            for (int i = 0; i < listeCours.length; i++) {
                texteCours += listeCours[i].toString() + "\n";
            }
        }
        return texteCours;
    }
}
