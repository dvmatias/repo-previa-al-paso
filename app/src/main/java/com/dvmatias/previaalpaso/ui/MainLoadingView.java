package com.dvmatias.previaalpaso.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.dvmatias.previaalpaso.R;

/**
 * Created by dvmatias on 17/09/17.
 */

public class MainLoadingView extends FrameLayout {

    public MainLoadingView(@NonNull Context context) {
        this(context, null);
    }

    public MainLoadingView(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // TODO
        initView(context);
    }

    private void initView(Context context) {
        View rootView = inflate(context, R.layout.view_loading_main, this);
    }

//    private void init(Context context) {
//        rootView = inflate(context, R.layout.value_selector, this);
//        valueTextView = (TextView) rootView.findViewById(R.id.valueTextView);
//
//        minusButton = rootView.findViewById(R.id.minusButton);
//        plusButton = rootView.findViewById(R.id.plusButton);
//
//        minusButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                decrementValue(); //we'll define this method later
//            }
//        });
//
//        plusButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                incrementValue(); //we'll define this method later        }
//            });
//        }
}
