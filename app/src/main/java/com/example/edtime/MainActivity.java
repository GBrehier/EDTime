package com.example.edtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EDTimeBDD bd = new EDTimeBDD(this);

        final Button bouton = findViewById(R.id.boutonDebut);


        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bd.open();

                //bd.insertUser("Guillaume");
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
}
