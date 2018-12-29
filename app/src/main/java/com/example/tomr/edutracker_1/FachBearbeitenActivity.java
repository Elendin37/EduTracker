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

    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fach_bearbeiten);


        ListAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, db.getAllFaecher());
        ListView listView = (ListView) findViewById(R.id.listfaecher);
        listView.setAdapter(adapter);
        db.close();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fach = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(FachBearbeitenActivity.this, fach, Toast.LENGTH_LONG).show();

                Intent FachBearbeiten2 = new Intent(view.getContext(), FachBearbeiten2Activity.class);
                FachBearbeiten2.putExtra("Übergabe-de.cl.edutracker_einstellungen.Fach", fach);

                startActivity(FachBearbeiten2);

            }
        });


    }
}
