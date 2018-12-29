package com.example.tomr.edutracker_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LZBearbeiten2Activity extends AppCompatActivity {

    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);


    private TextView Lernzeit;
    private EditText NotizAlt;
    private EditText AnhangAlt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lz_bearbeiten2);

        Lernzeit =(TextView) findViewById(R.id.Text_LZ);
        String LZname = getIntent().getExtras().getString("LZ-Übergabe");
        Lernzeit.setText(LZname+" bearbeiten");

        int LZid = Integer.parseInt(LZname.substring(0,4));

        NotizAlt =(EditText) findViewById(R.id.editTextNotiz);
        NotizAlt.setText(db.getLerneinheit(LZid).getNotizen());

        AnhangAlt =(EditText) findViewById(R.id.editTextAnhang);
        AnhangAlt.setText(db.getLerneinheit(LZid).getAnhang());
    }

    public void onClick_b_Speichern(View view){

        String newNotiz = NotizAlt.getText().toString();

        String newAnhang = AnhangAlt.getText().toString();

        String LZname = getIntent().getExtras().getString("LZ-Übergabe");
        int LZid = Integer.parseInt(LZname.substring(0,4));
        db.updateFachEinstellungen(LZid, newNotiz, newAnhang);


        Toast.makeText(this, LZname+" erfolgreich geändert", Toast.LENGTH_SHORT).show();

        Intent Test = new Intent(this, MainActivity.class);

        startActivity(Test);

    }

}
