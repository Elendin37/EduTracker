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

public class LerneinheitAnzeigen extends AppCompatActivity {

    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lerneinheit_anzeigen);

        ListAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, db.getAllLerneinheiten());
        ListView listView = (ListView) findViewById(R.id.listlz);
        listView.setAdapter(adapter);

    }
}
