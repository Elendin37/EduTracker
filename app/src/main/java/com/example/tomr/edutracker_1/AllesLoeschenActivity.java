package com.example.tomr.edutracker_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class AllesLoeschenActivity extends AppCompatActivity {

    private static final int REQUESTCODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alles_loeschen);
    }

    public void onClick_b_AllesLöschen(View view)
    {
        Toast.makeText(getApplicationContext(), "Alles gelöscht", Toast.LENGTH_SHORT).show();
        Intent zumHautpmenü = new Intent(this, MainActivity.class);
        startActivityForResult(zumHautpmenü, REQUESTCODE);
    }

    public void onClick_b_al_abbrechen(View view)
    {
        Toast.makeText(getApplicationContext(), "Abgebrochen", Toast.LENGTH_SHORT).show();
        Intent zumHautpmenü = new Intent(this, MainActivity.class);
        startActivityForResult(zumHautpmenü, REQUESTCODE);
    }
}
