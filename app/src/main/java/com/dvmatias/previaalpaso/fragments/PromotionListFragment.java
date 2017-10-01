package com.dvmatias.previaalpaso.fragments;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
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

public class PromotionListFragment extends Fragment {
    /**
     * TAG.
     */
    public static final String TAG = PromotionListFragment.class.getSimpleName();
    /**
     * Loading Fragment Instance.
     */
    public static final PromotionListFragment INSTANCE = newInstance();
    /**
     * Previa custom fragment manager.
     */
    private static PreviaFragmentManager mPreviaFragmentManager;
    /**
     * Recycler view.
     */
    private static RecyclerView rvPromotions;


    /**
     * TODO (desc)
     * @return
     */
    private static PromotionListFragment newInstance() {
        PromotionListFragment promotionListFragment = new PromotionListFragment();
        return promotionListFragment;
    }

    /**
     * Get {@link PromotionListFragment} instance. </br>
     *
     * @return [PromotionListFragment] Instance.
     */
    private static PromotionListFragment getInstance()  {
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_promotion_list, container, false);

        rvPromotions = (RecyclerView) rootView.findViewById(R.id.rv_promotions);

        rvPromotions.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

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
