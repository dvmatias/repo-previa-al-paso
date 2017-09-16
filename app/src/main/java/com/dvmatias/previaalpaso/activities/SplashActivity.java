package com.dvmatias.previaalpaso.activities;

import android.app.ActionBar;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.custom.SplashDialogFragment;

import java.io.IOException;
import java.util.Calendar;

public class SplashActivity extends AppCompatActivity implements SplashDialogFragment.OnDialogFragmentClickListener{
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
        SplashDialogFragment splashDialogFragment =
                SplashDialogFragment.newInstance(
                        mContext.getString(R.string.dialog_title_internet_problem),
                        mContext.getString(R.string.dialog_msg_internet_problem));
        splashDialogFragment.show(mFragmentManager,"dialog");
    }

    @Override
    public void onNeutralClicked(SplashDialogFragment dialog) {
        dialog.dismiss();
        HostAvailabilityTask task = new HostAvailabilityTask(this);
        task.execute(mHostName);

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
         * Task start time in ms.
         */
        private long mStartTaskTimeMs;
        /**
         * Minimum time task duration in ms.
         */
        private final long MIN_TIME_TASK_DURATION_MS = 4000;


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

            this.mStartTaskTimeMs = Calendar.getInstance().getTimeInMillis();
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

            long taskDuration = Calendar.getInstance().getTimeInMillis()
                    - this.mStartTaskTimeMs;
            if (taskDuration < MIN_TIME_TASK_DURATION_MS) {
                long delay = MIN_TIME_TASK_DURATION_MS - taskDuration;
                try {
                    Thread.sleep(delay);
                } catch (Exception e) {
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
                showInternetProblemsDialog();
            }
            this.cancel(true);
        }

        /**
         * Check if the device is not in airplane mode. </br>
         *
         * @return [boolean] "true" if the device is not in
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