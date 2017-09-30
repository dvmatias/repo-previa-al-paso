package com.dvmatias.previaalpaso.adapters;

import android.content.Context;
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
import com.dvmatias.previaalpaso.objects.Promotion;

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
     * Margin start in dp.
     */
    private final static int CV_MARGIN = 20;
    /**
     * Margin start in dp.
     */
    private final static int CV_MID_MARGIN = CV_MARGIN/2;
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
    public class PromotionsViewHolder  extends RecyclerView.ViewHolder{
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

        public PromotionsViewHolder(View itemView) {
            super(itemView);

            cvItemPromotion = (CardView) itemView.findViewById(R.id.cv_item_promotion);
            ivItemPromotionImage = (ImageView) itemView.findViewById(R.id.iv_item_promotion_image);
            tvItemPromotionName = (TextView) itemView.findViewById(R.id.tv_item_promotion_name);
            rbItemPromotion = (RatingBar) itemView.findViewById(R.id.rb_item_promotion);
            tvItemPromotionVotesCount =
                    (TextView) itemView.findViewById(R.id.tv_item_promotion_votes_count);
            tvItemPromotionDescription =
                    (TextView) itemView.findViewById(R.id.tv_item_promotion_description);
            tvItemPromotionStock = (TextView) itemView.findViewById(R.id.tv_item_promotion_stock);
            tvItemPromotionPrice = (TextView) itemView.findViewById(R.id.tv_item_promotion_price);
            ivItemPromotionLike = (ImageView) itemView.findViewById(R.id.iv_item_promotion_like);
            ivItemPromotionShare = (ImageView) itemView.findViewById(R.id.iv_item_promotion_share);
            tvItemPromotionType = (TextView) itemView.findViewById(R.id.tv_item_promotion_type);
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
        holder.tvItemPromotionVotesCount.setText(String.format(
                Locale.getDefault(), "%d", mPromotionArrayList.get(position).getVotes_count()) + " Votos");
        holder.tvItemPromotionDescription
                .setText(mPromotionArrayList.get(position).getDescription());
        holder.tvItemPromotionPrice.setText(getStringPrice(position));
        holder.tvItemPromotionType.setText(mPromotionArrayList.get(position).getType());

        holder.cvItemPromotion.setTag(position);
        holder.cvItemPromotion.setOnClickListener(cardViewOnClickListener);

        // TODO set like proper icon (border or fill)
        holder.ivItemPromotionLike.setOnClickListener(likeOnClickListener);

        holder.ivItemPromotionShare.setOnClickListener(shareOnClickListener);

        // TODO set STOCK right color (TextView)
    }

    /**
     * Like button on click listener.
     */
    private View.OnClickListener likeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, "Like clicked!", Toast.LENGTH_SHORT).show();
            // TODO implemente action
        }
    };

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
     * Transform dp units into px.
     */
    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

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
            
        }
    };
}
