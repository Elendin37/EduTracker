package com.example.tomr.edutracker_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "FaecherDB";
    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version)
    //super(context, name, factory, version);
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FAECHER_TABLE = "CREATE TABLE faecher ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, "+
                "zielzeit float, "+
                "istzeit float )";
        // create faecher table
        db.execSQL(CREATE_FAECHER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older facher table if existed
        db.execSQL("DROP TABLE IF EXISTS faecher");
        // create fresh facher table
        this.onCreate(db);

    }

    /**
     * CRUD operations (create "add", read "get", update, delete) book +
     * get all books + delete all books
     */

    //table name
    private static final String TABLE_FAECHER = "faecher";

    //Table Columns names
    private static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    private static final String KEY_ZIELZEIT = "zielzeit";
    private static final String KEY_ISTZEIT = "istzeit";

    private static final String[] COLUMNS = {KEY_ID,KEY_TITLE,KEY_ZIELZEIT, KEY_ISTZEIT};

    public void addFach(Fach fach){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value=new ContentValues() ;
        value.put(KEY_TITLE,fach.getTitle());
        value.put(KEY_ZIELZEIT, fach.getZielzeit());
        value.put(KEY_ISTZEIT, fach.getIstzeit());
        db.insert(TABLE_FAECHER, // table
                null, //nullColumnHack
                value);
        // key/value -> keys = column names and values = column values

        db.close();
        Log.d("addFach", fach.toString());
    }

    public Fach getFach(int id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_FAECHER, // select table
                        COLUMNS, // select columns
                        " id = ?", // select entry by primary key
                        new String[] { String.valueOf(id) }, // selections args
                        null, // group by
                        null, // having
                        null, // order by
                        null); // limit

        // if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
        Fach fach=new Fach();
        fach.setIstzeit(cursor.getFloat(3));
        fach.setZielzeit(cursor.getFloat(2));
        fach.setTitle(cursor.getString(1));
        fach.setId(Integer.parseInt(cursor.getString(0)));
        Log.d("getFach", fach.toString());
        db.close();
        return fach;
    }

    public Fach getFachname(String fachname){
        SQLiteDatabase db=this.getReadableDatabase();
/*
        Cursor cursor =
                db.query(TABLE_FAECHER, // select table
                        COLUMNS, // select columns
                        " id = ?", // select entry by primary key
                        new String[] { String.valueOf(fachname) }, // selections args
                        null, // group by
                        null, // having
                        null, // order by
                        null); // limit
*/
        Cursor cursor =db.query(MySQLiteHelper.TABLE_FAECHER,COLUMNS,null,null,null,null,null);
        // if we got results get the first one
        //String vergleich;
        boolean fachvorhanden = false;

        while (cursor.moveToNext()){
            //vergleich =cursor.getString(cursor.getColumnIndex(MySQLiteHelper.KEY_TITLE));
            if(fachname.equals(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.KEY_TITLE)))){
                fachvorhanden = true;
                break;
            }
        }
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
        //build the query
        String query = "SELECT  * FROM " + TABLE_FAECHER;
        // get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // go over each row, build book and add it to list
        Fach fach = null;
        if (cursor.moveToFirst()) {
            do {
                fach=new Fach();
                fach.setIstzeit(cursor.getFloat(3));
                fach.setZielzeit(cursor.getFloat(2));
                fach.setTitle(cursor.getString(1));
                fach.setId(Integer.parseInt(cursor.getString(0)));

                // Add book to books
                faecher.add(fach);
            } while (cursor.moveToNext());
        }

        //Log.d("showAllFaecher()", faecher.toString());

        // return books
        return faecher;

    }

    public int updateFach(int id, String newTitel, float newZielzeit, float newIstzeit){

        SQLiteDatabase db = this.getWritableDatabase();

        String selection = KEY_ID+" = ?";
        String[] selectionArgs =  { String.valueOf(id) };

        ContentValues contentValues = new ContentValues();
        //contentValues.put(KEY_ID, id);
        contentValues.put(KEY_TITLE, newTitel);
        contentValues.put(KEY_ZIELZEIT, newZielzeit);
        contentValues.put(KEY_ISTZEIT, newIstzeit);

        int i = db.update(TABLE_FAECHER, contentValues, selection, selectionArgs);

        db.close();
        return i;
    }


    public void deleteFach(Fach fach){
        Log.d("deleteFach ", "deleteFach");
        String number=String.valueOf(fach.getId());
        Log.d("deleteFach "+number, fach.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAECHER,
                KEY_ID+" = ?",
                new String[] { String.valueOf(fach.getId()) });
        Log.d("deleteFach2", fach.toString());




    }




}
