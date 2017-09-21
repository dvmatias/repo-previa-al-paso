package com.dvmatias.previaalpaso.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import com.dvmatias.previaalpaso.Products;
import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.custom.CustomTypefaceSpan;
import com.dvmatias.previaalpaso.fragments.LoadingFragment;
import com.dvmatias.previaalpaso.fragments.LocationFragment;
import com.dvmatias.previaalpaso.fragments.OnlineChatFragment;
import com.dvmatias.previaalpaso.fragments.PromotionsFragment;
import com.dvmatias.previaalpaso.fragments.SponsorsFragment;
import com.dvmatias.previaalpaso.interfaces.ILoading;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /**
     *
     */
    private final String TAG = getClass().getSimpleName();
    /**
     *
     */
    private static DrawerLayout mDrawerLayout;
    /**
     *
     */
    private static NavigationView navigationView;
    /**
     *
     */
    private static ActionBarDrawerToggle mToggle;
    /**
     * TODO: (desc)
     */
    private static final ILoading loadingListener = LoadingFragment.INSTANCE.getLoadingListener();
    /**
     * Database instance.
     */
    private static FirebaseDatabase mDatabaseInstance;
    /**
     * Database reference.
     */
    private static DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        // Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();

        // Navigation
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setNavigationMenuItemsFonts();

        addFragment(LoadingFragment.INSTANCE);


        mDatabaseInstance = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabaseInstance.getReference("product");
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "*** " +
                        dataSnapshot.getValue(Products.class).toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "*** " +
                        dataSnapshot.getValue(Products.class).toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        MainAsyncTask mainAsyncTask = new MainAsyncTask();
        mainAsyncTask.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            Intent i = new Intent(MainActivity.this, RegisterLoginActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Toast.makeText(getApplicationContext(),
                item.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();

        if (id == R.id.nav_item_promotions) {
            addFragment(PromotionsFragment.INSTANCE);
        } else if (id == R.id.nav_item_online_chat) {
            addFragment(OnlineChatFragment.INSTANCE);
        } else if (id == R.id.nav_item_sponsors) {
            addFragment(SponsorsFragment.INSTANCE);
        } else if (id == R.id.nav_item_location) {
            addFragment(LocationFragment.INSTANCE);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * TODO: (desc)
     */
    private void applyFontToMenuItem(MenuItem menuItem) {
        Typeface font = Typeface.createFromAsset(getAssets(), "roboto_regular.ttf");
        SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        menuItem.setTitle(mNewTitle);
    }

    /**
     * Login button on click listener. </br>
     *
     * @param v [View] Button view
     */
    public void login(View v) {
        Toast.makeText(getApplicationContext(),
                v.getTag() + " clicked!", Toast.LENGTH_SHORT).show();

        // TODO: implement action.

        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * Set fonts for the navigation items.
     */
    private void setNavigationMenuItemsFonts() {
        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
    }

    /**
     * TODO: (desc)
     * @param fragment
     */
    private void addFragment(android.support.v4.app.Fragment fragment) {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container_main, fragment); // newInstance() is a static factory method.
        transaction.commit();

        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private static class MainAsyncTask extends AsyncTask<Void, Void, Void> {
        /**
         * TAG.
         */
        private final static String TAG = MainAsyncTask.class.getSimpleName();
        /**
         * Task start time in ms.
         */
        private static long mStartTaskTimeMs;
        /**
         * Minimum time task duration in ms.
         */
        private final static long MIN_TIME_TASK_DURATION_MS = 5000;
        /**
         * Remote database reached and downloaded successfully.
         */
        private static boolean mRemoteDatabaseReady;
        /**
         * Local database reached and downloaded successfully.
         */
        private static boolean mLocalDatabaseReady;

        @Override
        protected void onPreExecute() {
            mRemoteDatabaseReady = false;
            mLocalDatabaseReady = false;
            mStartTaskTimeMs = Calendar.getInstance().getTimeInMillis();
            loadingListener.onLoadingStarted();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // TODO verify db device existance
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }

}
