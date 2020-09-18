package com.example.hw3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.hw3.dummy.GameContent;


public class GameInfoFragment extends Fragment {

    public GameInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_info, container, false);
    }

    public void displayGame(GameContent.Game game){
        FragmentActivity activity = getActivity();

        TextView titleEditTxt = activity.findViewById(R.id.title);
        TextView developerEditTxt = activity.findViewById(R.id.developer);
        TextView publisherEditTxt = activity.findViewById(R.id.publisher);
        TextView dataEditTxt = activity.findViewById(R.id.data);

        String title = game.title;
        String developer = game.developer;
        String publisher = game.publisher;
        String data = game.data;
        titleEditTxt.setText("Title: "+title);
        developerEditTxt.setText("Developer: "+developer);
        publisherEditTxt.setText("Publisher: "+publisher);
        dataEditTxt.setText("Release date: "+data);
    }

    public void displayEmptyGame(){
        FragmentActivity activity = getActivity();

        TextView titleEditTxt = activity.findViewById(R.id.title);
        TextView developerEditTxt = activity.findViewById(R.id.developer);
        TextView publisherEditTxt = activity.findViewById(R.id.publisher);
        TextView dataEditTxt = activity.findViewById(R.id.data);

        titleEditTxt.setText("");
        developerEditTxt.setText("");
        publisherEditTxt.setText("");
        dataEditTxt.setText("");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        if(intent != null){
           GameContent.Game recivedGame = intent.getParcelableExtra(MainActivity.GameExtra);
            if(recivedGame != null) {
                displayGame(recivedGame);
            }
        }
    }

}
