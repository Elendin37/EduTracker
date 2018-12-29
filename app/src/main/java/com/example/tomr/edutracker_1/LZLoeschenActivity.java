package com.example.tomr.edutracker_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LZLoeschenActivity extends AppCompatActivity {

    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);

    ListView lv;
    Button deleteBtn;

    ArrayAdapter adapter;
    ArrayList data = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lzloeschen);

        showFaecher();
    }

    public void showFaecher(){


        lv = (ListView) findViewById(R.id.listview);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, db.getAllLerneinheiten());
        lv.setAdapter(adapter);
        db.close();
    }
    public void onClick_b_LZLöschen(View view)
    {

        int pos = lv.getCheckedItemPosition();

        if (pos>=0) {
            String Lerneinheit = lv.getItemAtPosition(pos).toString();

            int LEid = Integer.parseInt(Lerneinheit.substring(0,4));
            adapter.notifyDataSetChanged();


            db.deleteLerneinheit(db.getLerneinheit(LEid));
            Toast.makeText(getApplicationContext(), Lerneinheit + " erfolgreich gelöscht!", Toast.LENGTH_SHORT).show();
            showFaecher();
        }
        else{
            Toast.makeText(getApplicationContext(), "Bitte Fach auswählen", Toast.LENGTH_SHORT).show();
        }

    }
}