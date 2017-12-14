package com.henteti.feres.webservicevolley;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.JsonReader;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.henteti.feres.webservicevolley.Base.BaseActivity;
import com.henteti.feres.webservicevolley.Models.Medecin;
import com.henteti.feres.webservicevolley.Singleton.AppController;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "userCheck";
    private MaterialEditText username, pwd;
    private AppCompatButton connect;
    private RelativeLayout loading;
    public static Activity logincxt;
    private Medecin m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logincxt = this;
        initView();

    }

    private void initView() {

        //check if these informations are stored or not
        if(checkSharedPrefs()!=null){
            startActivity(new Intent(this,CalendarActivity.class));
        }

        loading = (RelativeLayout) findViewById(R.id.loading);
        loading.setVisibility(View.GONE);
        username = (MaterialEditText) findViewById(R.id.username);
        pwd = (MaterialEditText) findViewById(R.id.pwd);
        connect = (AppCompatButton) findViewById(R.id.connect);
        connect.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.connect:

                //first we verify the fields
                if (verifyFields()){

                    //if the fields are not empty, show loading layout
                    showLoadingLayout();

                    //then verify the authentication on the server side
                    authenticate();

                }else{

                    //if one of the fields are empty, show warning
                    Toast.makeText(this,"Please fill in all the fields!",Toast.LENGTH_SHORT).show();

                }


                break;
        }
    }

    private void authenticate() {

        final String url = "http://192.168.42.103:8080/CabinetMedicalWS/medecin/medecinsLogIN/?username="
                +username.getText().toString()
                +"&password="
                +pwd.getText().toString();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideLoadingLayout();

                    Gson gson = new Gson();
                    m = gson.fromJson(response.toString(), Medecin.class);
                    AppController.getInstance(LoginActivity.this).setMedecin(m);
                    //check if these informations are stored or not, and if not, store them
                    if(checkSharedPrefs()==null ||
                            !checkSharedPrefs()[0].equals(username.getText().toString()) ||
                            !checkSharedPrefs()[1].equals(pwd.getText().toString())){
                        setSharedPrefs(username.getText().toString(),pwd.getText().toString());
                    }

                    //Authentication success..
                    startActivity(new Intent(LoginActivity.this,CalendarActivity.class));
            }

        },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideLoadingLayout();
                        pwd.setError("utilisateur ou mot de passe incorrect !");
                    }
                });


        AppController.getInstance(this).addToRequestQueue(jsObjRequest);


    }

    private boolean verifyFields() {

        if(username != null && !username.getText().toString().equals("")
                && pwd != null && !pwd.getText().toString().equals("")){
            return true;
        }

        if(username == null || username.getText().toString().equals(""))
            username.setError("veuillez entrer votre nom d'utilisateur svp !");

        if(pwd == null || pwd.getText().toString().equals(""))
            pwd.setError("veuillez entrer votre mot de passe svp !");

        return false;

    }

    private void showLoadingLayout() {

        loading.setVisibility(View.VISIBLE);
        username.setEnabled(false);
        pwd.setEnabled(false);
        connect.setEnabled(false);

    }

    private void hideLoadingLayout() {

        loading.setVisibility(View.GONE);
        username.setEnabled(true);
        pwd.setEnabled(true);
        connect.setEnabled(true);

    }

}
