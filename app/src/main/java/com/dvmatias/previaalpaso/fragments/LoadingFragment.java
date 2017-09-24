package com.dvmatias.previaalpaso.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.helpers.FirebaseDatabaseHelper;
import com.dvmatias.previaalpaso.ui.LoadingView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

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
     * TODO desc.
     */
    private static TextView tvRetry;
    /**
     * Loading indicator. FAB.
     */
    private static FloatingActionButton mLoadingIndicator;
    /**
     * Loading view.
     */
    private static LoadingView mLoadingView;
    /**
     * Minimum index icon number inside mArrayIcon (fab icons array).
     */
    private static final int MIN_ICON_INDEX = 0;
    /**
     * Maximum index icon number inside mArrayIcon (fab icons array).
     */
    private static final int MAX_ICON_INDEX = 7;
    /**
     * Offset time to show loading icon before hide-change.
     */
    private static final long OFFSET_TIME_ANIMATION = 1000;
    /**
     * Icon index to display.
     */
    private static int mIconIndex = MIN_ICON_INDEX;
    /**
     * Array of resources index icons for de loading indicator.
     */
    private static ArrayList<Integer> mArrayIcons;
    /**
     *
     */
    private static View rootView;

    /**
     * Create a new instance for {@link LoadingFragment}
     */
    private static LoadingFragment newInstance() {
        Log.d(TAG, "*** UNAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        return new LoadingFragment();
    }

    public static LoadingFragment getInstance() {
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_loading, container, false);

        tvRetry = (TextView) rootView.findViewById(R.id.tv_retry);
        tvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "*** retrtyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
                // TODO download database.
                FirebaseDatabaseHelper.downloadDatabase();
            }
        });

        mLoadingIndicator = (FloatingActionButton) rootView.findViewById(R.id.loading_indicator);
        mLoadingView = (LoadingView) rootView.findViewById(R.id.loading_view);

        setIconsArrayResources();
        mLoadingIndicator.setImageResource(mArrayIcons.get(mIconIndex));

        return rootView;
    }

    /**
     * Add all the loading icons resources id.
     */
    private void setIconsArrayResources() {
        mArrayIcons = new ArrayList<>();
        mArrayIcons.add(android.R.drawable.ic_dialog_map);
        mArrayIcons.add(android.R.drawable.ic_dialog_info);
        mArrayIcons.add(android.R.drawable.ic_dialog_alert);
        mArrayIcons.add(android.R.drawable.ic_dialog_email);
        mArrayIcons.add(android.R.drawable.ic_dialog_dialer);
        mArrayIcons.add(android.R.drawable.ic_dialog_map);
        mArrayIcons.add(android.R.drawable.ic_dialog_info);
        mArrayIcons.add(android.R.drawable.ic_dialog_alert);
        // Shuffle icons inside array.
        Collections.shuffle(mArrayIcons);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * TODO desc
     */
    public void startLoadingAnimation() {
        tvRetry.setVisibility(View.GONE);
        Log.d(TAG, "*** startLoadingAnimation()");
        mLoadingIndicator.clearAnimation();
        mLoadingView.setVisibility(View.VISIBLE);

        final Animation hideFab = AnimationUtils.loadAnimation(getActivity(), R.anim.hide_fab);
        hideFab.setStartOffset(OFFSET_TIME_ANIMATION);
        final Animation showFab = AnimationUtils.loadAnimation(getActivity(), R.anim.show_fab);
        showFab.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLoadingIndicator.startAnimation(hideFab);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        hideFab.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIconIndex++;
                if(mIconIndex > MAX_ICON_INDEX) {
                    mIconIndex = 0;
                }
                mLoadingIndicator.setImageResource(mArrayIcons.get(mIconIndex));
                mLoadingIndicator.startAnimation(showFab);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mLoadingIndicator.startAnimation(showFab);
    }

    /**
     * TODO desc
     */
    public void showRetry() {
        Log.d(TAG, "*** showRetry()");
        mLoadingView.setVisibility(View.GONE);
        stopLoadingAnimation();
        tvRetry.setVisibility(View.VISIBLE);
    }


    /**
     * TODO desc
     */
    public static void stopLoadingAnimation() {
        Log.d(TAG, "*** stopLoadingAnimation()");

        mLoadingIndicator.clearAnimation();
        mLoadingView.setVisibility(View.GONE);
    }

}

