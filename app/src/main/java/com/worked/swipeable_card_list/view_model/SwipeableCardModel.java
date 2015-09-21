package com.worked.swipeable_card_list.view_model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;

import com.worked.swipeable_card_list.view.Intro_Activity;

/**
 * Card Model Object
 * Represents each card in the recycler {@link Intro_Activity}
 */
public class SwipeableCardModel extends BaseObservable {

    // base attributes
    private String id;
    private int index;

    // attributes
    private String type;

    // views
    private String title, subtitle;

    // alert
    private boolean alertEnabled;
    private String alertTitle;
    private Drawable alertIcon;

    //summary
    private String balance, credit;

    //-- views

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

        notifyChange();
    }

    @Bindable
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;

        notifyChange();
    }

    // -- alerts

    @Bindable
    public boolean isAlertEnabled() {
        return alertEnabled;
    }

    public void setAlertEnabled(boolean enabled) {
        this.alertEnabled = enabled;

        notifyChange();
    }

    @Bindable
    public Drawable getAlertIcon() {
        return alertIcon;
    }

    public void setAlertIcon(Drawable alertIcon) {
        this.alertIcon = alertIcon;

        notifyChange();
    }

    @Bindable
    public String getAlertTitle() {
        return alertTitle;
    }

    public void setAlertTitle(String alertTitle) {
        this.alertTitle = alertTitle;

        notifyChange();
    }

    // -- attributes

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;

        notifyChange();
    }

    // -- summary

    @Bindable
    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;

        notifyChange();
    }

    @Bindable
    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;

        notifyChange();
    }

    // -- base attributes

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}