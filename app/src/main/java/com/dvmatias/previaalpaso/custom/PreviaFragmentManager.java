package com.dvmatias.previaalpaso.custom;

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
    private String currentFragmentTag;

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
     * @param containerViewId [int] The container view id.
     * @param fragment        [Fragment] The fragment to add and replace in container.
     * @param tag             [String] The tag of the fragment replaced.
     */
    public void replace(final int containerViewId,
                        final Fragment fragment,
                        final String tag) {
        FragmentTransaction transaction = mPreviaFragmentManager.beginTransaction();
        transaction.replace(containerViewId, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();

        this.currentFragmentTag = tag;

        Log.d(TAG, "replace(): " + toString());
    }

    /**
     * Add fragment. </br>
     *
     * @param containerViewId [int] The container view id.
     * @param fragment        [Fragment] The fragment to add in container.
     * @param tag             [String] The tag of the fragment added.
     */
    public void add(final int containerViewId,
                    final Fragment fragment,
                    final String tag) {
        FragmentTransaction transaction = mPreviaFragmentManager.beginTransaction();
        transaction.add(containerViewId, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();

        this.currentFragmentTag = tag;
    }

    /**
     * TODO (desc)
     * @return
     */
    public String getCurrentFragmentTag () {
        return this.currentFragmentTag;
    }

    /**
     * Default toString() method override. Show the fragment tag replaced. </br>
     * @return
     */
    @Override
    public String toString() {
        return "PreviaFragmentManager{" +
                "mPreviaFragmentManager = " + this.mPreviaFragmentManager +
                ", tag = " + this.currentFragmentTag +
                '}';
    }
}
