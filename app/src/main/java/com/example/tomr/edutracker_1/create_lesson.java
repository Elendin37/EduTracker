package com.example.tomr.edutracker_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
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
import android.widget.Toast;
import android.widget.Switch;
import android.widget.CompoundButton;

import java.util.ArrayList;

public class create_lesson extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //declaring variables of the class:
    public String title;
    public float zielzeit;
    public float istzeit;
    private Toast errToast;
    private Toast doneToast;
    private Toast timeToast;
    private Toast ectsToast;
    public Fach fach;
    private EditText text1; // bufer variable
    private EditText text2; // buffer variable
    private EditText text3; // buffer variable
    private Switch simpleSwitch;
    private Context context;
    private String errormessage = "Eingabe fehlerhaft.";
    private String donemessage = "Eingabe erfolgreich.";
    private String timemessage = "Zielzeit ist kleiner als die Istzeit";
    private String ectsmessage = "Bitte geben Sie die Lernzeit als ECTS ein";
    private int duration = Toast.LENGTH_SHORT;

    //insert this for toolbar:
    public Toolbar toolbar;
    private ArrayList<String> values;
    private ArrayAdapter<String> adapter;

    MyDatabaseHelper db =new MyDatabaseHelper(this,null,null,0);

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_create_lesson); // change this for navigation drawer

        context = getApplicationContext();
        // initialize the Toast for the Errormessages:
        errToast = Toast.makeText(context, errormessage, duration);
        doneToast = Toast.makeText(context, donemessage, duration);
        timeToast = Toast.makeText(context, timemessage, duration);
        ectsToast = Toast.makeText(context, ectsmessage, duration);

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

        //initialize the switch to change between normal hours or ECTS
        simpleSwitch = (Switch)findViewById(R.id.switch1);
        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // Double someNumber = Double.parseDouble(editTextInput.getText().toString());
                if (simpleSwitch.isChecked()){
                    ectsToast.show(); // show toast that ects is selected
                    // EditText mEditText= (EditText)findViewById(R.id.editTextECTS);
                    Intent ects= new Intent(getApplicationContext(), create_lesson_ects.class);
                    startActivity(ects); // Start new activity on button click
                } else if (!simpleSwitch.isChecked()) {

                }
                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });

    }

    // Funktion um die Onclicks auzulesen und die Eingaben zu übernehmen:
    public void onClickNew(View view) {

        text1=(EditText)findViewById(R.id.editTextects);    // readout Zielzeit
        text2=(EditText)findViewById(R.id.editTextsws);     // readout Istzeit
        text3=(EditText)findViewById(R.id.editTextTitel);       // readout Title

        // check if every field is filled
        if ((isEmpty(text1)==false) && (isEmpty(text2)==false) && (isEmpty(text3)==false)  ) {
            zielzeit    = Float.valueOf(text1.getText().toString());    // write zielzeit in variable
            istzeit     = Float.valueOf(text2.getText().toString());    // write istzeit in variable
            title       = text3.getText().toString();                   // write title i n variable

            //check if the times are correct
            if (isBigger(zielzeit,istzeit)==false){
                fach        = new Fach(title, istzeit, zielzeit);           // create class instance Fach with declared variables
                db.addFach(fach);   // add Fach to database
                doneToast.show();// Show toast everything worked
                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent); // Start new activity on button click
            }
            // if the times arent correct show toast and do nothing.
            else {
                timeToast.show(); // show toast wrong time selected.
                return;
            }
        }
        // if a field is empty call do nothing and return:
        else {
            errToast.show(); // show errortoast
            return;
        }
    }

    // function to test if the EditText field is empty
    // gives back true if the field is empty
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        }
        else {
            return true;
        }
    }

    // This function checks if the Zielzeit is bigger then the Istzeit so the input is correct:
    private boolean isBigger(float timeZiel, float timeIst) {
        if (timeZiel > timeIst){
            return false;
        }
        else {
            return true;
        }
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

