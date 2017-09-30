package com.dvmatias.previaalpaso.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by dvmatias on 30/09/17. SharedPreferences helper. Class designed to administrate
 * SharedPreferences.
 */

public class SharedPreferenceHelper {
    /**
     * TAG.
     */
    @SuppressWarnings("unused")
    private final static String TAG = SharedPreferenceHelper.class.getSimpleName();
    /**
     * SP Name - Like shared preference name.
     */
    private final static String SP_LIKE = "sp_like";
    /**
     * SP Key - Like preference key.
     */
    private final static String KEY_LIKE = "key_like";
    /**
     * SP Default value - Like preference default value.
     */
    private final static String DEFAULT_VALUE_LIKE = null;

    /**
     * Set the promotion has liked. This state is stored in SharedPreferences.
     *
     * @param context [Context]
     * @param promotionId [long] Id of the promotion liked.
     */
    public static void likePromotion(Context context, long promotionId) throws JSONException {
        SharedPreferences likePrefs =
                context.getSharedPreferences(SP_LIKE, Context.MODE_PRIVATE);
        String jsonLikeString = likePrefs.getString(KEY_LIKE, DEFAULT_VALUE_LIKE);

        JSONArray jsonLikeArray;

        if (TextUtils.isEmpty(jsonLikeString)) {
            jsonLikeArray= new JSONArray();
            jsonLikeArray.put(promotionId);
        } else {
            jsonLikeArray = new JSONArray(jsonLikeString);
            jsonLikeArray.put(promotionId);

        }

        SharedPreferences.Editor editor = likePrefs.edit();
        editor.putString(KEY_LIKE, jsonLikeArray.toString());
        editor.apply();
    }

    /**
     * Set the promotion has not liked. This state is stored in SharedPreferences.
     *
     * @param context [Context]
     * @param promotionId [long] Id of the promotion liked.
     */
    public static void unlikePromotion(Context context, long promotionId) throws JSONException {
        SharedPreferences likePrefs =
                context.getSharedPreferences(SP_LIKE, Context.MODE_PRIVATE);
        String jsonLikeString = likePrefs.getString(KEY_LIKE, DEFAULT_VALUE_LIKE);

        JSONArray jsonLikeArray = new JSONArray(jsonLikeString);
        for (int cont=0; cont < jsonLikeArray.length(); cont++) {
            if (promotionId == jsonLikeArray.getLong(cont)) {
                jsonLikeArray.remove(cont);
                break;
            }
        }

        SharedPreferences.Editor editor = likePrefs.edit();
        editor.putString(KEY_LIKE, jsonLikeArray.toString());
        editor.apply();
    }

    /**
     * Check if the promotions has been set has liked by the user.
     *
     * @param context [Context]
     * @param promotionId [long] Id of the promotion liked.
     * @return [Boolean] "true" is the Promotion is set has liked. False otherwise.
     */
    public static boolean isAlreadyLikePromotion(
            Context context, long promotionId) throws JSONException {
        SharedPreferences likePrefs =
                context.getSharedPreferences(SP_LIKE, Context.MODE_PRIVATE);
        String jsonLikeString = likePrefs.getString(KEY_LIKE, DEFAULT_VALUE_LIKE);

        if (!TextUtils.isEmpty(jsonLikeString)) {
            JSONArray jsonArray = new JSONArray(jsonLikeString);
            for (int cont=0; cont<jsonArray.length(); cont++) {
                if (promotionId == jsonArray.getLong(cont)) {
                    return true;
                }
            }
        }
        return false;
    }

}
