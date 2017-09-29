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
import android.widget.TextView;

import com.dvmatias.previaalpaso.R;
import com.dvmatias.previaalpaso.objects.Promotion;

import java.util.ArrayList;
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
        TextView tvItemPromotionDescription;
        TextView tvItemPromotionPrice;
        ImageView ivItemPromotionShare;
        ImageView ivItemPromotionBuy;

        public PromotionsViewHolder(View itemView) {
            super(itemView);

            cvItemPromotion = (CardView) itemView.findViewById(R.id.cv_item_promotion);
            ivItemPromotionImage = (ImageView) itemView.findViewById(R.id.iv_item_promotion_image);
            tvItemPromotionName = (TextView) itemView.findViewById(R.id.tv_item_promotion_name);
            tvItemPromotionDescription =
                    (TextView) itemView.findViewById(R.id.tv_item_promotion_description);
            tvItemPromotionPrice = (TextView) itemView.findViewById(R.id.tv_item_promotion_price);
            ivItemPromotionShare = (ImageView) itemView.findViewById(R.id.iv_item_promotion_share);
            ivItemPromotionBuy = (ImageView) itemView.findViewById(R.id.iv_item_promotion_buy);
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
        holder.tvItemPromotionDescription.setText(mPromotionArrayList.get(position).getDescription());
        holder.tvItemPromotionPrice.setText(getStringPrice(position));

        holder.cvItemPromotion.setTag(position);
        holder.cvItemPromotion.setOnClickListener(cardViewOnClickListener);

        setCardViewParams(holder, position);
    }

    /**
     * TODO desc.
     * @param position
     */
    private void setCardViewParams(PromotionsViewHolder holder, int position) {
        int pos = position+1;

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        params.topMargin = dpToPx(12);
        if ((pos%2) == 0) {
            params.setMarginStart(dpToPx(CV_MID_MARGIN));
            params.setMarginEnd(dpToPx(CV_MARGIN));
        } else {
            params.setMarginStart(dpToPx(CV_MARGIN));
            params.setMarginEnd(dpToPx(CV_MID_MARGIN));
        }
        if (pos == getItemCount()) {
            params.bottomMargin = dpToPx(12);
        }
        holder.cvItemPromotion.setLayoutParams(params);
    }

    /**
     * TODO (desd)
     * @param dp
     * @return
     */
    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * TODO (desd)
     * @param position
     * @return
     */
    private String getStringPrice(int position) {
        return Objects.toString("$" + mPromotionArrayList.get(position).getPrice() + ",0", "$???");
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
