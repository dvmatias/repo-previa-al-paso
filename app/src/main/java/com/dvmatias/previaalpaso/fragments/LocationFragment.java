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

public class LocationFragment extends Fragment {
    private static final String TAG = LocationFragment.class.getSimpleName();
    /**
     * Loading Fragment Instance.
     */
    public static final LocationFragment INSTANCE = newInstance();

    /**
     * TODO (desc)
     * @return
     */
    private static LocationFragment newInstance() {
        LocationFragment locationFragment = new LocationFragment();
        return locationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location, container, false);
    }
}
