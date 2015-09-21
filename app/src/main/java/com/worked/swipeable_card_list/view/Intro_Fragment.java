package com.worked.swipeable_card_list.view;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.worked.swipeable_card_list.BR;
import com.worked.swipeable_card_list.R;
import com.worked.swipeable_card_list.utils.shared.Constants;
import com.worked.swipeable_card_list.view.fling.GestureListener;
import com.worked.swipeable_card_list.view.fling.Scroll;
import com.worked.swipeable_card_list.view_model.SwipeableCardModel;
import com.worked.swipeable_card_list.view_model_controller.SwipeableCardList;

public class Intro_Fragment extends Fragment {

    public static final String TAG = Intro_Fragment.class.getSimpleName();

    private Context context;

    private RecyclerView recyclerView;

    /**
     * Factory Method : create a new instance
     *
     * @return a new instance of fragment {@link Intro_Fragment}
     */
    public static Intro_Fragment newInstance() {
        return new Intro_Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);

        context = getActivity();

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.intro_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView(view);
    }

    private void setupRecyclerView(@NonNull View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.intro_container);

        if (recyclerView == null) {
            return;
        }

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(new CardAdapter());
    }

    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.BindingHolder> {
        // Create new views (invoked by the layout manager)
        @Override
        public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View swipeableCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipeable_card, parent, false);

            BindingHolder viewHolder = new BindingHolder(swipeableCard);

            return viewHolder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(BindingHolder holder, int position) {
            final SwipeableCardModel swipeableCard = SwipeableCardList.getCardAt(position);

            holder.getBinding().setVariable(BR.swipeableCard, swipeableCard);

            holder.getBinding().executePendingBindings();
        }

        // Return the size of your data (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return SwipeableCardList.getCardsCount();
        }

        /**
         * Custom ViewHolder, in this case a binding holder
         * <p/>
         * Each card wrapper in the holder has a touch listener which manages all touch events:
         * Fling, Scroll, onSingleTap, etc...
         * onMove will ignore intercept calls on the parent
         */
        public class BindingHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
            private Scroll scroll;

            private GestureDetector gestureDetector;

            private GestureListener gesturelistener;

            private VelocityTracker velocityTracker;

            private int newX, offsetX;

            private ViewDataBinding binding;

            private CardView card;

            private String direction;

            /**
             * Binder Holder
             *
             * @param view
             */
            public BindingHolder(View view) {
                super(view);

                view.setOnTouchListener(this);

                card = (CardView) view.findViewById(R.id.swipeable_card_view);

                // binding
                binding = DataBindingUtil.bind(view);

                // scroll runnable
                scroll = new Scroll(context, recyclerView);

                // gestures, fling, down, tap
                gesturelistener = new GestureListener(context, scroll, recyclerView);

                // gesture detector
                gestureDetector = new GestureDetector(context, gesturelistener);
            }

            public ViewDataBinding getBinding() {
                return binding;
            }

            /**
             * On Card Wrapper Touch
             * <p/>
             * Moves card around...
             *
             * @param view
             * @param event
             * @return true - if capturing simple gesture events
             */
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // translates view without affecting velocity tracking
                //event.offsetLocation(newX, view.getY());

                // set current position
                scroll.setPosition(getAdapterPosition());

                // set current view
                scroll.setView(card);

                ViewConfiguration viewConfiguration = ViewConfiguration.get(context);

                int mSlop = viewConfiguration.getScaledTouchSlop();

                // touch action
                int action = MotionEventCompat.getActionMasked(event);

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("TAG", "onDown  : " + (int) card.getX());

                        offsetX = (int) card.getX() - (int) event.getRawX();

                        velocityTracker = VelocityTracker.obtain();

                        velocityTracker.clear();

                        velocityTracker.addMovement(event);

                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("TAG", "onMove  : " + (int) card.getX());

                        newX = (int) event.getRawX() + offsetX;

                        direction = newX > Constants.START_X ? Constants.RIGHT : Constants.LEFT;

                        // touch slop : used to prevent accidental scrolling when the user is
                        // performing some other touch operation, such as touching on-screen elements
                        if (Math.abs(newX) > mSlop) {
                            card.setX(newX);

                            float offset = (float) newX / (recyclerView.getMeasuredWidth() - Constants.THRESHOLD);

                            float alpha = direction.equals(Constants.RIGHT) ? Constants.FLOAT - offset : Constants.FLOAT + offset;

                            card.setAlpha(alpha);

                            velocityTracker.addMovement(event);

                            // disable recycler view scrolling while card is panning
                            recyclerView.requestDisallowInterceptTouchEvent(true);
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("TAG", "onUp    : " + (int) card.getX());

                        recyclerView.requestDisallowInterceptTouchEvent(false);

                        velocityTracker.recycle();

                        // not off screen & not a fling gesture : reset card
                        if (newX != Constants.START_X && !gestureDetector.onTouchEvent(event)) {
                            card.setX(Constants.START_X);

                            card.setAlpha(Constants.OPAQUE);

                            Log.d(TAG, "resetting card X location : " + (int) card.getX());
                        }

                        break;
                }

                return gestureDetector.onTouchEvent(event);
            }
        }
    }
}
