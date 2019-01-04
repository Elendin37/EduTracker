package com.example.tomr.edutracker_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FachBearbeiten2Activity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{



    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);

    private TextView fach;
    private EditText fachnamealt;
    private EditText zielzeit;

    //insert this for toolbar:
    public Toolbar toolbar;
    private ArrayList<String> values;
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fach_bearbeiten2);
        fach = (TextView)findViewById(R.id.Text_Fach);
        fachnamealt = (EditText) findViewById((R.id.EditTextFachname));
        zielzeit = (EditText) findViewById((R.id.editTextZielzeit));

        String Fachname = getIntent().getExtras().getString("Übergabe-de.cl.edutracker_einstellungen.Fach");


        fach.setText(Fachname+" bearbeiten:");
        fachnamealt.setText(Fachname);

        Float fzielzeit = db.getFachname(Fachname).getZielzeit();
        zielzeit.setText(fzielzeit.toString());
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
    }

    public void onClick_b_Speichern(View view){

        //fachnamealt = (EditText) findViewById((R.id.EditTextFachname));
        //zielzeit = (EditText) findViewById((R.id.editTextZielzeit));

        String Fachname = getIntent().getExtras().getString("Übergabe-de.cl.edutracker_einstellungen.Fach");

        int fachID = db.getFachname(Fachname).getId();

        String newName = fachnamealt.getText().toString();

        float newZielzeit = Float.valueOf(zielzeit.getText().toString());

        float Istzeit = db.getFachname(Fachname).getIstzeit();


        db.updateFach(fachID, newName ,newZielzeit, Istzeit);


        Toast.makeText(this, newName+" erfolgreich geändert", Toast.LENGTH_SHORT).show();

        Intent Test = new Intent(this, MainActivity.class);

        startActivity(Test);

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
