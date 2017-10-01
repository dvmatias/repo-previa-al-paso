package com.dvmatias.previaalpaso.helpers;

import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LongSparseArray;
import android.util.Log;
import android.util.SparseArray;

import com.dvmatias.previaalpaso.activities.MainActivity;
import com.dvmatias.previaalpaso.interfaces.IDatabaseDownloadState;
import com.dvmatias.previaalpaso.objects.Product;
import com.dvmatias.previaalpaso.objects.Promotion;
import com.dvmatias.previaalpaso.objects.Sponsor;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by dvmatias on 22/09/17. Firebase Database Helper provides tools to download
 * products and promotions and set the Arrays of those objects to use in the entire app.
 */

public class FirebaseDatabaseHelper {
    /**
     * TAG.
     */
    @SuppressWarnings("unused")
    private final static String TAG = FirebaseDatabaseHelper.class.getSimpleName();
    /**
     * Array of {@link Promotion} objects.
     */
    private static ArrayList<Promotion> mPromotionsArray;
    /**
     * Array of {@link com.dvmatias.previaalpaso.objects.Product} objects.
     */
    private static ArrayList<Product> mProductsArray;
    /**
     * FirebaseDatabase instance.
     */
    private static final FirebaseDatabase mDatabaseInstance = FirebaseDatabase.getInstance();
    /**
     * FirebaseDatabase reference.
     */
    private static final DatabaseReference mDatabaseReference = mDatabaseInstance.getReference();
    /**
     * Loading FirebaseDatabase info state listener.
     */
    private static final IDatabaseDownloadState loadingListener = MainActivity.getLoadingListener();
    /**
     * Key name for the FirebaseDatabase reference.
     */
    private static final String KEY_PROMOTION = "promotion";
    /**
     * Key name for the FirebaseDatabase reference.
     */
    private static final String KEY_PRODUCT = "product";
    /**
     * Key name for the promotion -> description child.
     */
    private static final String KEY_DESCRIPTION = "description";
    /**
     * Key name for the promotion/product -> name child.
     */
    private static final String KEY_NAME = "name";
    /**
     * Key name for the promotion/product -> price child.
     */
    private static final String KEY_PRICE = "price";
    /**
     * Key name for the promotion/product -> rating child.
     */
    private static final String KEY_RATING = "rating";
    /**
     * Key name for the promotion/product -> votes count child.
     */
    private static final String KEY_VOTES_COUNT = "votes_count";
    /**
     * Key name for the promotion -> products_id child.
     */
    private static final String KEY_PRODUCTS_ID = "products_id";
    /**
     * Key name for the promotion -> sponsor child.
     */
    private static final String KEY_SPONSOR = "sponsor";
    /**
     * Key name for the promotion/product -> type child.
     */
    private static final String KEY_TYPE = "type";
    /**
     * Key name for the promotion -> url_img child.
     */
    private static final String KEY_URL_IMAGE = "url_img";
    /**
     * Key name for the product -> brand child.
     */
    private static final String KEY_BRAND = "brand";
    /**
     * Key name for the product -> ontent_ml child.
     */
    private static final String KEY_CONTENT_ML = "content_ml";
    /**
     * Key name for the product -> flavor child.
     */
    private static final String KEY_FLAVOR = "flavor";
    /**
     * Key name for the product -> id child.
     */
    private static final String KEY_ID = "id";
    /**
     * Key name for the product -> stock child.
     */
    private static final String KEY_STOCK = "stock";
    /**
     * Min time of download task duration for booth, products and promotions.
     */
    private final static long MIN_DOWNLOAD_TASK_DURATION = 2500;
    /**
     * Max time of download task duration for booth, products and promotions.
     */
    private final static long MAX_DOWNLOAD_TASK_DURATION = 10000;
    /**
     * Message what value to indicate the handler that the download has been started.
     */
    private final static int MSG_WHAT_START_DOWNLOAD = 324;
    /**
     * Message what value to indicate the handler that the promotions must be downloaded.
     */
    private final static int MSG_WHAT_DOWNLOAD_PROMOTIONS = 515;
    /**
     * Message what value to indicate the handler that the products must be downloaded.
     */
    private final static int MSG_WHAT_DOWNLOAD_PRODUCTS = 723;
    /**
     * Message what value to indicate the handler that the products must be downloaded.
     */
    private final static int MSG_WHAT_SET_STOCK = 762;
    /**
     * Message what value to indicate the handler that the download has been completed.
     */
    private final static int MGS_WHAT_DOWNLOAD_COMPLETED = 351;
    /**
     * Message what value to indicate the handler that the download has failed.
     */
    private final static int MSG_WHAT_DOWNLOAD_FAILED = 514;
    /**
     * Quantity of promotions stored in FirebaseDatabase.
     */
    private static long mPromotionsCount;
    /**
     * Quantity of products stored in FirebaseDatabase.
     */
    private static long mProductsCount;
    /**
     *
     */
    private static boolean mIsPromotionsReady;
    /**
     *
     */
    private static boolean mIsProductsReady;
    /**
     *
     */
    private static ChildEventListener mChildEventListener;
    /**
     *
     */
    private static ValueEventListener mValueEventListener;

    /**
     * Constructor.
     */
    public FirebaseDatabaseHelper() {
        if (mPromotionsArray == null || mPromotionsArray.size() == 0
                || mProductsArray == null || mProductsArray.size() == 0) {
            mPromotionsArray = new ArrayList<>();
            mProductsArray = new ArrayList<>();
            mIsPromotionsReady = false;
            mIsProductsReady = false;
        } else {
            mIsPromotionsReady = true;
            mIsProductsReady = true;
        }
    }

    /**
     * Set the list of promotions.</br>
     * @param promotionsArray [ArrayList\<{@link Promotion><}\>] full list of promotions
     *                        obtained from FirebaseDatabase.
     */
    private static void setPromotions(ArrayList<Promotion> promotionsArray) {
        if (mPromotionsArray == null) {
            mPromotionsArray = new ArrayList<>();
        }
        mPromotionsArray = promotionsArray;
        mIsPromotionsReady = true;
    }

    /**
     * Set the list of products.</br>
     * @param productsArray [ArrayList\<{@link Product><}\>] full list of promotions
     *                        obtained from FirebaseDatabase.
     */
    private static void setProducts(ArrayList<Product> productsArray) {
        if (mProductsArray == null) {
            mProductsArray = new ArrayList<>();
        }
        mProductsArray = productsArray;
        mIsProductsReady = true;
    }

    /**
     * Get the full list of promotions. </br>
     *
     * @return [ArrayList\<{@link Promotion><}\>] full list of promotions.
     */
    public static ArrayList<Promotion> getPromotions() {
        if (mPromotionsArray == null) {
            mPromotionsArray = new ArrayList<>();
        }
        if (mPromotionsArray.size() == 0) {
            return null;
        }
        return mPromotionsArray;
    }

    /**
     * Tells if promotions and products where downloaded successfully. </br>
     *
     * @return [boolean] <b>true</b> if both, products and promotions, where downloaded
     *          successfully, <b>false</b> if not.
     */
    public static boolean isPromotionsAndProductsReady () {
        return (mIsProductsReady && mIsPromotionsReady);
    }

    /**
     * Handler.
     */
    private static Handler DownloadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WHAT_START_DOWNLOAD:
                    loadingListener.onLoadingStarted();
                    break;
                case MSG_WHAT_DOWNLOAD_PROMOTIONS:
                    downloadPromotions();
                    break;
                case MSG_WHAT_DOWNLOAD_PRODUCTS:
                    downloadProducts();
                    break;
                case MSG_WHAT_SET_STOCK:
                    setPromotionsStock();
                    break;
                case MGS_WHAT_DOWNLOAD_COMPLETED:
                    mDatabaseReference.removeEventListener(mChildEventListener);
                    mDatabaseReference.removeEventListener(mValueEventListener);
                    DownloadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loadingListener.onLoadingCompleted();
                        }
                    });
                    break;
                case MSG_WHAT_DOWNLOAD_FAILED:
                    mDatabaseReference.removeEventListener(mChildEventListener);
                    mDatabaseReference.removeEventListener(mValueEventListener);
                    DownloadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loadingListener.onLoadingFailed();
                        }
                    });
                    break;
            }
        }
    };

    /**
     * Start the Promotions download. Also set the counters for item count of products and
     * promotions that exist in database.
     */
    public static void downloadDatabase() {
        Message msgStartDownload = new Message();
        msgStartDownload.what = MSG_WHAT_START_DOWNLOAD;
        DownloadHandler.dispatchMessage(msgStartDownload);

        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    if (snap.getKey().equals(KEY_PROMOTION)) {
                        mPromotionsCount = snap.getChildrenCount();
                    } else if (snap.getKey().equals(KEY_PRODUCT)) {
                        mProductsCount = snap.getChildrenCount();
                    }
                }

                if (mProductsCount != 0 && mPromotionsCount != 0) {
                    Message messageDownloadPromotions = new Message();
                    messageDownloadPromotions.what = MSG_WHAT_DOWNLOAD_PROMOTIONS;
                    DownloadHandler.sendMessage(messageDownloadPromotions);

                } else {
                    Message messageDownloadFailed = new Message();
                    messageDownloadFailed.what = MSG_WHAT_DOWNLOAD_FAILED;
                    DownloadHandler.sendMessage(messageDownloadFailed);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mDatabaseReference.addListenerForSingleValueEvent(mValueEventListener);
    }

    /**
     * Download promotions.
     */
    private static void downloadPromotions() {
        final long mTaskStartTime = Calendar.getInstance().getTimeInMillis();
        final ArrayList<Promotion> promotionsArray = new ArrayList<>();

        mChildEventListener = mDatabaseReference
                .child(KEY_PROMOTION)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Promotion promotion = new Promotion();

                        promotion.setDescription((String) dataSnapshot
                                .child(KEY_DESCRIPTION).getValue());
                        promotion.setName((String) dataSnapshot.child(KEY_NAME).getValue());
                        promotion.setPrice((long) dataSnapshot.child(KEY_PRICE).getValue());
                        promotion.setId((long) dataSnapshot.child(KEY_ID).getValue());
                        promotion.setVotes_count(
                                (long) dataSnapshot.child(KEY_VOTES_COUNT).getValue());
                        promotion.setRating((double) dataSnapshot.child(KEY_RATING).getValue());
                        promotion.setProducts_id((ArrayList<Long>) dataSnapshot
                                .child(KEY_PRODUCTS_ID).getValue());
                        promotion.setSponsor(dataSnapshot
                                .child(KEY_SPONSOR).getValue(Sponsor.class));
                        promotion.setType((String) dataSnapshot.child(KEY_TYPE).getValue());
                        promotion.setUrl_img((String) dataSnapshot.child(KEY_URL_IMAGE).getValue());

                        promotionsArray.add(promotion);

                        if (promotionsArray.size() == mPromotionsCount) {
                            setPromotions(promotionsArray);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    long timeOffset = Calendar
                                            .getInstance().getTimeInMillis() - mTaskStartTime;
                                    if (timeOffset < MIN_DOWNLOAD_TASK_DURATION) {
                                        try {
                                            Thread.sleep(MIN_DOWNLOAD_TASK_DURATION - timeOffset);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    Message messageDownloadProducts = new Message();
                                    messageDownloadProducts.what = MSG_WHAT_DOWNLOAD_PRODUCTS;
                                    DownloadHandler.sendMessage(messageDownloadProducts);
                                }
                            }).start();
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

    /**
     * Download products.
     */
    private static void downloadProducts() {
        final long mTaskStartTime = Calendar.getInstance().getTimeInMillis();
        final ArrayList<Product> productsArray = new ArrayList<>();

        mChildEventListener = mDatabaseReference
                .child(KEY_PRODUCT)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Product product = new Product();

                        product.setBrand((String) dataSnapshot.child(KEY_BRAND).getValue());
                        product.setContentMl((long) dataSnapshot.child(KEY_CONTENT_ML).getValue());
                        product.setFlavor((String) dataSnapshot.child(KEY_FLAVOR).getValue());
                        product.setId((long) dataSnapshot.child(KEY_ID).getValue());
                        product.setPrice((long) dataSnapshot.child(KEY_PRICE).getValue());
                        product.setName((String) dataSnapshot.child(KEY_NAME).getValue());
                        product.setStock((long) dataSnapshot.child(KEY_STOCK).getValue());
                        product.setType((String) dataSnapshot.child(KEY_TYPE).getValue());

                        productsArray.add(product);

                        if (productsArray.size() == mProductsCount) {
                            setProducts(productsArray);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    long timeOffset =
                                            Calendar.getInstance().getTimeInMillis() - mTaskStartTime;
                                    if (timeOffset < MIN_DOWNLOAD_TASK_DURATION) {
                                        try {
                                            Thread.sleep(MIN_DOWNLOAD_TASK_DURATION - timeOffset);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    if (isPromotionsAndProductsReady()) {
                                        Message messageSetStock = new Message();
                                        messageSetStock.what = MSG_WHAT_SET_STOCK;
                                        DownloadHandler.sendMessage(messageSetStock);
                                    } else {
                                        Log.d(TAG, "*** C");
                                        Message msgDownloadFailed = new Message();
                                        msgDownloadFailed.what = MSG_WHAT_DOWNLOAD_FAILED;
                                        DownloadHandler.dispatchMessage(msgDownloadFailed);
                                    }
                                }
                            }).start();
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


    /**
     * If both, promotions and products are ready, set the stock of every promotion object.
     */
    private static void setPromotionsStock() {
        for (Promotion promotion : mPromotionsArray) {
            int occurrences = 0;
            ArrayList<Long> productsId = new ArrayList<>();
            productsId = promotion.getProducts_id();
            Collections.sort(productsId);

            HashMap<Long, Integer> map = new HashMap<>();
            for (int cont=0; cont<productsId.size();) {
                int frequency = Collections.frequency(productsId, productsId.get(cont));
                map.put(productsId.get(cont), frequency);
                cont += frequency;
            }
            int mapSize = map.size();
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                for (Product product : mProductsArray) {
                    long promotionProductId = (long) pair.getKey();
                    int promotionProductFrequency = (int) pair.getValue();
                    long productId = product.getId();
                    int productStock = (int) product.getStock();

                    if (promotionProductId == productId
                            && promotionProductFrequency <= productStock) {
                        occurrences++;
                    }
                }
                it.remove(); // avoids a ConcurrentModificationException
            }

            promotion.setInStock(occurrences == mapSize);
        }

        Message messageDownloadCompleted = new Message();
        messageDownloadCompleted.what = MGS_WHAT_DOWNLOAD_COMPLETED;
        DownloadHandler.sendMessage(messageDownloadCompleted);
    }
}
