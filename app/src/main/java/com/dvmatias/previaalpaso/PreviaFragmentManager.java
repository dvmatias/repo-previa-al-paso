package com.dvmatias.previaalpaso;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by dvmatias on 22/09/17.
 */

public class PreviaFragmentManager {
    /**
     * TAG.
     */
    private final static String TAG = PreviaFragmentManager.class.getSimpleName();
    /**
     * Fragment manager.
     */
    private final FragmentManager mPreviaFragmentManager;
    /**
     * Fragment tag.
     */
    private String fragmentTag;

    /**
     * Constructor. </br>
     *
     * @param activity [Activity]
     */
    public PreviaFragmentManager(Activity activity) {
        this.mPreviaFragmentManager = activity.getFragmentManager();
    }

    /**
     * Replace fragment. </br>
     *
     * @param containerViewId the view id
     * @param fragment        the fragment
     * @param tag             the tag
     */
    public synchronized void replace(final int containerViewId,
                                     final Fragment fragment,
                                     final String tag) {
        FragmentTransaction transaction = mPreviaFragmentManager.beginTransaction();
        transaction.replace(containerViewId, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();

        this.fragmentTag = tag;

        Log.d(TAG, "replace(): " + toString());
    }

    /**
     * Default toString() method override. Show the fragment tag replaced. </br>
     * @return
     */
    @Override
    public String toString() {
        return "PreviaFragmentManager{" +
                "mPreviaFragmentManager = " + this.mPreviaFragmentManager +
                ", tag = " + this.fragmentTag +
                '}';
    }
}
