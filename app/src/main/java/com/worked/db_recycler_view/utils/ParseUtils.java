package com.worked.db_recycler_view.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;

import com.worked.db_recycler_view.view_model.CardModel;
import com.worked.db_recycler_view.view_model_controller.CardList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Utils : Parse Json
 */
public class ParseUtils {
    private static final String TAG = ParseUtils.class.getSimpleName();

    /**
     * Parse Json
     */
    public static void parseJsonData(Context context, String json) {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context, json));

            JSONObject intro_screen = obj.getJSONObject("card_screen");

            JSONArray cards = intro_screen.getJSONArray("cards");

            CardList cardList = CardList.getInstance();

            for (int i = 0; i < cards.length(); i++) {
                // card data
                CardModel cardModel = new CardModel();

                JSONObject card = cards.optJSONObject(i).optJSONObject("card");

                cardModel.setId(card.optString("id"));

                cardModel.setIndex(card.optInt("index"));

                // card views
                JSONObject views = card.optJSONObject("views");

                if (views != null) {
                    cardModel.setTitle(views.optString("title"));

                    cardModel.setSubtitle(views.optString("subtitle"));
                }

                // card alert
                JSONObject alert = card.optJSONObject("alert");

                if (alert != null) {
                    cardModel.setAlertEnabled(alert.optBoolean("alert_enabled"));

                    cardModel.setAlertTitle(alert.optString("alert_title"));

                    cardModel.setAlertIcon(findDrawableByName(context, alert.optString("alert_icon")));
                }

                // card summary
                JSONObject summary = card.optJSONObject("summary");

                if (summary != null) {
                    cardModel.setType(summary.optString("type"));

                    cardModel.setBalance(summary.optString("balance"));

                    cardModel.setCredit(summary.optString("credit"));
                }

                if (cardModel == null) {
                    return;
                }

                cardList.addCard(cardModel);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper : Get drawable resource by name
     *
     * @param name resource
     * @return drawable
     */
    private static Drawable findDrawableByName(Context context, String name) {
        try {
            Resources resources = context.getResources();

            return ResourcesCompat.getDrawable(resources, resources.getIdentifier(name, "drawable", context.getPackageName()), null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Helper : Loads Json asset
     *
     * @return json string
     */
    private static String loadJSONFromAsset(Context context, @NonNull String json) {
        try {
            InputStream is = context.getAssets().open(json);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
