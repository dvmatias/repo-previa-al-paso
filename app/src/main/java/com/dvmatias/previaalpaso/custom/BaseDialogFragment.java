package com.dvmatias.previaalpaso.custom;

import android.app.Activity;
import android.support.v4.app.DialogFragment;

/**
 * Created by dvmatias on 11/09/17. Designed to keep hold of the instance of the Activity.
 * So when the Dialog is attached to the Activity, you will know the instance of the
 * Activity which created it.
 */

public abstract class BaseDialogFragment<T> extends DialogFragment {
    /**
     * Activity instance.
     */
    private T mActivityInstance;

    /**
     * Instance getter.
     *
     * @return - [T] Activity instance
     */
    public final T getActivityInstance() {
        return mActivityInstance;
    }

    @Override
    public void onAttach(Activity activity) {
        mActivityInstance = (T) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivityInstance = null;
    }


}