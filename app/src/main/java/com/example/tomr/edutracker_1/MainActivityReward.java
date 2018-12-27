package com.example.tomr.edutracker_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivityReward extends AppCompatActivity {


    private EditText GesamtZeitInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reward);
        GesamtZeitInput = findViewById(R.id.LernzeitInput);
    }

    public void onClickTin(View view){
        Intent i = new Intent(this,ShowActivityRang.class);
        if (GesamtZeitInput.getText().toString().isEmpty()) {
            Toast.makeText( this,"Invalid Input", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            int InputTime=Integer.parseInt(GesamtZeitInput.getText().toString());
            i.putExtra("LernzeitInput", InputTime);
            i.putExtra("Grundstein", 0);
            i.putExtra("Nachteule", 0);
            i.putExtra("FrueherVogel", 0);
            i.putExtra("WeLerner", 0);
            i.putExtra("MarathonLerner", 0);
            i.putExtra("PerfekteWoche", 0);
            startActivity(i);
        }


    }

    public void onClickC1(View view){
        Intent i = new Intent(this,ShowActivityRang.class);
        i.putExtra("LernzeitInput", 5);
        i.putExtra("Grundstein", 1);
        i.putExtra("Nachteule", 1);
        i.putExtra("FrueherVogel", 0);
        i.putExtra("WeLerner", 0);
        i.putExtra("MarathonLerner", 0);
        i.putExtra("PerfekteWoche", 1);
        startActivity(i);
    }
    public void onClickC2(View view){
        Intent i = new Intent(this,ShowActivityRang.class);
        i.putExtra("LernzeitInput", 15);
        i.putExtra("Grundstein", 1);
        i.putExtra("Nachteule", 0);
        i.putExtra("FrueherVogel", 0);
        i.putExtra("WeLerner", 1);
        i.putExtra("MarathonLerner", 0);
        i.putExtra("PerfekteWoche", 1);
        startActivity(i);
    }
    public void onClickC3(View view){
        Intent i = new Intent(this,ShowActivityRang.class);
        i.putExtra("LernzeitInput", 25);
        i.putExtra("Grundstein", 1);
        i.putExtra("Nachteule", 0);
        i.putExtra("FrueherVogel", 1);
        i.putExtra("WeLerner", 0);
        i.putExtra("MarathonLerner", 0);
        i.putExtra("PerfekteWoche", 1);
        startActivity(i);
    }
    public void onClickC4(View view){
        Intent i = new Intent(this,ShowActivityRang.class);
        i.putExtra("LernzeitInput", 35);
        i.putExtra("Grundstein", 1);
        i.putExtra("Nachteule", 0);
        i.putExtra("FrueherVogel", 0);
        i.putExtra("WeLerner", 0);
        i.putExtra("MarathonLerner", 0);
        i.putExtra("PerfekteWoche", 1);
        startActivity(i);
    }
}
