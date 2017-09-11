package com.dvmatias.previaalpaso;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {
    /**
     * TAG - Tag to use in logs.
     */
    @SuppressWarnings("unused")
    private static final String TAG = SplashActivity.class.getSimpleName();
    /**
     * String - Name of the host to check Internet connectivity.
     */
    private final static String mHostName = "www.google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        HostAvailabilityTask task =
                new HostAvailabilityTask();
        task.execute(mHostName);
    }

    private class HostAvailabilityTask extends AsyncTask<String, Void, Boolean> {
        private boolean isInternetAvailable;

        @Override
        protected void onPreExecute() {
            this.isInternetAvailable = false;
        }

        protected Boolean doInBackground(String... params) {
            if (isOnline()) {
                try {
                    Process process = java.lang.Runtime.getRuntime()
                            .exec("ping -c 1 " + params[0]);
                    isInternetAvailable = process.waitFor()==0;
                    process.destroy();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return isInternetAvailable;
        }

        protected void onPostExecute(Boolean... result) {
            if (result[0]) {
                // TODO launch main
            } else {
                // TODO problem dialog.
            }
        }

        boolean isOnline() {
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
    }
}
