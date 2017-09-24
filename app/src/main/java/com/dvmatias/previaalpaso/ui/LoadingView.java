package com.dvmatias.previaalpaso.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.dvmatias.previaalpaso.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by dvmatias on 17/09/17
 */

public class LoadingView extends FrameLayout {
    /**
     * TAG.
     */
    private final static String TAG = LoadingView.class.getSimpleName();
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
     * Context.
     */
    private static Context mContext;
    /**
     * Loading indicator.
     */
    private static FloatingActionButton loadingIndicator;
    /**
     * Array of resources index icons for de loading indicator.
     */
    private static ArrayList<Integer> mArrayIcons;
    /**
     * Icon index to display.
     */
    private static int mIconIndex = MIN_ICON_INDEX;

    public LoadingView(@NonNull Context context) {
        this(context, null);
    }

    public LoadingView(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View rootView = inflate(context, R.layout.view_loading_main, this);
        Log.d(TAG, "*** ZZZZZZZZZZZ");
        mContext = context;
        loadingIndicator = (FloatingActionButton)
                rootView.findViewById(R.id.loading_indicator);

        setIconsArrayResources();
        initView(context);
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
    
    /**
     * Init views.
     */
    private void initView(final Context context) {
        loadingIndicator.setVisibility(View.INVISIBLE);
        loadingIndicator.setClickable(false);
        loadingIndicator.setSize(FloatingActionButton.SIZE_NORMAL);
        loadingIndicator.setImageResource(mArrayIcons.get(mIconIndex));
    }

    /**
     * Start endless loading animation.
     */
    public static void startLoadingAnimation() {
        loadingIndicator.setVisibility(View.VISIBLE);
        final Animation hideFab = AnimationUtils.loadAnimation(mContext, R.anim.hide_fab);
        hideFab.setStartOffset(OFFSET_TIME_ANIMATION);
        final Animation showFab = AnimationUtils.loadAnimation(mContext, R.anim.show_fab);
        showFab.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loadingIndicator.startAnimation(hideFab);
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
                loadingIndicator.setImageResource(mArrayIcons.get(mIconIndex));
                loadingIndicator.startAnimation(showFab);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        loadingIndicator.startAnimation(showFab);
    }
}
