package com.dvmatias.previaalpaso.custom;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dvmatias.previaalpaso.R;

/**
 * Created by dvmatias on 12/09/17. Class
 */

public class GeneralDialogFragment extends BaseDialogFragment<GeneralDialogFragment.OnDialogFragmentClickListener> {
    /**
     * TAG.
     */
    private final String TAG = getClass().getSimpleName();

    private CardView cvDialog;
    private TextView tvDialogTitle;
    private TextView tvDialogMessage;

    private static final String ERROR_INIT_CONFIG_WITH_NULL = "GeneralDialogFragment configuration can not be initialized with null";
    private static final String LOG_INIT_CONFIG = "Initialize GeneralDialogFragment with configuration";
    static final String WARNING_RE_INIT_CONFIG = "Try to initialize GeneralDialogFragment which had already been initialized before. " + "To re-init GeneralDialogFragment with new configuration call GeneralDialogFragment.destroy() at first.";


    /**
     * TODO
     */
    public static final String BUTTON_TYPE_NEUTRAL = "button_type_neutral";
    /**
     * TODO
     */
    public static final String BUTTON_TYPE_OK = "button_type_ok";
    /**
     * TODO
     */
    public static final String BUTTON_TYPE_CANCEL = "button_type_cancel";
    /**
     * TODO
     */
    public static final String BUTTON_TYPE_OK_CANCEL = "button_type_ok_cancel";

    /**
     * Context.
     */
    private static Context mContext;
    /**
     *
     */
    private static GeneralDialogFragmentConfiguration mGeneralDialogFragmentCofiguration;


    /**
     * Interface to handle the dialog click back to the Activity
     */
    public interface OnDialogFragmentClickListener {
        public void onOkClicked(GeneralDialogFragment dialog);
        public void onCancelClicked(GeneralDialogFragment dialog);
        public void onNeutralClicked(GeneralDialogFragment dialog);
    }

    /**
     * Create an instance of the Dialog with the input.
     *
     * @return - [GeneralDialogFragment]
     */
    public static GeneralDialogFragment newInstance() {
        GeneralDialogFragment frag = new GeneralDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    public void init(Context context) {
        this.mGeneralDialogFragmentCofiguration =
                GeneralDialogFragmentConfiguration.createDefault(context);
        // TODO: apply default.
    }

    /**
     * Init with custom config.
     * @param generalDialogFragmentConfiguration
     */
    public void init(GeneralDialogFragmentConfiguration generalDialogFragmentConfiguration) {
        Log.d(TAG, "*** A");
        if (generalDialogFragmentConfiguration == null) {
            throw new IllegalArgumentException(ERROR_INIT_CONFIG_WITH_NULL);
        }
        if (mGeneralDialogFragmentCofiguration == null) {
            Log.d(TAG, LOG_INIT_CONFIG);
            mGeneralDialogFragmentCofiguration = generalDialogFragmentConfiguration;
        } else {
            Log.w(TAG, WARNING_RE_INIT_CONFIG);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "*** B");
        View rootView = inflater.inflate(R.layout.dialog_layout, container, false);
        if (rootView != null) {
            cvDialog = rootView.findViewById(R.id.cv_dialog);
            tvDialogTitle = rootView.findViewById(R.id.tv_dialog_title);
            tvDialogMessage = rootView.findViewById(R.id.tv_dialog_msg);


            applyConfiguration();
        }
        return rootView;
    }

    private void applyConfiguration() {
        setDialogDimens();
        tvDialogTitle.setText(mGeneralDialogFragmentCofiguration.title);
        tvDialogMessage.setText(mGeneralDialogFragmentCofiguration.message);
    }

    private void setDialogDimens() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getActivityInstance())
                .getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(
                        (int)(screenWidth * mGeneralDialogFragmentCofiguration.width),
                        (int)(screenHeight* mGeneralDialogFragmentCofiguration.height));
        cvDialog.setLayoutParams(params);


    }

    // TODO: make destroy method
    public void destroy() {

    }
}