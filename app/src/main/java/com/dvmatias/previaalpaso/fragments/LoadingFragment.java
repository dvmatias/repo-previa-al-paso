package com.dvmatias.previaalpaso.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.interfaces.ILoading;
import com.dvmatias.previaalpaso.ui.MainLoadingView;

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
     * TODO (desc)
     * @return
     */
    private static LoadingFragment newInstance() {
        LoadingFragment loadingFragment = new LoadingFragment();
        return loadingFragment;
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

    ILoading iLoadingListener = new ILoading() {
        @Override
        public void onLoadingStarted() {
            Log.d(TAG, "*** onLoadingStarted()");
            MainLoadingView.startLoadingAnimation();
        }

        @Override
        public void onLoadingCompleted() {
            Log.d(TAG, "*** onLoadingCompleted()");
        }

        @Override
        public void onLoadingFailed() {
            Log.d(TAG, "*** onLoadingFailed()");
        }
    };

    /**
     * TODO: (desc)
     * @return
     */
    public ILoading getLoadingListener() {
        Log.d(TAG, "*** BBBBBBB");
        return iLoadingListener;
    }




}

