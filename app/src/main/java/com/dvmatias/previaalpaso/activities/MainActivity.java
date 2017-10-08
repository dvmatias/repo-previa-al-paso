package com.dvmatias.previaalpaso.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.custom.PreviaFragmentManager;
import com.dvmatias.previaalpaso.fragments.LoadingFragment;
import com.dvmatias.previaalpaso.fragments.PromotionListFragment;
import com.dvmatias.previaalpaso.helpers.FirebaseDatabaseHelper;
import com.dvmatias.previaalpaso.interfaces.IDatabaseDownloadState;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class MainActivity extends AppCompatActivity {
    /**
     * TAG.
     */
    @SuppressWarnings("unused")
    private final static String TAG = MainActivity.class.getSimpleName();
    /**
     * Previa custom fragment manager.
     */
    private static PreviaFragmentManager mPreviaFragmentManager;
    /**
     * Fragments container.
     */
    @SuppressLint("StaticFieldLeak")
    public static FrameLayout container;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        new FirebaseDatabaseHelper(getApplicationContext());

        // ActionBar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Add margin to toolbar to avoid status bar overlap.
        CollapsingToolbarLayout.LayoutParams toolbarParams = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        toolbarParams.setMargins(0,getStatusBarHeight(),0,0);
        toolbar.setLayoutParams(toolbarParams);

        // Select marquee to auto scroll.
        findViewById(R.id.tv_app_bar_main_toolbar_marquee).setSelected(true);

        // Floating button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // TODO Implement action.
            }
        });

        // Collapsing Toolbar.
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

        container = findViewById(R.id.container_main);

        mPreviaFragmentManager = new PreviaFragmentManager(this);
        if (!FirebaseDatabaseHelper.isPromotionsAndProductsReady()) {
            CoordinatorLayout.LayoutParams params =
                    (CoordinatorLayout.LayoutParams) container.getLayoutParams();
            params.setBehavior(null);
            container.requestLayout();
            // Replace LoadingFragment.
            mPreviaFragmentManager.replace(R.id.container_main,
                    LoadingFragment.getInstance(),
                    LoadingFragment.TAG);
        } else {
            CoordinatorLayout.LayoutParams params =
                    (CoordinatorLayout.LayoutParams) container.getLayoutParams();
            params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
            container.requestLayout();
            // Replace PromotionListFragment.
            mPreviaFragmentManager.replace(R.id.container_main,
                    PromotionListFragment.INSTANCE,
                    PromotionListFragment.TAG);
        }

        // UIL setup.
        setupUIL();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * TODO desc
     * @param activity
     * @param bits
     * @param on
     */
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * Calculate the status bar height.
     *
     * @return [int] Status bar height.
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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
            CoordinatorLayout.LayoutParams params =
                    (CoordinatorLayout.LayoutParams) container.getLayoutParams();
            params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
            container.requestLayout();
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
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
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
     * Setup the UIL to be used in the app.
     */
    private void setupUIL() {
        // TODO config properly.
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(false)
                .cacheInMemory(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
    }
}
