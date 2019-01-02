package com.example.tomr.edutracker_1;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Statistik_Anzeige extends AppCompatActivity {

    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);

    TextView tvTest;
    ArrayList<String> FächerList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_anzeige);

        tvTest = (TextView)findViewById(R.id.tv_test);


        FächerList = getIntent().getStringArrayListExtra("ÜbergabeFächer");
        //String Fächer = getIntent().getExtras().getString("ÜbergabeFächer");
        String Startdatum = getIntent().getExtras().getString("ÜbergabeStartdatum");
        String Enddatum = getIntent().getExtras().getString("ÜbergabeEnddatum");


        Integer anzahlfächer = FächerList.size();
        String fachdaten ="";
        String ausgabe = "";


        for (int i = 0; i<anzahlfächer; i++ ){

            //ausgabe = ausgabe + "\n" +FächerList.get(i);

            ausgabe =ausgabe + "\n" + FächerList.get(i)+ ": "+ db.getFachdata(FächerList.get(i));
        }


        tvTest.setText(anzahlfächer.toString()+"\n"+ausgabe+"\n"+Startdatum+"\n"+Enddatum+"\n");



    }




}
