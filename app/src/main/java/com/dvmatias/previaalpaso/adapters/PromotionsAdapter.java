package com.dvmatias.previaalpaso.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.helpers.FirebaseDatabaseHelper;
import com.dvmatias.previaalpaso.helpers.SharedPreferenceHelper;
import com.dvmatias.previaalpaso.objects.Promotion;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by dvmatias on 25/09/17.
 * Adapter created for the Promotions RecyclerView.
 */

public class PromotionsAdapter extends
        RecyclerView.Adapter<PromotionsAdapter.PromotionsViewHolder> {
    /**
     * TAG.
     */
    @SuppressWarnings("unused")
    private final static String TAG = PromotionsAdapter.class.getSimpleName();
    /**
     * Promotions array list.
     */
    private ArrayList<Promotion> mPromotionArrayList;
    /**
     * Context.
     */
    private Context mContext;

    /**
     * Constructor. </br>
     *
     * @param promotionsArray [ArrayList<Promotion>] list of all promotions in DB.
     */
    public PromotionsAdapter(ArrayList<Promotion> promotionsArray, Context context) {
        this.mPromotionArrayList = promotionsArray;
        this.mContext = context;
    }

    /**
     * ViewHolder class.
     */
    class PromotionsViewHolder  extends RecyclerView.ViewHolder{
        CardView cvItemPromotion;
        ImageView ivItemPromotionImage;
        TextView tvItemPromotionName;
        RatingBar rbItemPromotion;
        TextView tvItemPromotionVotesCount;
        TextView tvItemPromotionDescription;
        TextView tvItemPromotionStock;
        TextView tvItemPromotionPrice;
        ImageView ivItemPromotionLike;
        ImageView ivItemPromotionShare;
        TextView tvItemPromotionType;

        PromotionsViewHolder(View itemView) {
            super(itemView);

            cvItemPromotion = itemView.findViewById(R.id.cv_item_promotion);
            ivItemPromotionImage = itemView.findViewById(R.id.iv_item_promotion_image);
            tvItemPromotionName = itemView.findViewById(R.id.tv_item_promotion_name);
            rbItemPromotion = itemView.findViewById(R.id.rb_item_promotion);
            tvItemPromotionVotesCount =
                    itemView.findViewById(R.id.tv_item_promotion_votes_count);
            tvItemPromotionDescription =
                    itemView.findViewById(R.id.tv_item_promotion_description);
            tvItemPromotionStock = itemView.findViewById(R.id.tv_item_promotion_stock);
            tvItemPromotionPrice = itemView.findViewById(R.id.tv_item_promotion_price);
            ivItemPromotionLike = itemView.findViewById(R.id.iv_item_promotion_like);
            ivItemPromotionShare = itemView.findViewById(R.id.iv_item_promotion_share);
            tvItemPromotionType = itemView.findViewById(R.id.tv_item_promotion_type);
        }
    }

    @Override
    public PromotionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_list_promotion, parent, false);

        return new PromotionsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(PromotionsViewHolder holder, int position) {
        // TODO set image.

        holder.tvItemPromotionName.setText(mPromotionArrayList.get(position).getName());
        holder.rbItemPromotion.setRating((float) mPromotionArrayList.get(position).getRating());
        holder.tvItemPromotionVotesCount.setText(
                String.format(mContext.getResources().getString(
                        R.string.votes_count_place_holder),
                        mPromotionArrayList.get(position).getVotes_count()));
        holder.tvItemPromotionDescription
                .setText(mPromotionArrayList.get(position).getDescription());
        holder.tvItemPromotionPrice.setText(getStringPrice(position));
        holder.tvItemPromotionType.setText(mPromotionArrayList.get(position).getType());

        holder.cvItemPromotion.setTag(position);
        holder.cvItemPromotion.setOnClickListener(cardViewOnClickListener);

        setLikeIcon(holder.ivItemPromotionLike,
                mPromotionArrayList.get(position).getId());
        holder.ivItemPromotionLike.setTag(mPromotionArrayList.get(position).getId());
        holder.ivItemPromotionLike.setOnClickListener(likeOnClickListener);

        holder.ivItemPromotionShare.setOnClickListener(shareOnClickListener);

        setStockTextColor(holder.tvItemPromotionStock, mPromotionArrayList.get(position));
    }

    /**
     * Set green color text if the promo is in stock. Gray otherwise.
     *
     * @param v             [View] Stock TextView.
     * @param promotion     [Promotion] Promotion object to evaluate stock.
     */
    private void setStockTextColor(View v, Promotion promotion) {
        if (promotion.isInStock()) {
            ((TextView) v).setTextColor(
                    ContextCompat.getColor(mContext, R.color.colorStockHighlight));
        } else {
            ((TextView) v).setTextColor(
                    ContextCompat.getColor(mContext, R.color.colorSecondaryText));
        }

    }

    /**
     * Like button on click listener.
     */
    private View.OnClickListener likeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (SharedPreferenceHelper
                                .isAlreadyLikePromotion(mContext, (long)view.getTag())) {
                            SharedPreferenceHelper.unlikePromotion(mContext, (long) view.getTag());
                        } else {
                            SharedPreferenceHelper.likePromotion(mContext, (long) view.getTag());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                }
            }).start();
        }
    };

    /**
     * According it the Promotion is already liked by the user, the "like" icon is set to the
     * proper drawable resource.
     *
     * @param promotionId [long] Id of the promotion.
     */
    private void setLikeIcon(View v, long promotionId) {
        try {
            if (SharedPreferenceHelper.isAlreadyLikePromotion(mContext, promotionId)) {
                ((ImageView) v).setImageResource(R.drawable.ic_like_black_24dp);
            } else {
                ((ImageView) v).setImageResource(R.drawable.ic_like_border_black_24dp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Share button on click listener.
     */
    private View.OnClickListener shareOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, "Share clicked!", Toast.LENGTH_SHORT).show();
            // TODO implemente action
        }
    };

    /**
     * Form the proper text for the price TextView.
     */
    private String getStringPrice(int position) {
        return Objects.toString("$" + mPromotionArrayList.get(position).getPrice(), "$???");
    }

    @Override
    public int getItemCount() {
        return this.mPromotionArrayList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Cardview item click listener. Launch PromotionItemDetailActivity.
     */
    private View.OnClickListener cardViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "*** clicked item = " + view.getTag());
            // TODO: implement action.
        }
    };
}
