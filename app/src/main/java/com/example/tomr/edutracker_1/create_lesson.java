package com.example.tomr.edutracker_1;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class create_lesson extends AppCompatActivity {

    //declaring variables of the class:
    public String title;
    public float zielzeit;
    public float istzeit;
    private Toast errToast;
    private Toast doneToast;
    public Fach fach;
    private EditText text1; // bufer variable
    private EditText text2; // buffer variable
    private EditText text3; // buffer variable

    private Context context;
    private String errormessage = "Eingabe fehlerhaft.";
    private String donemessage = "Eingabe erfolgreich.";
    private int duration = Toast.LENGTH_SHORT;

    MyDatabaseHelper db = new MyDatabaseHelper(this, null, null, 0);

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lesson);
        context = getApplicationContext();
        errToast = Toast.makeText(context, errormessage, duration);
        doneToast = Toast.makeText(context, donemessage, duration);
    }

    public void onClickNew(View view) {

        text1=(EditText)findViewById(R.id.editTextzielzeit);    // readout Zielzeit
        text2=(EditText)findViewById(R.id.editTextistzeit);     // readout Istzeit
        text3=(EditText)findViewById(R.id.editTextTitel);       // readout Title

        // check if every field is filled
        if ((isEmpty(text1)==false) && (isEmpty(text2)==false) && (isEmpty(text3)==false)  ) {

            zielzeit    = Float.valueOf(text1.getText().toString());    // write zielzeit in variable
            istzeit     = Float.valueOf(text2.getText().toString());    // write istzeit in variable
            title       = text3.getText().toString();                   // write title i n variable
            fach        = new Fach(title, istzeit, zielzeit);           // create class instance Fach with declared variables
            db.addFach(fach);   // add Fach to database
            doneToast.show();// Show toast everything worked
            finish();   // close activity
        }
        else {
            errToast.show(); // show errortoast
            //finish(); //close Activity
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
}