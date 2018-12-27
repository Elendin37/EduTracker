package com.example.tomr.edutracker_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class LZBearbeitenActivity extends AppCompatActivity {

    MyDatabaseHelper dbL = new MyDatabaseHelper(this, null, null, 0);

    private static final int REQUESTCODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lz_bearbeiten);

        //String[] Lernzeiten = {"Mo 05.11.2018", "Di 06.11.2018", "Mi 07.11.2018", "Do 08.11.2018", "Fr 09.11.2018", "Sa 10.11.2018", "So 11.11.2018"};


        ListAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dbL.getAllLerneinheiten());
        ListView listView = (ListView) findViewById(R.id.listlz);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String lz = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(LZBearbeitenActivity.this, lz, Toast.LENGTH_LONG).show();

                Intent LZBearbeiten2 = new Intent(view.getContext(), LZBearbeiten2Activity.class);
                LZBearbeiten2.putExtra("LZ-Übergabe", lz);

                startActivityForResult(LZBearbeiten2, REQUESTCODE);

            }
        });


    }
}
