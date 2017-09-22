package com.dvmatias.previaalpaso.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import com.dvmatias.previaalpaso.custom.PreviaFragmentManager;
import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.custom.CustomTypefaceSpan;
import com.dvmatias.previaalpaso.fragments.LoadingFragment;
import com.dvmatias.previaalpaso.fragments.LocationFragment;
import com.dvmatias.previaalpaso.fragments.OnlineChatFragment;
import com.dvmatias.previaalpaso.fragments.PromotionsFragment;
import com.dvmatias.previaalpaso.fragments.SponsorsFragment;
import com.dvmatias.previaalpaso.interfaces.ILoading;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /**
     *
     */
    @SuppressWarnings("unused")
    private final String TAG = getClass().getSimpleName();
    /**
     *
     */
    private DrawerLayout mDrawerLayout;
    /**
     *
     */
    private NavigationView mNavigationView;
    /**
     *
     */
    static ActionBarDrawerToggle mToggle;
    /**
     * Loading FirebaseDatabase info state listener.
     */
    private static final ILoading loadingListener = LoadingFragment.INSTANCE.getLoadingListener();
    /**
     * Previa custom fragment manager.
     */
    private PreviaFragmentManager mPreviaFragmentManager;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        // Drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();

        // Navigation
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.getMenu().getItem(0).setChecked(true);
        setNavigationMenuItemsFonts();

        // Instantiate FragmentManager and add PromotionsFragment.
        mPreviaFragmentManager = new PreviaFragmentManager(this);
        mPreviaFragmentManager.replace(R.id.container_main,
                PromotionsFragment.INSTANCE,
                PromotionsFragment.TAG);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Toast.makeText(getApplicationContext(),
                item.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();

        if (id == R.id.nav_item_promotions) {
            mPreviaFragmentManager.replace(R.id.container_main,
                    PromotionsFragment.INSTANCE,
                    PromotionsFragment.TAG);
        } else if (id == R.id.nav_item_online_chat) {
            mPreviaFragmentManager.replace(R.id.container_main,
                    OnlineChatFragment.INSTANCE,
                    OnlineChatFragment.TAG);
        } else if (id == R.id.nav_item_sponsors) {
            mPreviaFragmentManager.replace(R.id.container_main,
                    SponsorsFragment.INSTANCE,
                    SponsorsFragment.TAG);
        } else if (id == R.id.nav_item_location) {
            mPreviaFragmentManager.replace(R.id.container_main,
                    LocationFragment.INSTANCE,
                    LocationFragment.TAG);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
        Menu m = mNavigationView.getMenu();
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
}
