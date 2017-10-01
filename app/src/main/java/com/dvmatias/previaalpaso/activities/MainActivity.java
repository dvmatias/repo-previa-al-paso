package com.dvmatias.previaalpaso.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dvmatias.previaalpaso.custom.PreviaFragmentManager;
import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.custom.CustomTypefaceSpan;
import com.dvmatias.previaalpaso.fragments.LoadingFragment;
import com.dvmatias.previaalpaso.fragments.PromotionListFragment;
import com.dvmatias.previaalpaso.fragments.SponsorsFragment;
import com.dvmatias.previaalpaso.helpers.FirebaseDatabaseHelper;
import com.dvmatias.previaalpaso.interfaces.IDatabaseDownloadState;

public class MainActivity extends AppCompatActivity {
    /**
     * TAG.
     */
    @SuppressWarnings("unused")
    private final static String TAG = MainActivity.class.getSimpleName();
    /**
     *
     */
    public final Activity activitty = MainActivity.this;
    /**
     * Previa custom fragment manager.
     */
    private static PreviaFragmentManager mPreviaFragmentManager;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        mPreviaFragmentManager = new PreviaFragmentManager(this);
        if (!FirebaseDatabaseHelper.isPromotionsAndProductsReady()) {
            // Replace LoadingFragment.
            mPreviaFragmentManager.replace(R.id.container_main,
                    LoadingFragment.getInstance(),
                    LoadingFragment.TAG);
        } else {
            // Replace PromotionListFragment.
            mPreviaFragmentManager.replace(R.id.container_main,
                    PromotionListFragment.INSTANCE,
                    PromotionListFragment.TAG);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!FirebaseDatabaseHelper.isPromotionsAndProductsReady()) {
            FirebaseDatabaseHelper.downloadDatabase();
        }
    }

    /**
     * Loading state listener.
     */
    static IDatabaseDownloadState iDatabaseDownloadStateListener = new IDatabaseDownloadState() {
        @Override
        public void onLoadingStarted() {
            LoadingFragment.getInstance().startLoadingAnimation();
        }

        @Override
        public void onLoadingCompleted() {
            // Replace PromotionListFragment.
            mPreviaFragmentManager.replace(R.id.container_main,
                    PromotionListFragment.INSTANCE,
                    PromotionListFragment.TAG);
        }

        @Override
        public void onLoadingFailed() {
            LoadingFragment.showRetry();
        }

        @Override
        public void retryLoading() {
            FirebaseDatabaseHelper.downloadDatabase();
        }
    };

    /**
     * Return listener.
     */
    public static IDatabaseDownloadState getLoadingListener() {
        return iDatabaseDownloadStateListener;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            // TODO implement.
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Apply custom font to navigation drawer menu items.
     */
    private void applyFontToMenuItem(MenuItem menuItem) {
        Typeface font = Typeface.createFromAsset(getAssets(), "roboto_regular.ttf");
        SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        menuItem.setTitle(mNewTitle);
    }
}
