package com.example.tomr.edutracker_1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Statistik_Auswahl extends AppCompatActivity {

    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);

    ListView listview;
    ListAdapter adapter;
    ArrayList<String> selectedItems=new ArrayList<>();

    
    TextView tvStart, tvEnd;
    Button BtStart, BtEnd;

    DatePickerDialog dpd;

    String startdatum, enddatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_auswahl);

//Auswahl der Fächer

        listview = (ListView) findViewById(R.id.lv_faecher);

        List<Fach> faecher = new LinkedList<Fach>();
        faecher=db.getAllFaecher();

        Fach fach = null;
        fach=new Fach();
        fach.setTitle("Alle Fächer");

        faecher.add(0,fach);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, faecher);
                listview.setAdapter(adapter);
        db.close();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ausgewählteFächer = ((TextView)view).getText().toString();
                if(selectedItems.contains(ausgewählteFächer)){
                    selectedItems.remove(ausgewählteFächer);
                }
                else
                {
                    selectedItems.add(ausgewählteFächer);
                }
            }
        });



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

                        startdatum = String.format("%02d", day)+"."+String.format("%02d", (month+1))+"."+year;
                        tvStart.setText(startdatum);
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
                        enddatum = String.format("%02d", day)+"."+String.format("%02d", (month+1))+"."+year;
                        tvEnd.setText(enddatum);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
    }

    public void onClick_b_StatistikAnzeigen(View view)
    {
//        Toast.makeText(this, "Button -Statistik anzeigen- gedrückt", Toast.LENGTH_SHORT).show();

        Intent StatistikAnzeigen = new Intent(this, Statistik_Anzeige.class);
        StatistikAnzeigen.putExtra("ÜbergabeStartdatum", startdatum);
        StatistikAnzeigen.putExtra("ÜbergabeEnddatum", enddatum);
        StatistikAnzeigen.putExtra("ÜbergabeFächer", selectedItems);
        startActivity(StatistikAnzeigen);
    }


}


