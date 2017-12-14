package com.henteti.feres.webservicevolley;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.henteti.feres.webservicevolley.Adapters.MyAdapter;
import com.henteti.feres.webservicevolley.Base.BaseActivity;
import com.henteti.feres.webservicevolley.Models.Medecin;
import com.henteti.feres.webservicevolley.Models.Patient;
import com.henteti.feres.webservicevolley.Models.Rdv;
import com.henteti.feres.webservicevolley.Singleton.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends BaseActivity {

    FloatingActionButton fab;
    TextView txt, welcome;
    CalendarView mydate;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Rdv> Rdvs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        LoginActivity.logincxt.finish();
        checkUser();    }

    private void initView() {


        mydate = (CalendarView) findViewById(R.id.mydate);
        txt = (TextView) findViewById(R.id.txt);
        welcome = (TextView) findViewById(R.id.welcome);
        Rdvs = new ArrayList<>();
        welcome.setText("Bienvenue "+AppController.getInstance(this).getMedecin().getPrenom()+" "+
                AppController.getInstance(this).getMedecin().getNom());


        mydate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String s = year+"-"+(month+1)+"-"+day;
                txt.setText("Vos rendez-vous pour le "+s+" :");
                fetchData(s);
                showProgress("Recherche de rendez-vous pour le "+s,CalendarActivity.this);
            }
        });


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSharedPrefs();
                startActivity(new Intent(CalendarActivity.this,LoginActivity.class));
                finish();
            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);




    }

    private void fetchData(String date){

        if(Rdvs != null) Rdvs.clear();

        //recuperer la liste des rdvs
        final String url = "http://192.168.42.103:8080/CabinetMedicalWS/cabinet/rdvsMedecinByDate?idMedecin="
                +AppController.getInstance(this).getMedecin().getIdMedecin()
                +"&date="
                +date;

            JsonArrayRequest jsObjRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                stopProgress();

                for (int i = 0; i < response.length(); i++ ){
                    try {
                        Rdvs.add(new Gson().fromJson(response.get(i).toString(), Rdv.class));
                    } catch (JSONException e) {
                        Toast.makeText(CalendarActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }
                }

                initializeAdapter();
            }

        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        stopProgress();
                        Toast.makeText(CalendarActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });


        AppController.getInstance(this).addToRequestQueue(jsObjRequest);

    }

    private void initializeAdapter(){
        mAdapter = new MyAdapter(Rdvs);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void checkUser() {

        showProgress("Chargement..",CalendarActivity.this);

        if(AppController.getInstance(CalendarActivity.this).getMedecin()==null){

            final String url = "http://192.168.42.103:8080/CabinetMedicalWS/medecin/medecinsLogIN/?username="
                    +checkSharedPrefs()[0]
                    +"&password="
                    +checkSharedPrefs()[1];


            JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                stopProgress();

                Gson gson = new Gson();
                Medecin m = gson.fromJson(response.toString(), Medecin.class);
                AppController.getInstance(CalendarActivity.this).setMedecin(m);
                initView();

            }

        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        stopProgress();
                        Toast.makeText(CalendarActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });

        AppController.getInstance(this).addToRequestQueue(jsObjRequest);

        }else{
            stopProgress();
            initView();
        }
    }



}
