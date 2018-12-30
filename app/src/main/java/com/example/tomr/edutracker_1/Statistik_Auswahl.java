package com.example.tomr.edutracker_1;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Statistik_Auswahl extends AppCompatActivity {

    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);

    ListView listview;
    ListAdapter adapter;

    TextView tvStart, tvEnd;
    Button BtStart, BtEnd;

    DatePickerDialog dpd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_auswahl);

//Auswahl der Fächer
        listview = (ListView) findViewById(R.id.lv_faecher);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, db.getAllFaecher());
        listview.setAdapter(adapter);
        db.close();


//Startdatum über Kalenderfenster auswählen
        tvStart = (TextView)findViewById(R.id.tv_startdatum);
        BtStart = (Button)findViewById(R.id.b_startdatum);

        BtStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                final Calendar c = Calendar.getInstance();
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                int mMonth = c.get(Calendar.MONTH);
                int mYear =c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(Statistik_Auswahl.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        tvStart.setText(String.format("%02d", day)+"."+String.format("%02d", (month+1))+"."+year);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
//TODO: -Christoph- Auswahl begranzen --> Enddatum >= Startdatum
 //Enddatum über Kalenderfenster auswählen
        tvEnd =(TextView)findViewById(R.id.tv_enddatum);
        BtEnd = (Button)findViewById(R.id.b_Enddatum);

        BtEnd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                final Calendar c = Calendar.getInstance();
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                int mMonth = c.get(Calendar.MONTH);
                int mYear =c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(Statistik_Auswahl.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        tvEnd.setText(String.format("%02d", day)+"."+String.format("%02d", (month+1))+"."+year);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
    }



}


