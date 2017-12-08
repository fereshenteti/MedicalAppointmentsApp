package com.henteti.feres.webservicevolley.Base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Feres on 12/7/2017.
 */

public class BaseActivity extends AppCompatActivity {

    public ProgressDialog mProgressDialog;
    public static final String SETTINGS = "Settings_File";
    public RelativeLayout loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void showProgress(String text,Context ctx) {
        mProgressDialog = new ProgressDialog(ctx);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(text);
        mProgressDialog.show();
    }


    public void stopProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public String[] checkSharedPrefs() {
        SharedPreferences prefs = getSharedPreferences(SETTINGS, MODE_PRIVATE);
        String shared_login = prefs.getString("login", null);
        String shared_pwd = prefs.getString("pwd", null);

        if (shared_login != null && shared_pwd != null) {
            return new String[]{shared_login, shared_pwd};
        } else {
            return null;
        }
    }

    public void setSharedPrefs(String _mail, String _pwd) {
        SharedPreferences.Editor settings = getApplicationContext().getSharedPreferences(SETTINGS, MODE_PRIVATE).edit();
        settings.putString("login", _mail);
        settings.putString("pwd", _pwd);
        settings.apply();
    }


    public ArrayList hashToList(HashMap hash) {
        return new ArrayList<>(hash.values());
    }


    public void removeNotificationBar() {
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    public void removeTitleBar(){
        //remove title bar :
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }



}

