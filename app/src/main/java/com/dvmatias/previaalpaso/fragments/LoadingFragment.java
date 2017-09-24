package com.dvmatias.previaalpaso.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvmatias.previaalpaso.R;

/**
 * Created by dvmatias on 17/09/17.
 */

public class LoadingFragment extends Fragment {
    /**
     * TAG.
     */
    public static final String TAG = LoadingFragment.class.getSimpleName();
    /**
     * Loading Fragment Instance.
     */
    public static final LoadingFragment INSTANCE = newInstance();

    /**
     * Create a new instance for {@link LoadingFragment}
     */
    private static LoadingFragment newInstance() {
        return new LoadingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

