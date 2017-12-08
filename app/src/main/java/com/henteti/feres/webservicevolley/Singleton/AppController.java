package com.henteti.feres.webservicevolley.Singleton;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.henteti.feres.webservicevolley.Models.User;

import java.util.List;

/**
 * Created by Feres on 12/7/2017.
 */

public class AppController {

        private static AppController mInstance;
        private static Context mCtx;
        private RequestQueue mRequestQueue;

        private User user;

        private AppController(Context context) {
            mCtx = context;
            mRequestQueue = getRequestQueue();
        }

        // if an instance is already created , it will return it . if no instance was created , it will create a new one then reurn it
        public static synchronized AppController getInstance(Context context) {
            if (mInstance == null) {
                mInstance = new AppController(context);
            }
            return mInstance;
        }

        public RequestQueue getRequestQueue() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            }
            return mRequestQueue;
        }

        public  void addToRequestQueue(@NonNull final Request request) {
            getRequestQueue().add(request);
        }

        public  void addToRequestQueueWithTag(@NonNull final Request request, String tag) {
            request.setTag(tag);
            getRequestQueue().add(request);
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

}


