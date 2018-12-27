package com.example.tomr.edutracker_1;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Speichern extends AppCompatActivity {

    String fachname, lerndauer,notizen_string;
    long lerndauerInMs;
    Calendar startzeit, stoppzeit;
    Button addAnhang, speichern;
    TextView fach, lernzeit, filename;
    EditText notizen;

    //public MyDatabaseHelper(Context context, String name, CursorFactory factory, int version)
    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);

    /*For filereading. Try later...
    private String[] mFileList;
    private File mPath = new File(Environment.getExternalStorageDirectory() + "//yourdir//");
    private String mChosenFile;
    private static final String FTYPE = ".txt";
    private static final int DIALOG_LOAD_FILE = 1000;
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speichern);

        //---------------------------To delete old databases----------------------------------
        // DATABASE_NAME = "LerneinheitenDB";
        //getApplicationContext().deleteDatabase("LerneinheitenDB");

        fach = (TextView) findViewById(R.id.fach);
        lernzeit = (TextView) findViewById(R.id.lerndauer);
        notizen = (EditText) findViewById(R.id.notizen);
        filename = (TextView) findViewById(R.id.filename);


        speichern = (Button) findViewById(R.id.save);
        addAnhang = (Button) findViewById(R.id.anhang);


        /*
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Lernzeit speichern");
        */

        //Übergebene Werte des Intens aus der MainActivity
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        lerndauerInMs = (long) bd.get("durationInMs");
        lerndauer = (String)bd.get("duration");
        fachname = (String) bd.get("fach");
        startzeit = (Calendar) bd.get("starttime");
        stoppzeit = (Calendar) bd.get("stopptime");

        //Setzen der Anzeige
        fach.setText(fachname);
        lernzeit.setText(lerndauer);
    }

    //Zurückgehen nicht erlaubt
    @Override
    public void onBackPressed() {

    }

    public void onClickSave (View v) {
        switch (v.getId()) {
            case R.id.notizen:
                notizen.setText("");

            case R.id.anhang:

                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(camera);

                break;
            case R.id.save:

                notizen_string = notizen.getText().toString();

                String wochentag = getWochentag(startzeit.get(Calendar.DAY_OF_WEEK));

                //Millisekunden vom 1.1.1970
                long millisFrom1970_start =startzeit.getTime().getTime();
                long millisFrom1970_stop  =stoppzeit.getTime().getTime();

                //Differenz zwischen Start und Stopp der Stoppuhr
                long different =  millisFrom1970_stop - millisFrom1970_start;

                //Pausendauer ist die insgesamt vergangene Zeit abzüglich der getrackten Zeit
                long pausendauer = different - lerndauerInMs;

                //Umwandeln zu DD:HH:MM:SS
                String stringStarttime = TimeConverter(millisFrom1970_start);
                String stringStopptime = TimeConverter(millisFrom1970_stop);
                String stringPausendauer = TimeConverter(pausendauer);

                //String gesamtdauer = calculateDifference(startzeit.getTime(), stoppzeit.getTime());;
                String anhang ="ANHANG.PDF";

                Lerneinheit unit = new Lerneinheit(fachname, wochentag, startzeit.getTime().toString(),stoppzeit.getTime().toString(),stringPausendauer,lerndauer,notizen_string,anhang);
                db.addLerneinheit(unit);

                //showLerneinheiten();

                Toast.makeText(getApplicationContext(), "Eintrag wurde gespeichert", Toast.LENGTH_LONG).show();

                // Back to MainActivity
                Intent i=new Intent(this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                //finish();
                break;
            }
        }


    /*public void showLerneinheiten(){
        List<Lerneinheit> units = new LinkedList<Lerneinheit>();
        units = db.getAllLerneinheiten();
        String text = units.toString();
        testfeld.setText(text.toCharArray(),0,text.length());
    }*/

    public String getWochentag(int day) {
        String s;
        switch (day) {
            case 1:
                s = "Sonntag";
                break;
            case 2:
                s = "Montag";
                break;
            case 3:
                s = "Dienstag";
                break;
            case 4:
                s = "Mittwoch";
                break;
            case 5:
                s = "Donnerstag";
                break;
            case 6:
                s = "Freitag";
                break;
            case 7:
                s = "Samstag";
                break;
            default:
                s= "Kein Wochentag";
                break;
        }
        return  s;
    }

    public String TimeConverter(long millis){
            return String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }


    public String calculateDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        String s = elapsedDays + ":" + elapsedHours + ":" + elapsedMinutes + ":" + elapsedSeconds;
        return s;
    }

    /*For filereading. Try later...
    private void loadFileList() {
        try {
            mPath.mkdirs();
        }
        catch(SecurityException e) {
            Log.e(TAG, "unable to write on the sd card " + e.toString());
        }
        if(mPath.exists()) {
            FilenameFilter filter = new FilenameFilter() {

                @Override
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    return filename.contains(FTYPE) || sel.isDirectory();
                }

            };
            mFileList = mPath.list(filter);
        }
        else {
            mFileList= new String[0];
        }
    }

    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new Builder(this);

        switch(id) {
            case DIALOG_LOAD_FILE:
                builder.setTitle("Choose your file");
                if(mFileList == null) {
                    Log.e(TAG, "Showing file picker before loading the file list");
                    dialog = builder.create();
                    return dialog;
                }
                builder.setItems(mFileList, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mChosenFile = mFileList[which];
                        //you can do stuff with the file here too
                    }
                });
                break;
        }
        dialog = builder.show();
        return dialog;
    }
     */
}
