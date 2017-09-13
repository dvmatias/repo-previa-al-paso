package com.dvmatias.previaalpaso.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.custom.GeneralDialogFragment;
import com.dvmatias.previaalpaso.custom.GeneralDialogFragmentConfiguration;

public class SplashActivity extends AppCompatActivity implements GeneralDialogFragment.OnDialogFragmentClickListener{
    /**
     * TAG - Tag to use in logs.
     */
    @SuppressWarnings("unused")
    private static final String TAG = SplashActivity.class.getSimpleName();
    /**
     * String - Name of the host to check Internet connectivity.
     */
    private final static String mHostName = "www.google.com";
    /**
     * Fragment manager.
     */
    private static FragmentManager mFragmentManager;

    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mFragmentManager = getSupportFragmentManager();

        mContext = getApplicationContext();

        HostAvailabilityTask task =
                new HostAvailabilityTask(this);
        task.execute(mHostName);
    }

    /**
     * Show custom dialog to retry Internet connection
     * check (launch HostAvailabilityTask).
     */
    private static void showInternetProblemsDialog() {
        GeneralDialogFragmentConfiguration dialogFragmentConfiguration =
                new GeneralDialogFragmentConfiguration.Builder(mContext)
                .title("Titulo Configurado")
                .message("Mensaje personalizado.")
                .width(0.7f)
                .height(0.3f)
                .build();
        GeneralDialogFragment generalDialogFragment =
                GeneralDialogFragment.newInstance();
        generalDialogFragment.init(dialogFragmentConfiguration);

        generalDialogFragment.show(mFragmentManager, "dialog");
        // TODO show()?
    }

    @Override
    public void onOkClicked(GeneralDialogFragment dialog) {
        // TODO
    }

    @Override
    public void onCancelClicked(GeneralDialogFragment dialog) {
        // TODO
    }

    @Override
    public void onNeutralClicked(GeneralDialogFragment dialog) {
        // TODO
    }

    /**
     * Inner class.
     *
     * AsyncTask. Check Internet connection and host availability.
     */
    private class HostAvailabilityTask extends AsyncTask<String, Void, Boolean> {
        /**
         * Activity.
         */
        private SplashActivity splashActivity;
        /**
         * Boolean - Used to store Internet status. false if not connection available.
         */
        private boolean isInternetAvailable;

        /**
         * Constructor.
         *
         * @param splashActivity - [Activity] Activity instance.
         */
        HostAvailabilityTask(SplashActivity splashActivity) {
            this.splashActivity = splashActivity;
        }

        @Override
        protected void onPreExecute() {
            this.isInternetAvailable = false;
        }

        @Override
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

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                // TODO launch main.
            } else {
                // TODO Internet problem dialog.
                this.splashActivity.showInternetProblemsDialog();
            }
        }

        /**
         * Check if the device is not in airplane mode.
         *
         * @return [boolean] - "true" if the device is not in
         * airplane mode, "false" otherwise.
         */
        private boolean isOnline() {
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
    }
}