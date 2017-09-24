package com.dvmatias.previaalpaso.helpers;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

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

/**
 * Created by dvmatias on 22/09/17.
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
     *
     */
    private final static long MIN_DOWNLOAD_TASK_DURATION = 3000;
    /**
     *
     */
    private final static long MAX_DOWNLOAD_TASK_DURATION = 5;
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
    public static boolean mIsPromotionsReady;
    /**
     *
     */
    public static boolean mIsProductsReady;

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
     * TODO (desc)
     * @return
     */
    public static boolean isPromotionsAndProductsReady () {
        return (mIsProductsReady && mIsPromotionsReady);
    }

    private static Handler DownloadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO
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
                case MGS_WHAT_DOWNLOAD_COMPLETED:
                    // TODO remove all database listeners.
                    DownloadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loadingListener.onLoadingCompleted();
                        }
                    });
                    break;
                case MSG_WHAT_DOWNLOAD_FAILED:
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
     * TODO
     */
    public static void downloadDatabase() {
        Message msgStartDownload = new Message();
        msgStartDownload.what = MSG_WHAT_START_DOWNLOAD;
        DownloadHandler.dispatchMessage(msgStartDownload);

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    Log.d(TAG, "*** A");
                    Message messageDownloadFailed = new Message();
                    messageDownloadFailed.what = MSG_WHAT_DOWNLOAD_FAILED;
                    DownloadHandler.sendMessage(messageDownloadFailed);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * TODO
     */
    private static void downloadPromotions() {
        Log.d(TAG, "*** downloadPromotions()");
        final long mTaskStartTime = Calendar.getInstance().getTimeInMillis();
        final ArrayList<Promotion> promotionsArray = new ArrayList<>();

        final ChildEventListener childEventListener = mDatabaseReference
                .child(KEY_PROMOTION)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Promotion promotion = new Promotion();

                        promotion.setDescription((String) dataSnapshot
                                .child(KEY_DESCRIPTION).getValue());
                        promotion.setName((String) dataSnapshot.child(KEY_NAME).getValue());
                        promotion.setPrice((long) dataSnapshot.child(KEY_PRICE).getValue());
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
                                    long timeOffset =
                                            Calendar.getInstance().getTimeInMillis() - mTaskStartTime;
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
     * TODO
     */
    private static void downloadProducts() {
        Log.d(TAG, "*** downloadProducts()");
        final long mTaskStartTime = Calendar.getInstance().getTimeInMillis();
        final ArrayList<Product> productsArray = new ArrayList<>();

        mDatabaseReference.child(KEY_PRODUCT).addChildEventListener(new ChildEventListener() {
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
                                Message messageDownloadCompleted = new Message();
                                messageDownloadCompleted.what = MGS_WHAT_DOWNLOAD_COMPLETED;
                                DownloadHandler.sendMessage(messageDownloadCompleted);
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
}
