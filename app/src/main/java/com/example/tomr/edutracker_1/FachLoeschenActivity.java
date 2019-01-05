package com.example.tomr.edutracker_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class FachLoeschenActivity extends AppCompatActivity {

    ListView lv;
    Button deleteBtn;

    ArrayAdapter adapter;

    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fach_loeschen);


        deleteBtn=(Button)findViewById(R.id.deleteBtn);
//        lv = (ListView) findViewById(R.id.listview);
  //      adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, Fächer);
    //    lv.setAdapter(adapter);
    showFaecher();

    }
    public void showFaecher(){


        lv = (ListView) findViewById(R.id.listview);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, db.getAllFaecher());
        lv.setAdapter(adapter);
        db.close();
    }


    public void onClick_deleteBtn(View view)
    {

        int pos = lv.getCheckedItemPosition();

        if (pos>=0) {
            String Fach = lv.getItemAtPosition(pos).toString();
            adapter.notifyDataSetChanged();
            int fachid = db.getFachname(Fach).getId();
            db.deleteFach(db.getFach(fachid));
            Toast.makeText(getApplicationContext(), Fach + " erfolgreich gelöscht!", Toast.LENGTH_SHORT).show();
            showFaecher();
        }
        else{
            Toast.makeText(getApplicationContext(), "Bitte Fach auswählen", Toast.LENGTH_SHORT).show();
        }
    }
}