package com.example.tomr.edutracker_1;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Timer extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        running = false;
        time = 0;
        start =  findViewById(R.id.b_starttimer);
        stopp =  findViewById(R.id.b_stopptimer);
        timeview =  findViewById(R.id.timeview);
        fach =  findViewById(R.id.fach);

        currentStartTime = Calendar.getInstance();//.getTime();

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

}
