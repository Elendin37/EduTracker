package com.example.tomr.edutracker_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Statistik_Anzeige extends AppCompatActivity {

    TextView tvTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_anzeige);

        tvTest = (TextView)findViewById(R.id.tv_test);

        String Fächer = getIntent().getExtras().getString("ÜbergabeFächer");
        String Startdatum = getIntent().getExtras().getString("ÜbergabeStartdatum");
        String Enddatum = getIntent().getExtras().getString("ÜbergabeEnddatum");

        tvTest.setText(Fächer+Startdatum+"\n"+Enddatum);
    }
}
