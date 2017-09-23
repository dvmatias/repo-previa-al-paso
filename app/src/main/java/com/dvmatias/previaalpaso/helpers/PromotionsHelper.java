package com.dvmatias.previaalpaso.helpers;

import android.util.Log;

import com.dvmatias.previaalpaso.fragments.LoadingFragment;
import com.dvmatias.previaalpaso.interfaces.ILoading;
import com.dvmatias.previaalpaso.objects.Promotion;
import com.dvmatias.previaalpaso.objects.Sponsor;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by dvmatias on 22/09/17.
 */

public class PromotionsHelper {
    /**
     * TAG.
     */
    @SuppressWarnings("unused")
    private final static String TAG = PromotionsHelper.class.getSimpleName();
    /**
     * Array of {@link Promotion} objects.
     */
    private static ArrayList<Promotion> mPromotionsArray;
    /**
     * Loading FirebaseDatabase info state listener.
     */
    private static final ILoading loadingListener = LoadingFragment.INSTANCE.getLoadingListener();
    /**
     * Key name for the FirebaseDatabase reference.
     */
    private static final String KEY_PROMOTION = "promotion";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_PRODUCTS_ID = "products_id";
    private static final String KEY_SPONSOR = "sponsor";
    private static final String KEY_TYPE = "type";
    private static final String KEY_URL_IMAGE = "url_img";
    /**
     * Quantity of promotions stored in FirebaseDatabase.
     */
    private static long mPromotionsCount;
    /**
     *
     */
    public static boolean mPromotionsReady;

    /**
     * Constructor.
     */
    public PromotionsHelper() {
        if (mPromotionsArray == null || mPromotionsArray.size() == 0) {
            mPromotionsArray = new ArrayList<>();
            mPromotionsReady = false;
        } else {
            mPromotionsReady = true;
        }
    }

    /**
     * Set the list of promotions.</br>
     * @param promotionsArray [ArrayList\<{@link Promotion><}\>] full list of promotions
     *                        obtained from FirebaseDatabase.
     */
    public static void setPromotions(ArrayList<Promotion> promotionsArray) {
        mPromotionsReady = false;
        if (mPromotionsArray == null) {
            mPromotionsArray = new ArrayList<>();
        }
        mPromotionsArray = promotionsArray;
        if (mPromotionsArray != null && mPromotionsArray.size() != 0) {
            mPromotionsReady = true;
        }
    }

    /**
     * Get the full list of promotions. </br>
     *
     * @return [ArrayList\<{@link Promotion><}\>] full list of promotions.
     */
    public static ArrayList<Promotion> getPromotions() {
        mPromotionsReady = false;
        if (mPromotionsArray == null) {
            mPromotionsArray = new ArrayList<>();
        }
        if (mPromotionsArray.size() == 0) {
            return null;
        }
        mPromotionsReady = true;
        return mPromotionsArray;
    }

    /**
     * TODO (desc)
     * @return
     */
    public static boolean isPromotionsReady () {
        return mPromotionsReady;
    }

    /**
     *
     */
    public static void downloadPromotions() {
        mPromotionsReady = false;
        loadingListener.onLoadingStarted();

        final ArrayList<Promotion> promotionsArray = new ArrayList<>();

        FirebaseDatabase mDatabaseInstance = FirebaseDatabase.getInstance();
        final DatabaseReference mDatabaseReference = mDatabaseInstance.getReference();
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    if (snap.getKey().equals(KEY_PROMOTION)) {
                        mPromotionsCount = snap.getChildrenCount();

                        Log.d(TAG, "**** COUNT:" + mPromotionsCount);
                    }
                }

                if (mPromotionsCount != 0) {
                    mDatabaseReference.child(KEY_PROMOTION).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Promotion promotion = new Promotion();

                            promotion.setDescription((String) dataSnapshot.child(KEY_DESCRIPTION).getValue());
                            promotion.setName((String) dataSnapshot.child(KEY_NAME).getValue());
                            promotion.setPrice((long) dataSnapshot.child(KEY_PRICE).getValue());
                            promotion.setProducts_id((ArrayList<Long>) dataSnapshot.child(KEY_PRODUCTS_ID).getValue());
                            promotion.setSponsor(dataSnapshot.child(KEY_SPONSOR).getValue(Sponsor.class));
                            promotion.setType((String) dataSnapshot.child(KEY_TYPE).getValue());
                            promotion.setUrl_img((String) dataSnapshot.child(KEY_URL_IMAGE).getValue());

                            Log.d(TAG, "**** PROMOTION ACQUIRED:" + promotion.toString());

                            promotionsArray.add(promotion);

                            if (promotionsArray.size() == mPromotionsCount) {
                                Log.d(TAG, "*** all promotions stored in array");
                                loadingListener.onLoadingCompleted();
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        mDatabaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Promotion promotion = new Promotion();
//
//                promotion.setName((String) dataSnapshot.child("name").getValue());
//                promotion.setType((String) dataSnapshot.child("type").getValue());
//                promotion.setPrice((long) dataSnapshot.child("price").getValue());
//                promotion.setSponsor(dataSnapshot.child("sponsor").getValue(Sponsor.class));
//
//                Log.d(TAG, "**** PROMOTION ACQUIRED:" + promotion.toString());
//
//                promotionsArray.add(promotion);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d(TAG, "*** onCancelled()");
//            }
//
//        });
    }
}
