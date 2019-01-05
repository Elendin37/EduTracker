package com.example.tomr.edutracker_1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Statistik_Anzeige extends AppCompatActivity {

    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);

    TextView tvTest;
    TextView tvdatum;
    ArrayList<String> FächerList=new ArrayList<>();

    boolean alleFächer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_anzeige);

        tvTest = (TextView)findViewById(R.id.tv_test);
        tvdatum = (TextView)findViewById(R.id.tv_datum);
        String ausgabe = "";

        FächerList = getIntent().getStringArrayListExtra("ÜbergabeFächer");
        String Startdatum = getIntent().getExtras().getString("ÜbergabeStartdatum");
        String Enddatum = getIntent().getExtras().getString("ÜbergabeEnddatum");

        tvdatum.setText(Startdatum + " - " + Enddatum);

        Integer anzahlfächer = FächerList.size();

        for (int i = 0; i<anzahlfächer; i++ ) {
            if(FächerList.get(i).equals("Alle Fächer")){
                alleFächer = true;
            }
        }

        if(alleFächer){

            List<Fach> allefaecher = new LinkedList<Fach>();
            allefaecher=db.getAllFaecher();

            for (int i = 0; i<allefaecher.size(); i++ ){

                ausgabe =ausgabe + "\n" + allefaecher.get(i)+ ": "+ db.getLernzeitFach(allefaecher.get(i).toString(), Startdatum, Enddatum);
            }
        }else if (alleFächer == false) {


            for (int i = 0; i < anzahlfächer; i++) {

                ausgabe = ausgabe + "\n" + FächerList.get(i) + ": " + db.getLernzeitFach(FächerList.get(i), Startdatum, Enddatum);
            }
        }


        ausgabe = ausgabe + "\n\nAngabe in [hh:mm:ss]";

        tvTest.setText(ausgabe);
    }

    public void onClick_b_fertig(View view){
        Intent StatistikAuswahl = new Intent(this, Statistik_Auswahl.class);
        startActivity(StatistikAuswahl);
    }


}
