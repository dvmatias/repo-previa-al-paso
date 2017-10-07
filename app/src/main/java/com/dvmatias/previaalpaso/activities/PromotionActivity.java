package com.dvmatias.previaalpaso.activities;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.objects.Promotion;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class PromotionActivity extends AppCompatActivity {
    /**
     * TAG.
     */
    @SuppressWarnings("unused")
    private static final String TAG = PromotionActivity.class.getSimpleName();
    /**
     * Time delay before load the image.
     */
    private static final int FADE_IN_DELAY = 10000;
    /**
     * Key. Extra name to send Promotion object to PromotionActivity.
     */
    private static final String KEY_EXTRA_PROMOTION = "promotion";
    /**
     * Promotion object.
     */
    private static Promotion mPromotion;
    /**
     * Resources.
     */
    private static Resources mResources;
    /**
     * Frame that contains image.
     */
    private FrameLayout mFlContainerImage;
    /**
     * Loading image progress bar.
     */
    private ProgressBar mProgressBar;
    /**
     * Promotion image.
     */
    private ImageView mIvImage;
    /**
     * Logo image.
     */
    private ImageView mIvLogo;
    /**
     * Berr image.
     */
    private ImageView mIvBeers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        mResources = getApplicationContext().getResources();

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        // ActionBar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // Add margin to toolbar to avoid status bar overlap.
        CollapsingToolbarLayout.LayoutParams toolbarParams =
                (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        toolbarParams.setMargins(0,getStatusBarHeight(),0,0);
        toolbar.setLayoutParams(toolbarParams);

        // Collapsing Toolbar.
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

        mPromotion = getIntent().getExtras().getParcelable(KEY_EXTRA_PROMOTION);

        mFlContainerImage = findViewById(R.id.fl_container_image);
        mIvImage = findViewById(R.id.iv_image);
        mIvLogo = findViewById(R.id.iv_logo);
        mIvBeers = findViewById(R.id.iv_beers);
        mProgressBar = findViewById(R.id.pb);


        setImageDimensions();
        downloadImage();
    }

    /**
     * Set window flaggs to get transluscen status bar.
     */
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * Calculate the status bar height.
     *
     * @return [int] Status bar height.
     */
    private static int getStatusBarHeight() {
        int result = 0;
        int resourceId = mResources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mResources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * Set the container dimensions.
     */
    private void setImageDimensions() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mFlContainerImage.setLayoutParams(new CollapsingToolbarLayout.LayoutParams(
                CollapsingToolbarLayout.LayoutParams.MATCH_PARENT,
                (int) (0.6 * metrics.widthPixels)));
    }

    /**
     * Download promo image. Set image in view. Animate logo and beer.
     */
    private void downloadImage() {
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(false)
                .cacheInMemory(false)
                .displayer(new FadeInBitmapDisplayer(FADE_IN_DELAY))
                .showImageForEmptyUri(null)
                .showImageOnFail(null)
                .showImageOnLoading(null)
                .build();

        //download and display image from url
        imageLoader.loadImage(mPromotion.getUrl_img(), options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                // TODO handle.
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mProgressBar.setVisibility(View.GONE);
                mIvImage.setImageBitmap(loadedImage);

                mIvLogo.setVisibility(View.VISIBLE);
                Animation animLogo = AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.anim_logo_in);
                mIvLogo.setAnimation(animLogo);
                animLogo.start();

                mIvBeers.setVisibility(View.VISIBLE);
                Animation animBeers = AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.anim_beers_in);
                mIvBeers.setAnimation(animBeers);
                animBeers.start();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                // TODO handle.
                mProgressBar.setVisibility(View.GONE);

            }
        });
    }
}
