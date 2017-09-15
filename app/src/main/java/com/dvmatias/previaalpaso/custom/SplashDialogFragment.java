package com.dvmatias.previaalpaso.custom;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dvmatias.previaalpaso.R;

/**
 * Created by dvmatias on 12/09/17. Class
 */

public class SplashDialogFragment extends BaseDialogFragment<SplashDialogFragment.OnDialogFragmentClickListener> {
    /**
     * TAG.
     */
    private final String TAG = getClass().getSimpleName();

    private CardView cvDialog;
    private TextView tvDialogTitle;
    private TextView tvDialogMessage;
    private Button btnDialogNeutal;

    /**
     * interface to handle the dialog click back to the Activity
     */
    public interface OnDialogFragmentClickListener {
        public void onNeutralClicked(SplashDialogFragment dialog);
    }

    // Create an instance of the Dialog with the input
    public static SplashDialogFragment newInstance(String title, String message) {
        SplashDialogFragment frag = new SplashDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("msg", message);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_layout, container, false);
        if (rootView != null) {
            cvDialog = rootView.findViewById(R.id.cv_dialog);
            tvDialogTitle = rootView.findViewById(R.id.tv_dialog_title);
            tvDialogMessage = rootView.findViewById(R.id.tv_dialog_msg);
            btnDialogNeutal = rootView.findViewById(R.id.btn_dialog_neutral);

            setViews();
        }
        return rootView;
    }

    private void setViews() {
        tvDialogTitle.setText(getArguments().getString("title"));
        tvDialogMessage.setText(getArguments().getString("msg"));
        btnDialogNeutal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivityInstance().onNeutralClicked(SplashDialogFragment.this);
            }
        });

    }

}