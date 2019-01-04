package com.example.tomr.edutracker_1;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Timer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final long SLEEPTIME = 10;
    boolean running;
    Thread refreshThread;
    long time;
    Button start, stopp;
    TextView timeview, fach;
    String fachname;
    Calendar currentStartTime, currentStopTime;
    AudioManager am;
    int oldRingerMode;
    //insert this for toolbar:
    public android.support.v7.widget.Toolbar toolbar;
    private ArrayList<String> values;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_timer);
        running = false;
        time = 0;
        start =  findViewById(R.id.b_starttimer);
        stopp =  findViewById(R.id.b_stopptimer);
        timeview =  findViewById(R.id.timeview);
        fach =  findViewById(R.id.fach);

        currentStartTime = Calendar.getInstance();//.getTime();


        //----------------Toolbar + Application Drawer begin --------------------------------------//
        // initialize Toolbar: (needed for the app.Drawer)
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
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
        /*
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Stoppuhr");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        */

        //timeview.setText(getString(R.string.time_string, String.format("%.2f", time)));
        timeview.setText(getString(R.string.time_string, formatTime(time)));

        //Übergebene Werte des Intens aus der MainActivity
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        fachname = (String) bd.get("fach");
        fach.setText(fachname);


        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        oldRingerMode = am.getRingerMode();                         //vorherigen RingMode auslesen

    }


    public void onClickTimer (View v) {
        switch (v.getId()) {
            case R.id.b_starttimer:


                /* für Modus nicht stören -> Vibration ausreichend
                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                if (!notificationManager.isNotificationPolicyAccessGranted()) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                }*/

                if (oldRingerMode != 0) {                                       //Wenn bereits silent, dann nicht umschalten
                    am.setRingerMode(1);                                        //Auf Vibration schalten
                }

                if (!running) {
                    running = true;
                    initThread();
                    start.setText(R.string.pause);

                } else {
                    running = false;
                    start.setText(R.string.start);
                }
                break;

            case R.id.b_stopptimer:
                running = false;
                AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(Timer.this);
                //dlgBuilder.setMessage("Sind Sie sicher, dass Sie ihre Lernzeit beenden wollen?\n\nAktuelle Lernzeit: "+ String.format("%.2f", time));
                dlgBuilder.setMessage("Sind Sie sicher, dass Sie ihre Lernzeit beenden wollen?\n\nAktuelle Lernzeit: "+ formatTime(time));
                dlgBuilder.setCancelable(false);
                dlgBuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        currentStopTime = Calendar.getInstance();
                        Toast.makeText(Timer.this, "Lernzeit beendet", Toast.LENGTH_SHORT).show();

                        am.setRingerMode(oldRingerMode);    //Auf vorherige Einstellung schalten

                        Intent i = new Intent(getApplicationContext(), Speichern.class);
                        i.putExtra("fach",fachname);
                        i.putExtra("durationInMs",time);
                        i.putExtra("duration",timeview.getText());
                        i.putExtra("starttime",currentStartTime);
                        i.putExtra("stopptime",currentStopTime);
                        startActivity(i);



                        //running = true;
                        //initThread();
                    }
                });
                dlgBuilder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Timer.this, "Alles klar, die Lernzeit läuft weiter", Toast.LENGTH_SHORT).show();
                        running = true;
                        initThread();
                    }
                });
                AlertDialog alert = dlgBuilder.create();
                alert.show();
                break;

            case R.id.toolbar:
                finish();
                //startActivity(new Intent(getApplicationContext(), Funktionsauswahl.class));
                //openActivityFromLeft();
                break;
        }
    }


    private String formatTime(long millis) {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return  hms;
    }


    public void initThread() {
        refreshThread = new Thread(new Runnable() {
            public void run() {
                while (running) {
                    //time = time + 0.01;
                    time = time + 10;
                    try {
                        Thread.sleep(SLEEPTIME);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    runOnUiThread(new Runnable() {
                        public void run() {
                            //timeview.setText(getString(R.string.time_string, String.format("%.2f", time)));
                            timeview.setText(getString(R.string.time_string, formatTime(time)));
                        }
                    });
                }
            }
        });
        refreshThread.start();
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
