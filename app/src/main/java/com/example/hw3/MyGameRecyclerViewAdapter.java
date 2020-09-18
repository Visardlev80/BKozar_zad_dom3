package com.example.hw3;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.hw3.dummy.GameContent;
import com.example.hw3.dummy.GameContent.Game;

import java.util.List;

import static com.example.hw3.R.id.game_name;

public class MyGameRecyclerViewAdapter extends RecyclerView.Adapter<MyGameRecyclerViewAdapter.ViewHolder> {

    private final List<GameContent.Game> mValues;
    public final GameFragment.OnListFragmentInteractionListener mListener;

    public MyGameRecyclerViewAdapter(List<GameContent.Game> items, GameFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_game, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Game mGame = mValues.get(position);
        holder.mItem = mGame;
        holder.mGameView.setText(mGame.title);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentClickInteraction(holder.mItem, v);

                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mListener.onListFragmentLongClickInteraction(holder.mItem);
                return false;
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteButtonClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final ImageView mAvatarView;
        public final TextView mGameView;
        public GameContent.Game mItem;
        public ImageButton deleteButton;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            view.setBackgroundColor(Color.rgb(100, 150, 250));

            mGameView = (TextView) view.findViewById(game_name);
            deleteButton = view.findViewById(R.id.game_delete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mGameView.getText() + "'";
        }
    }
}
