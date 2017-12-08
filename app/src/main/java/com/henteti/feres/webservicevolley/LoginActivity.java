package com.henteti.feres.webservicevolley;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
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
import com.henteti.feres.webservicevolley.Base.BaseActivity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

    }

    private void initView() {

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

        final String url = "http://192.168.42.103:8080/CabinetMedicalWS/login/?email="
                +username.getText().toString()
                +"&password="
                +pwd.getText().toString();


        StringRequest jsObjRequest = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        hideLoadingLayout();
                        try{
                            boolean success = Boolean.parseBoolean(response);
                            if (success){
                                Toast.makeText(LoginActivity.this,
                                        "Welcome "+username.getText().toString()+"!",
                                        Toast.LENGTH_SHORT).show();

                                //Authentication success..
                            }else{
                                Toast.makeText(LoginActivity.this,
                                        "Authentication failed! Are you already registered ?",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }catch(Exception e){
                            Toast.makeText(LoginActivity.this,e.toString()
                                    ,Toast.LENGTH_LONG).show();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideLoadingLayout();
                        Toast.makeText(LoginActivity.this,error.toString()
                                ,Toast.LENGTH_LONG).show();

                    }
                });
        AppController.getInstance(this).addToRequestQueue(jsObjRequest);


    }

    private boolean verifyFields() {

        if(username != null && !username.getText().toString().equals("")
                && pwd != null && !pwd.getText().toString().equals("")){
            return true;
        }
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
