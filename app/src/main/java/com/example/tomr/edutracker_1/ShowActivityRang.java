package com.example.tomr.edutracker_1;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ShowActivityRang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rang);

        /*Define time range for each title*/
        int GrundschuelerTime = 10;
        int AbiturientTime = 20;
        int MasterTime = 30;

        /* Get Element ID  */
        TextView RangOutput= findViewById(R.id.OutputRangID);
        TextView RangIntro= findViewById(R.id.EinleitungZuRangID);
        GridView RewardGridView=(GridView)findViewById(R.id.grid_view_image_text);

        /*Übergabe der benötigten Parameter als Stub bis DB und Zugriffe vorhanden*/
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            super.finish();
        }

        /*Teil1: Ausgabe Rang*/
        /*Auswertung gesamte Lernzeit und Anzeige von entsprechendem Rang mit Bild und Slogan*/

        Integer OutTime= extras.getInt("LernzeitInput");

        if(OutTime >=0 & OutTime<GrundschuelerTime){
            ImageView img= (ImageView) findViewById(R.id.image);
            img.setImageResource(R.drawable.grundschueler);
            RangIntro.setText("Halte dich ran, du bist noch");
            RangOutput.setText("Grundschüler");
            float TimeTill = AbiturientTime - OutTime;

        } else if(OutTime >=GrundschuelerTime & OutTime<AbiturientTime){
            ImageView img= (ImageView) findViewById(R.id.image);
            img.setImageResource(R.drawable.abiturient);
            RangIntro.setText("Sehr schön, du bist schon");
            RangOutput.setText("Abiturient");
        }else if(OutTime >=AbiturientTime & OutTime<MasterTime){
            ImageView img= (ImageView) findViewById(R.id.image);
            img.setImageResource(R.drawable.master);
            RangIntro.setText("Immer weiter, du bist bereits");
            RangOutput.setText("Master");
        }else if(OutTime >=MasterTime){
            ImageView img= (ImageView) findViewById(R.id.image);
            img.setImageResource(R.drawable.professor);
            RangIntro.setText("Herzlichen Glückwunsch, du bist");
            RangOutput.setText("Professor");
        }

        /*Teil2: Ausgabe der Errungenschaften*/


        /*Erstellen eines Interger Arrays und befüllen mit 9 grauen Sternen*/
        int[] imgList=new int[9];
        for(int i=0;i<imgList.length;i++){
            imgList[i]=R.drawable.ic_star_grey;
        }

        /*Definition der Errungenschaften und Speichern in String Array*/
        final String txtList[]={"Grundstein","Nachteule","Früher Vogel","Wochenend-Lerner","Marathon-Lerner","Perfekte Woche","Bsp7","Bsp8","Bsp9"};

        /*Ab hier kommt Auswertelogik aus DB um Sterne bei Erfüllung Gold anzuzeigen*/

        /*Prüfung für Grundstein*/
        //dummy-Fkt
        Integer TC_Grundstein = extras.getInt("Grundstein");
        Integer TC_Nachteule = extras.getInt("Nachteule");
        Integer TC_FrueherVogel = extras.getInt("FrueherVogel");
        Integer TC_WeLerner = extras.getInt("WeLerner");
        Integer TC_MarathonLerner = extras.getInt("MarathonLerner");
        Integer TC_PerfekteWoche = extras.getInt("PerfekteWoche");

        if(TC_Grundstein==1){
            imgList[0]=R.drawable.ic_star_gold;
        }
        if(TC_Nachteule==1){
            imgList[1]=R.drawable.ic_star_gold;
        }
        if(TC_FrueherVogel==1){
            imgList[2]=R.drawable.ic_star_gold;
        }
        if(TC_WeLerner==1){
            imgList[3]=R.drawable.ic_star_gold;
        }
        if(TC_MarathonLerner==1){
            imgList[4]=R.drawable.ic_star_gold;
        }
        if(TC_PerfekteWoche==1){
            imgList[5]=R.drawable.ic_star_gold;
        }







        /*GridView mit Daten aus Adapter füllen*/
        GridAdapter adapterViewAndroid = new GridAdapter(ShowActivityRang.this, txtList, imgList);
        RewardGridView.setAdapter(adapterViewAndroid);

        /*Anlegen von Toasts und Click Listener um Errungenschaften zu erklären*/
        RewardGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                /*Anlegen der Toasts*/
                Toast toast = Toast.makeText(ShowActivityRang.this,"",Toast.LENGTH_LONG);
                if(i==0){
                    toast = Toast.makeText(ShowActivityRang.this, "Lege den Grundstein mit deiner ersten Lerneinheit", Toast.LENGTH_LONG);
                }else if(i==1){
                    toast = Toast.makeText(ShowActivityRang.this, "Lerne mehr als x Stunden zwischen y und z Uhr", Toast.LENGTH_LONG);
                }else if(i==2){
                    toast = Toast.makeText(ShowActivityRang.this, "Lerne mehr als x Stunden zwischen y Uhr und z Uhr", Toast.LENGTH_LONG);
                }else if(i==3){
                    toast = Toast.makeText(ShowActivityRang.this, "Lerne mehr als x Stunden an Wochenenden", Toast.LENGTH_LONG);
                }else if(i==4){
                    toast = Toast.makeText(ShowActivityRang.this, "Lerne über x Wochen mindestens y Stunden pro Woche", Toast.LENGTH_LONG);
                }else if(i==5){
                    toast = Toast.makeText(ShowActivityRang.this, "Lerne an 7 aufeinanderfolgenden Tagen mindestens x Stunde", Toast.LENGTH_LONG);
                }else {
                    toast = Toast.makeText(ShowActivityRang.this, "GridView Item: " + txtList[+i], Toast.LENGTH_LONG);
                }
                /*Zentrierung der Toasts*/
                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                if( v != null) v.setGravity(Gravity.CENTER);
                toast.show();
            }
        });
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
