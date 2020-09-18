package com.example.hw3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.hw3.dummy.GameContent;


public class GameEditFragment extends Fragment {

    private int id;
    private String title;
    private String developer;
    private String publisher;
    private String data;

    private EditText titleEditTxt;
    private EditText developerEditTxt;
    private EditText publisherEditTxt;
    private EditText dataEditTxt;
    private Button saveChangesBtn;

    public GameEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_edit, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentActivity activity = getActivity();

        titleEditTxt = activity.findViewById(R.id.title);
        developerEditTxt = activity.findViewById(R.id.developer);
        publisherEditTxt = activity.findViewById(R.id.publisher);
        dataEditTxt = activity.findViewById(R.id.data);

        saveChangesBtn = activity.findViewById(R.id.saveChangesButton);

        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        Intent intent = getActivity().getIntent();
        if(intent != null){
            GameContent.Game recivedGame = intent.getParcelableExtra(MainActivity.GameExtra);
            if(recivedGame != null) {
                displayGame(recivedGame);
            }
        }

    }

    public void saveChanges(){


        title = titleEditTxt.getText().toString();
        developer = developerEditTxt.getText().toString();
        publisher = publisherEditTxt.getText().toString();
        data = dataEditTxt.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Games").document(Integer.toString(id));

        docRef.update("title", title,
                            "developer", developer,
                            "publisher", publisher,
                            "data", data);

        getActivity().finish();
    }

    public void displayGame(GameContent.Game game){

        id = game.ID;
        title = game.title;
        developer = game.developer;
        publisher = game.publisher;
        data = game.data;

        titleEditTxt.setText(title);
        developerEditTxt.setText(developer);
        publisherEditTxt.setText(publisher);
        dataEditTxt.setText(data);
    }
}
