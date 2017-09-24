package com.dvmatias.previaalpaso.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.custom.PreviaFragmentManager;

/**
 * Created by dvmatias on 16/09/17. Fragment designed to show the full promotions
 * list.
 */

public class PromotionsFragment extends Fragment {
    /**
     * TAG.
     */
    public static final String TAG = PromotionsFragment.class.getSimpleName();
    /**
     * Loading Fragment Instance.
     */
    public static final PromotionsFragment INSTANCE = newInstance();
    /**
     * Previa custom fragment manager.
     */
    private static PreviaFragmentManager mPreviaFragmentManager;


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
        View rootView = inflater.inflate(R.layout.fragment_promotions, container, false);

        // Floating button
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
