package com.example.tomr.edutracker_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ShowActivityRang extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    //insert this for toolbar:
    public Toolbar toolbar;
    private ArrayList<String> values;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_show_rang);

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
