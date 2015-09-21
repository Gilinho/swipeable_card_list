package com.worked.swipeable_card_list.view.fling;

/**
 * Created by bnp804 on 9/13/15.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.worked.swipeable_card_list.utils.shared.Constants;
import com.worked.swipeable_card_list.view_model_controller.SwipeableCardList;

/**
 * Helper : Gesture Listener
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final String TAG = GestureListener.class.getSimpleName();

    private final RecyclerView recyclerView;

    private Scroll scroll;

    private Context context;

    public GestureListener(Context context, Scroll scroll, RecyclerView recyclerView) {
        this.context = context;

        this.scroll = scroll;

        this.recyclerView = recyclerView;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling");

        float x = scroll.getView().getX();

        float y = scroll.getView().getY();

        String direction = Constants.RIGHT;

        if (e1.getX() > e2.getX()) {
            direction = Constants.LEFT;
        }

        return scroll.onFling(x, y, velocityX, velocityY, direction);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(TAG, "onTapped");

        Toast.makeText(context, SwipeableCardList.getCardAt(scroll.getPosition()).getTitle() + " tapped!", Toast.LENGTH_SHORT).show();

        return true;
    }
}