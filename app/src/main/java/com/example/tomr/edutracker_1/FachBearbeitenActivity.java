package com.example.tomr.edutracker_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FachBearbeitenActivity extends Activity {

    private static final int REQUESTCODE = 3;

    MySQLiteHelper dbF =new MySQLiteHelper(this,null,null,0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fach_bearbeiten);

//        String[] fächer = {"Elektronik 1", "Elektronik 2", "Master-Projekt", "ADSE", "MNS", "TIM", "MICO"};


        ListAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dbF.getAllFaecher());
        ListView listView = (ListView) findViewById(R.id.listfaecher);
        listView.setAdapter(adapter);
        dbF.close();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fach = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(FachBearbeitenActivity.this, fach, Toast.LENGTH_LONG).show();

                Intent FachBearbeiten2 = new Intent(view.getContext(), FachBearbeiten2Activity.class);
                FachBearbeiten2.putExtra("Übergabe-de.cl.edutracker_einstellungen.Fach", fach);

                startActivityForResult(FachBearbeiten2, REQUESTCODE);

            }
        });


    }
}
