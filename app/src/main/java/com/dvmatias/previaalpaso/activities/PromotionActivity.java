package com.dvmatias.previaalpaso.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.objects.Promotion;

public class PromotionActivity extends AppCompatActivity {
    /**
     * TAG.
     */
    private static final String TAG = PromotionActivity.class.getSimpleName();
    /**
     * Key. Extra name to send Promotion object to PromotionActivity.
     */
    private static final String KEY_EXTRA_PROMOTION = "promotion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        Promotion promotion = getIntent().getExtras().getParcelable(KEY_EXTRA_PROMOTION);
        Log.d(TAG, "*** PROMOTION: " + promotion.toString());
    }
}
