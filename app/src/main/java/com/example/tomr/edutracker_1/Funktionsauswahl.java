package com.example.tomr.edutracker_1;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;


public class Funktionsauswahl extends AppCompatActivity implements View.OnClickListener {
    TextView fach, lernfortschritt;
    Button stoppuhr, timer, nachtrag, statistik, back;
    String fachname;

    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funktionsauswahl);

        fach = findViewById(R.id.fach);
        lernfortschritt = findViewById(R.id.lernfortschritt);
        stoppuhr = findViewById(R.id.b_stoppuhr);
        timer = findViewById(R.id.b_timer);
        nachtrag = findViewById(R.id.b_nachtrag);
        statistik = findViewById(R.id.b_statistik);

        /*
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Funktionsauwahl");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        */

        //Übergebene Werte des Intens aus der MainActivity
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        fachname = (String) bd.get("fach");
        fach.setText(fachname);


        //Anzeige der geleisteten Lernzeit pro Fach
        try{
            Lerneinheit unit = db.getFach(fachname);
            lernfortschritt.setText(unit.getLerndauer() + " / 150h");
        }catch (CursorIndexOutOfBoundsException e){
            lernfortschritt.setText("00:00:00 / 150h");
        }
    }


    public void onClick (View v){
        switch (v.getId()){
            case R.id.b_stoppuhr:
                Intent i = new Intent(getApplicationContext(), Timer.class);
                i.putExtra("fach",fachname);
                startActivity(i);
                break;
            case R.id.b_timer:
                break;
            case R.id.b_nachtrag:
                break;
            case R.id.b_statistik:
                break;
            case R.id.toolbar:
                finish();
                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                //openActivityFromLeft();
                break;
        }
    }


}
