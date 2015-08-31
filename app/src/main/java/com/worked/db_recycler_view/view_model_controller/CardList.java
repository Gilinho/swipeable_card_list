package com.worked.db_recycler_view.view_model_controller;

import android.support.annotation.NonNull;

import com.worked.db_recycler_view.view_model.CardModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Card List Data Object - Singleton
 * Represents data in each card
 * Helper methods to manipulate the {@link CardModel} data
 * <p/>
 * List containing {@link CardModel} representing each card in the {@link CardList}
 */
public class CardList {
    private static final CardList INSTANCE = new CardList();

    private static List<CardModel> data = new ArrayList();

    private CardList() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already instantiated...");
        }
    }

    public static CardList getInstance() {
        return INSTANCE;
    }

    /**
     * Get model data
     *
     * @return data
     */
    public static List<CardModel> getCards() {
        return data;
    }

    /**
     * Get model data size
     *
     * @return data size
     */
    public static int getCardsCount() {
        return data.size();
    }

    /**
     * Get card by position
     *
     * @param position index
     * @return card
     */
    public static CardModel getCardAt(int position) {
        return data.get(position);
    }

    /**
     * Add model data
     * Default : set current to first card
     *
     * @param cardData card data
     */
    public static void addCard(@NonNull CardModel cardData) {
        data.add(cardData);
    }
}