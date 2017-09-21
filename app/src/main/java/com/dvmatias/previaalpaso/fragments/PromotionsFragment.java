package com.dvmatias.previaalpaso.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvmatias.previaalpaso.R;

/**
 * Created by dvmatias on 16/09/17.
 */

public class PromotionsFragment extends Fragment {
    private static final String TAG = PromotionsFragment.class.getSimpleName();
    /**
     * Loading Fragment Instance.
     */
    public static final PromotionsFragment INSTANCE = newInstance();

    /**
     * TODO (desc)
     * @return
     */
    private static PromotionsFragment newInstance() {
        PromotionsFragment promotionsFragment = new PromotionsFragment();
        return promotionsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_promotions, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Floating button
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
