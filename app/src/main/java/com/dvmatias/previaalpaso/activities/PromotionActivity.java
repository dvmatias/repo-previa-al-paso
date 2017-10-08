package com.dvmatias.previaalpaso.activities;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.objects.Promotion;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.Objects;

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
     * Beer image.
     */
    private ImageView mIvBeers;
    /**
     * Name of the promotion.
     */
    private TextView mTvPromotionName;
    /**
     * Price of the promotion.
     */
    private TextView mTvPromotionPrice;
    /**
     * Type of the promotion.
     */
    private TextView mTvPromotionType;
    /**
     * Rating of the promotion.
     */
    private TextView mTvPromotionRating;
    /**
     * Votes count of the promotion.
     */
    private TextView mTvPromotionVotesCount;
    /**
     * Description of the promotion.
     */
    private TextView mTvPromotionDescription;
    /**
     * Stock condition of the promotion.
     */
    private TextView mTvPromotionStock;
    /**
     * Rating bar.
     */
    private RatingBar mRbPromotion;
    /**
     * Rating bar.
     */
    private FloatingActionButton mFabSendRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        mResources = getApplicationContext().getResources();

        mPromotion = getIntent().getExtras().getParcelable(KEY_EXTRA_PROMOTION);

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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Add margin to toolbar to avoid status bar overlap.
        CollapsingToolbarLayout.LayoutParams toolbarParams =
                (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        toolbarParams.setMargins(0,getStatusBarHeight(),0,0);
        toolbar.setLayoutParams(toolbarParams);

        // Collapsing Toolbar.
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(mPromotion.getName());
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);

        mFlContainerImage = findViewById(R.id.fl_container_image);
        mIvImage = findViewById(R.id.iv_image);
        mIvLogo = findViewById(R.id.iv_logo);
        mIvBeers = findViewById(R.id.iv_beers);
        mProgressBar = findViewById(R.id.pb);

        mTvPromotionName = findViewById(R.id.tv_promotion_name);
        mTvPromotionPrice = findViewById(R.id.tv_promotion_price);
        mTvPromotionType = findViewById(R.id.tv_promotion_type);
        mTvPromotionRating = findViewById(R.id.tv_promotion_rating);
        mTvPromotionVotesCount = findViewById(R.id.tv_promotion_votes_count);
        mTvPromotionDescription = findViewById(R.id.tv_promotion_description);
        mTvPromotionStock = findViewById(R.id.tv_promotion_stock);

        mRbPromotion = findViewById(R.id.rb_promotion);
        mFabSendRating = findViewById(R.id.fab_send_rating);
        mFabSendRating.setEnabled(false);
        mFabSendRating.setBackgroundTintList(ColorStateList.valueOf(
                ContextCompat.getColor(getApplicationContext()
                        , R.color.colorDisableHintText)));
        mFabSendRating.setOnClickListener(fabSendRatingClickListener);

        // Rating bar listener.
        mRbPromotion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                if (rating != 0.0) {
                    mFabSendRating.setEnabled(true);
                    mFabSendRating.setBackgroundTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(getApplicationContext()
                                    , R.color.colorStockTrue)));
                } else {
                    mFabSendRating.setEnabled(false);
                    mFabSendRating.setBackgroundTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(getApplicationContext()
                                    , R.color.colorDisableHintText)));
                }
            }
        });

        setImageDimensions();
        setInfo();
        downloadImage();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Listener to send rating.
     */
    private View.OnClickListener fabSendRatingClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(PromotionActivity.this, "Send Rating!", Toast.LENGTH_SHORT).show();
        }
    };

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

        mIvImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(
                (int) (0.9 * metrics.widthPixels),
                FrameLayout.LayoutParams.MATCH_PARENT);
        imageParams.gravity= Gravity.CENTER_HORIZONTAL;
        imageParams.bottomMargin=dpToPx(3);
        mIvImage.setLayoutParams(imageParams);
    }

    /**
     * Convert px in dp.
     */
    private static int dpToPx(int dp){
        DisplayMetrics metrics = mResources.getDisplayMetrics();
        return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * Set the text information.
     */
    private void setInfo() {
        mTvPromotionName.setText(mPromotion.getName());
        mTvPromotionPrice.setText(getStringPrice(mPromotion.getPrice()));
        mTvPromotionType.setText(mPromotion.getType());
        // TODO
        mTvPromotionRating.setText(Double.toString(mPromotion.getRating()));
        // TODO
        mTvPromotionVotesCount.setText(""+mPromotion.getVotes_count() + " votos");
        mTvPromotionDescription.setText(mPromotion.getDescription());
        // TODO
        mTvPromotionStock.setText((mPromotion.isInStock())
                ?"Esta promoción está disponible en stock"
                :"Esta promoción no se encuentra disponible");
        mTvPromotionStock.setTextColor((mPromotion.isInStock())
                ? ContextCompat.getColor(getApplicationContext(), R.color.colorStockTrue)
                : ContextCompat.getColor(getApplicationContext(), R.color.colorStockFalse));


        for(int cont=0; cont<mPromotion.getProductsNames().size(); cont++) {
            TextView tvProduct = new TextView(getApplicationContext());
            tvProduct.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            tvProduct.setTextColor(ContextCompat.getColor(getApplicationContext()
                    , R.color.colorPrimaryText));
            tvProduct.setLines(1);
            tvProduct.setText(mPromotion.getProductsNames().get(cont));
            ((LinearLayout) findViewById(R.id.ll_container_promotion_products)).addView(tvProduct);
        }
    }

    /**
     * Form the proper text for the price TextView.
     */
    private String getStringPrice(long price) {
        return Objects.toString("$" + price, "$???");
    }

    /**
     * Download promo image. Set image in view. Animate logo and beer.
     */
    private void downloadImage() {
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
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
                mIvImage.setVisibility(View.INVISIBLE);
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

                mIvImage.setVisibility(View.VISIBLE);
                Animation animPromoImage = AnimationUtils.loadAnimation(
                        getApplicationContext(), R.anim.anim_promo_image_fade_in);
                mIvImage.setAnimation(animPromoImage);
                animPromoImage.start();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                // TODO handle.
                mProgressBar.setVisibility(View.GONE);

            }
        });
    }
}
