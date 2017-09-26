package com.dvmatias.previaalpaso.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.adapters.PromotionsAdapter;
import com.dvmatias.previaalpaso.custom.PreviaFragmentManager;
import com.dvmatias.previaalpaso.helpers.FirebaseDatabaseHelper;

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
     * FAB.
     */
    private static FloatingActionButton fab;
    /**
     * Recycler view.
     */
    private static RecyclerView rvPromotions;


    /**
     * TODO (desc)
     * @return
     */
    private static PromotionsFragment newInstance() {
        PromotionsFragment promotionsFragment = new PromotionsFragment();
        return promotionsFragment;
    }

    /**
     * Get {@link PromotionsFragment} instance. </br>
     *
     * @return [PromotionsFragment] Instance.
     */
    private static PromotionsFragment getInstance()  {
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_promotions, container, false);

        // Floating button
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        rvPromotions = (RecyclerView) rootView.findViewById(R.id.rv_promotions);

        rvPromotions.setLayoutManager(
                new GridLayoutManager(getActivity().getApplicationContext(), 2));

        PromotionsAdapter adapter =
                new PromotionsAdapter(FirebaseDatabaseHelper.getPromotions()
                        , getActivity().getApplicationContext());
        rvPromotions.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
