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

    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lz_bearbeiten);


        ListAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, db.getAllLerneinheiten());
        ListView listView = (ListView) findViewById(R.id.listlz);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String lz = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(LZBearbeitenActivity.this, lz, Toast.LENGTH_LONG).show();

                Intent LZBearbeiten2 = new Intent(view.getContext(), LZBearbeiten2Activity.class);
                LZBearbeiten2.putExtra("LZ-Übergabe", lz);

                startActivity(LZBearbeiten2);

            }
        });
    }
}
