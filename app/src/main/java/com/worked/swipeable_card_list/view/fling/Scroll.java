package com.worked.swipeable_card_list.view.fling;

/**
 * Created by bnp804 on 9/13/15.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.worked.swipeable_card_list.R;
import com.worked.swipeable_card_list.utils.shared.Constants;
import com.worked.swipeable_card_list.view_model.SwipeableCardModel;
import com.worked.swipeable_card_list.view_model_controller.SwipeableCardList;

/**
 * Helper : Fling
 */
public class Scroll implements Runnable {
    private static final String TAG = Scroll.class.getSimpleName();

    private final RecyclerView recyclerView;

    private Context context;

    private View view;

    private Scroller scroller;

    private int scrollX, position;

    private Adapter adapter;

    private String direction;

    // constructor
    public Scroll(Context context, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;

        this.context = context;

        adapter = recyclerView.getAdapter();

        scroller = new Scroller(context, new LinearInterpolator());
    }

    /**
     * Fling running...
     */
    @Override
    public void run() {
        if (scroller.computeScrollOffset()) {

            scrollX = scroller.getCurrX();

            view.setX(scrollX);

            float offset = (float) scrollX / (view.getMeasuredWidth() - Constants.THRESHOLD);

            float alpha = direction.equals(Constants.RIGHT) ? Constants.FLOAT - offset : Constants.FLOAT + offset;

            view.setAlpha(alpha);

            // off screen : remove card from adaptor
            if (scrollX > view.getMeasuredWidth() || scrollX < -view.getMeasuredWidth()) {
                Log.d(TAG, "remove card : " + SwipeableCardList.getCardAt(position).getTitle());

                final SwipeableCardModel card = SwipeableCardList.getCardAt(position);

                SwipeableCardList.removeCard(card);

                adapter.notifyItemRemoved(position);

                // snackbar
                Snackbar.make(recyclerView.findViewById(R.id.intro_container), card.getTitle() + " has been removed.", Constants.SNACKBAR_LENGTH)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SwipeableCardList.addCardAt(card, position);

                                adapter.notifyItemInserted(position);

                                // reset card
                                resetCard();
                            }
                        })
                        .setActionTextColor(Color.RED)
                        .show();

                return;
            }

            // recursive
            view.post(this);
        }

        // not off screen : reset card
        if (scroller.isFinished() && scrollX != Constants.START_X) {
            resetCard();

            return;
        }
    }

    /**
     * Reset card attributes
     */
    private void resetCard() {
        view.setX(Constants.START_X);

        view.setAlpha(Constants.OPAQUE);

        scrollX = Constants.START_X;

        Log.d(TAG, "resetting card X location : " + scrollX);
    }

    /**
     * On Fling - post fling values to FlingRunnable
     *
     * @param motionX
     * @param motionY
     * @param velocityX
     * @param velocityY
     * @return true
     */
    public boolean onFling(float motionX, float motionY, float velocityX, float velocityY, String direction) {
        int moX = (int) motionX;

        int moY = 0;

        int minX = -view.getMeasuredWidth() * 2;

        int maxX = view.getMeasuredWidth() * 2;

        int minY = 0;

        int maxY = 0;

        int velX = (int) velocityX;

        int velY = 0;

        this.direction = direction;

        // fling magic
        scroller.fling(moX, moY, velX, velY, minX, maxX, minY, maxY);

        view.post(this);

        return true;
    }

    // getters & setters

    public View getView() {
        return view;
    }

    public void setView(CardView view) {
        this.view = view;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
