package com.dvmatias.previaalpaso.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvmatias.previaalpaso.R;

/**
 * Created by dvmatias on 16/09/17.
 */

public class SponsorsFragment extends Fragment {
    private static final String TAG = SponsorsFragment.class.getSimpleName();
    /**
     * Loading Fragment Instance.
     */
    public static final SponsorsFragment INSTANCE = newInstance();

    /**
     * TODO (desc)
     * @return
     */
    private static SponsorsFragment newInstance() {
        SponsorsFragment sponsorsFragment = new SponsorsFragment();
        return sponsorsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sponsor, container, false);
    }
}
