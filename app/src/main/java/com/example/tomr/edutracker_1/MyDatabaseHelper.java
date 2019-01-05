package com.example.tomr.edutracker_1;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.regex.Pattern;

import static java.sql.Time.valueOf;


public class MyDatabaseHelper extends  SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "EduTrackerDB";
    private static final String TABLE_LERNEINHEITEN = "lerneinheiten";
    private static final String TABLE_FAECHER = "faecher";
    private static final String TABLE_STATUS = "status";

    public MyDatabaseHelper(Context context, String name, CursorFactory factory, int version){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_LERNEINHEIT_TABLE = "CREATE TABLE " + TABLE_LERNEINHEITEN + " ( "+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fach TEXT, " +
                "start TEXT, " +
                "ende TEXT, " +
                "pause TEXT, " +
                "lerndauer TEXT, " +
                "notizen TEXT, " +
                "anhang TEXT )";

        db.execSQL(CREATE_LERNEINHEIT_TABLE);

        String CREATE_FAECHER_TABLE = "CREATE TABLE " + TABLE_FAECHER +  " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, "+
                "zielzeit float, "+
                "istzeit float )";
        // create faecher table
        db.execSQL(CREATE_FAECHER_TABLE);

        String CREATE_STATUS_TABLE = "CREATE TABLE " + TABLE_STATUS +  " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "grundstein integer, "+
                "nachteule integer, "+
                "fruehervogel integer, "+
                "wochenendlerner integer, "+
                "marathonlerner integer, "+
                "perfektewoche integer )";
        // create status table
        db.execSQL(CREATE_STATUS_TABLE);


    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LERNEINHEITEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAECHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
        this.onCreate(db);
    }
//------------------------------Funktionen Lerneinheiten----------------------------



    private static final String KEY_ID = "id";
    private static final String KEY_FACH = "fach";
    private static final String KEY_START = "start";
    private static final String KEY_ENDE = "ende";
    private static final String KEY_PAUSE = "pause";
    private static final String KEY_LERNDAUER = "lerndauer";
    private static final String KEY_NOTIZEN = "notizen";
    private static final String KEY_ANHANG = "anhang";
    private static final String[] COLUMNS = {KEY_ID,KEY_FACH,KEY_START,KEY_ENDE,KEY_PAUSE,KEY_LERNDAUER,KEY_NOTIZEN,KEY_ANHANG};



    public void addLerneinheit(Lerneinheit unit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(KEY_FACH,unit.getFach());
        value.put(KEY_START,unit.getStart());
        value.put(KEY_ENDE, unit.getEnde());
        value.put(KEY_PAUSE, unit.getPause());
        value.put(KEY_LERNDAUER,unit.getLerndauer());
        value.put(KEY_NOTIZEN, unit.getNotizen());
        value.put(KEY_ANHANG,unit.getAnhang());
        db.insert(TABLE_LERNEINHEITEN,null,value);
        db.close();
    }

    public Lerneinheit getLerneinheit(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LERNEINHEITEN, COLUMNS, "id = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        Lerneinheit unit = new Lerneinheit();
        unit.setId(Integer.parseInt(cursor.getString(0)));
        unit.setFach(cursor.getString(1));
        unit.setStart(cursor.getString(2));
        unit.setEnde(cursor.getString(3));
        unit.setPause(cursor.getString(4));
        unit.setLerndauer(cursor.getString(5));
        unit.setNotizen(cursor.getString(6));
        unit.setAnhang(cursor.getString(7));
        db.close();
        return unit;
    }


    public Lerneinheit getFach(String fachname){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_LERNEINHEITEN, // select table
                        COLUMNS, // select columns
                        " fach = ?", // select entry by primary key
                        new String[] {fachname}, // selections args
                        null, // group by
                        null, // having
                        null, // order by
                        null); // limit
        if (cursor != null)
            cursor.moveToFirst();
        Lerneinheit unit =new Lerneinheit();
        unit.setId(Integer.parseInt(cursor.getString(0)));
        unit.setFach(cursor.getString(1));
        unit.setStart(cursor.getString(2));
        unit.setEnde(cursor.getString(3));
        unit.setPause(cursor.getString(4));
        unit.setLerndauer(cursor.getString(5));
        unit.setNotizen(cursor.getString(6));
        unit.setAnhang(cursor.getString(7));
        db.close();
        return unit;
    }

    public List<Lerneinheit> getAllLerneinheiten() {
        List<Lerneinheit> lerneinheiten = new LinkedList<Lerneinheit>();
        String query = "SELECT * FROM " + TABLE_LERNEINHEITEN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Lerneinheit unit = null;
        if (cursor.moveToFirst()) {
            do {
                unit = new Lerneinheit();
                unit.setId(Integer.parseInt(cursor.getString(0)));
                unit.setFach(cursor.getString(1));
                unit.setStart(cursor.getString(2));
                unit.setEnde(cursor.getString(3));
                unit.setPause(cursor.getString(4));
                unit.setLerndauer(cursor.getString(5));
                unit.setNotizen(cursor.getString(6));
                unit.setAnhang(cursor.getString(7));
                lerneinheiten.add(unit);
            } while (cursor.moveToNext());
        }
        return lerneinheiten;
    }

    public int updateLerneinheit(Lerneinheit unit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(KEY_FACH, unit.getFach());
        value.put(KEY_START,unit.getStart());
        value.put(KEY_ENDE, unit.getEnde());
        value.put(KEY_PAUSE, unit.getPause());
        value.put(KEY_LERNDAUER,unit.getLerndauer());
        value.put(KEY_NOTIZEN, unit.getNotizen());
        value.put(KEY_ANHANG,unit.getAnhang());
        int i = db.update(TABLE_LERNEINHEITEN, value,KEY_ID+" = ?",new String[]{String.valueOf(unit.getId())});
        db.close();
        return i;
    }

    public int updateFachEinstellungen(int id, String newNotiz, String newAnhang){

        SQLiteDatabase db = this.getWritableDatabase();

        String selection = KEY_ID+" = ?";
        String[] selectionArgs =  { String.valueOf(id) };

        ContentValues contentValues = new ContentValues();
        //contentValues.put(KEY_ID, id);
        contentValues.put(KEY_NOTIZEN, newNotiz);
        contentValues.put(KEY_ANHANG, newAnhang);

        int i = db.update(TABLE_LERNEINHEITEN, contentValues, selection, selectionArgs);

        db.close();
        return i;
    }

    public void deleteLerneinheit(Lerneinheit unit){
        String number = String.valueOf(unit.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LERNEINHEITEN, KEY_ID+" = ?", new String[]{String.valueOf(unit.getId())});
    }

    public String getFachdata(String fachtitel, String Startdatum, String Enddatum) {
        SQLiteDatabase db = this.getWritableDatabase();

        String Gesamtzeit="";
        String data="";

        long totalsec=0;
        int gh=0;
        int gmin=0;
        int gsec=0;
        int datah=0;
        int datamin=0;
        int datasec=0;


        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        Date selectStartdate = null;
        Date selectEnddate = null;
        Date DateLerneinheit=null;

        try {
            selectStartdate = sdf.parse(Startdatum);
            selectEnddate = sdf.parse(Enddatum);

        }catch (ParseException e) {
            e.printStackTrace();
        }


        String DatumLerneinheit;

        Cursor cursor =db.query(MyDatabaseHelper.TABLE_LERNEINHEITEN,COLUMNS,null,null,null,null,null);

        while (cursor.moveToNext()){
            if(fachtitel.equals(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_FACH)))) {
/*
                DatumLerneinheit = cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_START));
                SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZ yyyy", Locale.GERMANY);

                try {
                    DateLerneinheit = sdf1.parse(DatumLerneinheit);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(DateLerneinheit.after(selectStartdate) && DateLerneinheit.before(selectEnddate)) {
*/
                    data = cursor.getString(5);

                    String[] time = data.split(Pattern.quote(":"));
                    datah = Integer.parseInt(time[0]);
                    datamin = Integer.parseInt(time[1]);
                    datasec = Integer.parseInt(time[2]);

                    gh = gh + datah;
                    gmin = gmin + datamin;
                    gsec = gsec + datasec;
//                }
            }


        }

        totalsec = ((gh * 60) + gmin) * 60 + gsec;

        Long lstunden, lminuten, lsekunden;
        String stunden, minuten, sekunden;

        lstunden = totalsec/3600;
        stunden =  String.format("%02d", lstunden);

        lminuten =((totalsec)/60)%60;
        minuten = String.format("%02d", lminuten);

        lsekunden = totalsec%60;
        sekunden = String.format("%02d", lsekunden);

        Gesamtzeit = stunden+":"+minuten+":"+sekunden;


        return Gesamtzeit;
    }



//------------------------------Funktionen Fächer----------------------------


    private static final String KEY_ID_FACH = "id";
    public static final String KEY_TITLE = "title";
    private static final String KEY_ZIELZEIT = "zielzeit";
    private static final String KEY_ISTZEIT = "istzeit";
    private static final String[] COLUMNS_FACH = {KEY_ID_FACH,KEY_TITLE,KEY_ZIELZEIT, KEY_ISTZEIT};



    public void addFach(Fach fach){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value=new ContentValues() ;
        value.put(KEY_TITLE,fach.getTitle());
        value.put(KEY_ZIELZEIT, fach.getZielzeit());
        value.put(KEY_ISTZEIT, fach.getIstzeit());
        db.insert(TABLE_FAECHER,null, value);
        db.close();
    }

    public Fach getFach(int id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_FAECHER, // select table
                        COLUMNS_FACH, // select columns
                        " id = ?", // select entry by primary key
                        new String[] { String.valueOf(id) }, // selections args
                        null, // group by
                        null, // having
                        null, // order by
                        null); // limit

        if (cursor != null)
            cursor.moveToFirst();
        Fach fach=new Fach();
        fach.setIstzeit(cursor.getFloat(3));
        fach.setZielzeit(cursor.getFloat(2));
        fach.setTitle(cursor.getString(1));
        fach.setId(Integer.parseInt(cursor.getString(0)));
        db.close();
        return fach;
    }

    public Fach getFachname(String fachname){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor =db.query(MyDatabaseHelper.TABLE_FAECHER,COLUMNS_FACH,null,null,null,null,null);

        boolean fachvorhanden = false;

        while (cursor.moveToNext()){
            //vergleich =cursor.getString(cursor.getColumnIndex(MySQLiteHelper.KEY_TITLE));
            if(fachname.equals(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.KEY_TITLE)))){
                fachvorhanden = true;
                break;
            }
        }
        //TODO: Christoph auf TRY-CATCH ändern
        if(fachvorhanden) {
            Fach fach = new Fach();
            fach.setIstzeit(cursor.getFloat(3));
            fach.setZielzeit(cursor.getFloat(2));
            fach.setTitle(cursor.getString(1));
            fach.setId(Integer.parseInt(cursor.getString(0)));
            Log.d("getFachname", fach.toString());
            db.close();
            return fach;
        }
        else
        {
            Fach fach = new Fach();
            fach.setIstzeit((float) 0.0);
            fach.setZielzeit((float) 0.0);
            fach.setTitle("ERROR");
            fach.setId(9999);
            db.close();
            return fach;
        }

    }


    public List<Fach> getAllFaecher() {

        List<Fach> faecher = new LinkedList<Fach>();
        String query = "SELECT  * FROM " + TABLE_FAECHER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Fach fach = null;
        if (cursor.moveToFirst()) {
            do {
                fach=new Fach();
                fach.setIstzeit(cursor.getFloat(3));
                fach.setZielzeit(cursor.getFloat(2));
                fach.setTitle(cursor.getString(1));
                fach.setId(Integer.parseInt(cursor.getString(0)));

                faecher.add(fach);
            } while (cursor.moveToNext());
        }
        return faecher;
    }

    public int updateFach(int id, String newTitel, float newZielzeit, float newIstzeit){

        SQLiteDatabase db = this.getWritableDatabase();

        String selection = KEY_ID_FACH+" = ?";
        String[] selectionArgs =  { String.valueOf(id) };

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, newTitel);
        contentValues.put(KEY_ZIELZEIT, newZielzeit);
        contentValues.put(KEY_ISTZEIT, newIstzeit);

        int i = db.update(TABLE_FAECHER, contentValues, selection, selectionArgs);

        db.close();
        return i;
    }

    public void deleteFach(Fach fach){
        String number=String.valueOf(fach.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAECHER,KEY_ID_FACH+" = ?", new String[] { String.valueOf(fach.getId()) });
    }


    //------------------------------Funktionen Status ----------------------------
    private static final String KEY_ID_STATUS = "id";
    private static final String KEY_GRUNDSTEIN = "grundstein";
    private static final String KEY_NACHTEULE = "nachteule";
    private static final String KEY_FRVOGEL = "fruehervogel";
    private static final String KEY_WELERNER = "wochenendlerner";
    private static final String KEY_MARLERNER = "marathonlerner";
    private static final String KEY_PWOCHE = "perfektewoche";
    private static final String[] COLUMNS_STATUS = {KEY_ID_STATUS,KEY_GRUNDSTEIN,
            KEY_NACHTEULE,KEY_FRVOGEL, KEY_WELERNER,KEY_MARLERNER,KEY_PWOCHE};

    public void updatestatus(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_STATUS,null);
        if (c.moveToFirst()){

            String selection = KEY_ID_STATUS+" = ?";
            String[] selectionArgs =  { String.valueOf(1) };

            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_GRUNDSTEIN, 1);
            contentValues.put(KEY_NACHTEULE, 1);
            contentValues.put(KEY_FRVOGEL, 1);
            contentValues.put(KEY_WELERNER, 1);
            contentValues.put(KEY_MARLERNER, 1);
            contentValues.put(KEY_PWOCHE, 1);

            //int i = db.update(TABLE_STATUS, contentValues, selection, selectionArgs);
            db.update(TABLE_STATUS, contentValues, selection, selectionArgs);



        }else{

            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_GRUNDSTEIN, 1);
            contentValues.put(KEY_NACHTEULE, 0);
            contentValues.put(KEY_FRVOGEL, 1);
            contentValues.put(KEY_WELERNER, 1);
            contentValues.put(KEY_MARLERNER, 0);
            contentValues.put(KEY_PWOCHE, 0);

            db.insert(TABLE_STATUS,null, contentValues);


        }
        c.close();
        db.close();

    }
    public Status getstatus(){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_STATUS, // select table
                        COLUMNS_STATUS, // select columns
                        " id = ?", // select entry by primary key
                        new String[] { String.valueOf(1) }, // selections args
                        null, // group by
                        null, // having
                        null, // order by
                        null); // limit

        if (cursor != null)
            cursor.moveToFirst();
        Status status=new Status();
        status.setGrundstein(cursor.getInt(1));
        status.setNachteule(cursor.getInt(2));
        status.setFrVogel(cursor.getInt(3));
        status.setWELerner(cursor.getInt(4));
        status.setMaLerner(cursor.getInt(5));
        status.setPWoche(cursor.getInt(6));

        db.close();
        return status;
    }

}

