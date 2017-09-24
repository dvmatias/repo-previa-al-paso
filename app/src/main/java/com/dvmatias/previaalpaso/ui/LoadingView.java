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
import android.widget.TextView;

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
     * Context.
     */
    private static Context mContext;
    /**
     * Loading indicator.
     */
    private static FloatingActionButton loadingIndicator;

    public LoadingView(@NonNull Context context) {
        this(context, null);
    }

    public LoadingView(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View rootView = inflate(context, R.layout.view_loading_main, this);
        mContext = context;
        loadingIndicator = (FloatingActionButton)
                rootView.findViewById(R.id.loading_indicator);

        initView(context);
    }
    
    /**
     * Init views.
     */
    private void initView(final Context context) {
        Log.d(TAG, "*** initView() UNAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        loadingIndicator.setClickable(false);
        loadingIndicator.setSize(FloatingActionButton.SIZE_NORMAL);
    }
}
