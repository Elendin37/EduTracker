package com.example.tomr.edutracker_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FachBearbeiten2Activity extends AppCompatActivity {



    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);

    private TextView fach;
    private EditText fachnamealt;
    private EditText zielzeit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fach_bearbeiten2);
        fach = (TextView)findViewById(R.id.Text_Fach);
        fachnamealt = (EditText) findViewById((R.id.EditTextFachname));
        zielzeit = (EditText) findViewById((R.id.editTextZielzeit));

        String Fachname = getIntent().getExtras().getString("Übergabe-de.cl.edutracker_einstellungen.Fach");


        fach.setText(Fachname+" bearbeiten:");
        fachnamealt.setText(Fachname);

        Float fzielzeit = db.getFachname(Fachname).getZielzeit();
        zielzeit.setText(fzielzeit.toString());

    }

    public void onClick_b_Speichern(View view){

        //fachnamealt = (EditText) findViewById((R.id.EditTextFachname));
        //zielzeit = (EditText) findViewById((R.id.editTextZielzeit));

        String Fachname = getIntent().getExtras().getString("Übergabe-de.cl.edutracker_einstellungen.Fach");

        int fachID = db.getFachname(Fachname).getId();

        String newName = fachnamealt.getText().toString();

        float newZielzeit = Float.valueOf(zielzeit.getText().toString());

        float Istzeit = db.getFachname(Fachname).getIstzeit();


        db.updateFach(fachID, newName ,newZielzeit, Istzeit);


        Toast.makeText(this, newName+" erfolgreich geändert", Toast.LENGTH_SHORT).show();

        Intent Test = new Intent(this, MainActivity.class);

        startActivity(Test);

    }


}
