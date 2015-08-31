package com.worked.db_recycler_view.view;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.worked.db_recycler_view.BR;
import com.worked.db_recycler_view.R;
import com.worked.db_recycler_view.view_model.CardModel;
import com.worked.db_recycler_view.view_model_controller.CardList;

public class Intro_Fragment extends Fragment {

    public static final String TAG = Intro_Fragment.class.getSimpleName();

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
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.intro_container);

        if (recyclerView == null) {
            return;
        }

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(new CardAdapter());
    }

    private class CardAdapter extends RecyclerView.Adapter<CardAdapter.BindingHolder> {
        // Create new views (invoked by the layout manager)
        @Override
        public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);

            BindingHolder viewHolder = new BindingHolder(card);

            return viewHolder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(BindingHolder holder, int position) {
            final CardModel card = CardList.getCardAt(position);

            holder.getBinding().setVariable(BR.card, card);

            holder.getBinding().executePendingBindings();

            //holder.title.setText(cards.get(position).getTitle());
        }

        // Return the size of your data (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return CardList.getCardsCount();
        }



        /**
         * Custom ViewHolder, in this case a binding holder
         */
        public class BindingHolder extends RecyclerView.ViewHolder {

            private ViewDataBinding binding;

            public BindingHolder(View card) {
                super(card);

                binding = DataBindingUtil.bind(card);
            }

            public ViewDataBinding getBinding() {
                return binding;
            }
        }
    }
}
