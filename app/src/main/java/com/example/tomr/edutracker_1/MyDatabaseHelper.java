package com.example.tomr.edutracker_1;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;


public class MyDatabaseHelper extends  SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LerneinheitenDB";
    private static final String TABLE_LERNEINHEITEN = "lerneinheiten";

    public MyDatabaseHelper(Context context, String name, CursorFactory factory, int version){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_LERNEINHEIT_TABLE = "CREATE TABLE " + TABLE_LERNEINHEITEN + " ( "+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fach TEXT, " +
                "wochentag TEXT, " +
                "start TEXT, " +
                "ende TEXT, " +
                "pause TEXT, " +
                "lerndauer TEXT, " +
                "notizen TEXT, " +
                "anhang TEXT )";

        db.execSQL(CREATE_LERNEINHEIT_TABLE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LERNEINHEITEN);
    }

    private static final String KEY_ID = "id";
    private static final String KEY_FACH = "fach";
    private static final String KEY_WOCHENTAG = "wochentag";
    private static final String KEY_START = "start";
    private static final String KEY_ENDE = "ende";
    private static final String KEY_PAUSE = "pause";
    private static final String KEY_LERNDAUER = "lerndauer";
    private static final String KEY_NOTIZEN = "notizen";
    private static final String KEY_ANHANG = "anhang";
    private static final String[] COLUMNS = {KEY_ID,KEY_FACH,KEY_WOCHENTAG,KEY_START,KEY_ENDE,KEY_PAUSE,KEY_LERNDAUER,KEY_NOTIZEN,KEY_ANHANG};


    public void addLerneinheit(Lerneinheit unit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(KEY_FACH,unit.getFach());
        value.put(KEY_WOCHENTAG, unit.getWochentag());
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
        unit.setWochentag(cursor.getString(2));
        unit.setStart(cursor.getString(3));
        unit.setEnde(cursor.getString(4));
        unit.setPause(cursor.getString(5));
        unit.setLerndauer(cursor.getString(6));
        unit.setNotizen(cursor.getString(7));
        unit.setAnhang(cursor.getString(8));
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
        unit.setWochentag(cursor.getString(2));
        unit.setStart(cursor.getString(3));
        unit.setEnde(cursor.getString(4));
        unit.setPause(cursor.getString(5));
        unit.setLerndauer(cursor.getString(6));
        unit.setNotizen(cursor.getString(7));
        unit.setAnhang(cursor.getString(8));
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
                unit.setWochentag(cursor.getString(2));
                unit.setStart(cursor.getString(3));
                unit.setEnde(cursor.getString(4));
                unit.setPause(cursor.getString(5));
                unit.setLerndauer(cursor.getString(6));
                unit.setNotizen(cursor.getString(7));
                unit.setAnhang(cursor.getString(8));
                lerneinheiten.add(unit);
            } while (cursor.moveToNext());
        }
        return lerneinheiten;
    }

    public int updateLerneinheit(Lerneinheit unit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(KEY_FACH, unit.getFach());
        value.put(KEY_WOCHENTAG, unit.getWochentag());
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



}
