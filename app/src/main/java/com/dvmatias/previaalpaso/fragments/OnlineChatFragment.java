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

public class OnlineChatFragment extends Fragment {
    private static final String TAG = OnlineChatFragment.class.getSimpleName();
    /**
     * Loading Fragment Instance.
     */
    public static final OnlineChatFragment INSTANCE = newInstance();

    /**
     * TODO (desc)
     * @return
     */
    private static OnlineChatFragment newInstance() {
        OnlineChatFragment onlineChatFragment = new OnlineChatFragment();
        return onlineChatFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_online_chat, container, false);
    }
}
