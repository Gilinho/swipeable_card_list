package com.worked.swipeable_card_list.view_model_controller;

import android.support.annotation.NonNull;

import com.worked.swipeable_card_list.view_model.SwipeableCardModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Card List Data Object - Singleton
 * Represents data in each card
 * Helper methods to manipulate the {@link SwipeableCardModel} data
 * <p/>
 * List containing {@link SwipeableCardModel} representing each card in the {@link SwipeableCardList}
 */
public class SwipeableCardList {
    private static final SwipeableCardList INSTANCE = new SwipeableCardList();

    private static List<SwipeableCardModel> data = new ArrayList();

    private SwipeableCardList() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already instantiated...");
        }
    }

    public static SwipeableCardList getInstance() {
        return INSTANCE;
    }

    /**
     * Get model data
     *
     * @return data
     */
    public static List<SwipeableCardModel> getCards() {
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
    public static SwipeableCardModel getCardAt(int position) {
        return data.get(position);
    }

    /**
     * Add model data
     * Default : set current to first card
     *
     * @param cardData card data
     */
    public static void addCard(@NonNull SwipeableCardModel cardData) {
        data.add(cardData);
    }

    /**
     * Add model data at index
     *
     * @param cardData card data
     * @param index    index
     */
    public static void addCardAt(@NonNull SwipeableCardModel cardData, @NonNull int index) {
        data.add(index, cardData);
    }

    /**
     * remove model data by object
     *
     * @param cardData
     */
    public static void removeCard(@NonNull SwipeableCardModel cardData) {
        data.remove(cardData);
    }

    /**
     * Remove model data by position
     *
     * @param position
     */
    public static void removeCardAt(@NonNull int position) {
        data.remove(position);
    }
}