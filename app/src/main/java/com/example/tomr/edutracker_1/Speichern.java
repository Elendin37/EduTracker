package com.example.tomr.edutracker_1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class Speichern extends AppCompatActivity {

    String fachname, lerndauer,notizen_string;
    long lerndauerInMs;
    Calendar startzeit, stoppzeit;
    Button addAnhang, speichern;
    TextView fach, lernzeit, filename;
    EditText notizen;
    ImageView vorschauAnhang;
    String IMAGE_DIRECTORY;
    Uri file;
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


        IMAGE_DIRECTORY = "Interner Speicher/EduTracker";

        fach = findViewById(R.id.fach);
        lernzeit = findViewById(R.id.lerndauer);
        notizen = findViewById(R.id.notiz);
        filename = findViewById(R.id.filename);
        vorschauAnhang =  findViewById(R.id.vorschau);

        speichern = findViewById(R.id.save);
        addAnhang = findViewById(R.id.anhang);

        notizen.setTextColor(Color.GRAY);
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
            case R.id.notiz:
                notizen.setTextColor(Color.BLACK);
                if(notizen.getText().toString().equals("Notizen hinzufügen")){
                    notizen.setText("");}
                break;

            case R.id.anhang:
                showPictureDialog();
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

                Lerneinheit unit = new Lerneinheit(fachname, startzeit.getTime().toString(),stoppzeit.getTime().toString(),stringPausendauer,lerndauer,notizen_string,anhang);
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

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Wähle eine Aktion aus");
        String[] pictureDialogItems = {
                "Auswahl eines Fotos aus der Galerie",
                "Aufnehmen eines Fotos mit der Kamera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, 3);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);

        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, 100);*/

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == 3) {//gallery
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    vorschauAnhang.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (requestCode == CAMERA) {//camera
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            vorschauAnhang.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }



   /* private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }*/


}
