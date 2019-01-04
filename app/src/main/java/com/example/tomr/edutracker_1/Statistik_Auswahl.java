package com.example.tomr.edutracker_1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
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

public class Statistik_Auswahl extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //insert this for toolbar:
    public Toolbar toolbar;
    private ArrayList<String> values;
    private ArrayAdapter<String> adapter;

    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);

    ListView listview;
    ListAdapter adapter2;
    ArrayList<String> selectedItems=new ArrayList<>();

    
    TextView tvStart, tvEnd;
    Button BtStart, BtEnd;

    DatePickerDialog dpd;

    String startdatum, enddatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_statistik_auswahl);


        //----------------Toolbar + Application Drawer begin --------------------------------------//
        // initialize Toolbar: (needed for the app.Drawer)
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //initialize the Drawer:
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Werte für Adapter anlegen
        values = new ArrayList<String>();

        //Adapter anlegen
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        //----------------Toolbar + Application Drawer end-----------------------------------------//
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

    //----------------Toolbar + Application Drawer-------------------------------------------------//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_tracking) {
            // Handle the camera action
        } else if (id == R.id.nav_lerneinheiten) {

        } else if (id == R.id.nav_statistik) {

        } else if (id == R.id.nav_errungenschaften) {
            Intent goals = new Intent(getApplicationContext(),MainActivityReward.class);
            startActivity(goals);

        } else if (id == R.id.nav_manage) {
            Intent manage = new Intent(getApplicationContext(),Einstellung_Uebersicht.class);
            startActivity(manage);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //----------------Toolbar + Application Drawer--------------------------------------------//

}


