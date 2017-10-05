package com.dvmatias.previaalpaso.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.adapters.PromotionsAdapter;
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
     * Instance getter.
     */
    private static PromotionListFragment newInstance() {
        return new PromotionListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_promotion_list, container, false);

        RecyclerView mRvPromotions = rootView.findViewById(R.id.rv_promotions);

        mRvPromotions.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        PromotionsAdapter adapter =
                new PromotionsAdapter(FirebaseDatabaseHelper.getPromotions(),
                        getActivity().getApplicationContext(),
                        getActivity());
        mRvPromotions.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
