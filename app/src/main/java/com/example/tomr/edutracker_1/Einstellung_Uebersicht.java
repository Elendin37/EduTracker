package com.example.tomr.edutracker_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Einstellung_Uebersicht extends AppCompatActivity {


    //MySQLiteHelper dbF =new MySQLiteHelper(this,null,null,0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellung__uebersicht);
    }
    public void onClick_b_FachBearbeiten(View view)
    {
        Toast.makeText(this, "Button -FACH BEARBEITEN- gedrückt", Toast.LENGTH_SHORT).show();

        Intent FachBearbeiten = new Intent(this, FachBearbeitenActivity.class);

        startActivity(FachBearbeiten);
    }


    public void onClick_b_FachLoeschen(View view)
    {
        Toast.makeText(this, "Button -FACH LÖSCHEN- gedrückt", Toast.LENGTH_SHORT).show();

        Intent FachLoeschen = new Intent(this, FachLoeschenActivity.class);

        startActivity(FachLoeschen);
    }


    public void onClick_b_LernzeitBearbeiten(View view)
    {
        Toast.makeText(this, "Button -LERNZEIT BEARBEITEN- gedrückt", Toast.LENGTH_SHORT).show();

        Intent LZBearbeiten = new Intent(this, LZBearbeitenActivity.class);

        startActivity(LZBearbeiten);
    }


    public void onClick_b_LernzeitLoeschen(View view)
    {
        Toast.makeText(this, "Button -LERNZEIT LÖSCHEN- gedrückt", Toast.LENGTH_SHORT).show();

        Intent LZLoeschen = new Intent(this, LZLoeschenActivity.class);

        startActivity(LZLoeschen);
    }


    public void onClick_b_AllesLoeschen(View view)
    {
        Toast.makeText(this, "Button -ALLES LÖSCHEN- gedrückt", Toast.LENGTH_SHORT).show();

        Intent AllesLoeschen = new Intent(this, AllesLoeschenActivity.class);

        startActivity(AllesLoeschen);
    }


}
