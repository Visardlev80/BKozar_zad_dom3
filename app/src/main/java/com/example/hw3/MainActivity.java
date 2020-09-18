package com.example.hw3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.hw3.dummy.GameContent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static com.example.hw3.dummy.GameContent.Games;

public class MainActivity extends AppCompatActivity implements
        GameFragment.OnListFragmentInteractionListener,
        DeleteDialog.OnDeleteDialogInteractionListener,
        EditDialog.OnEditDialogInteractionListener {

    int selectedDeleteItem = -1;
    public static  String GameExtra = "GameExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddGameActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        getFirebaseData();

    }
    public void getFirebaseData(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Games")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            Games.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Map<String, Object> game = new HashMap<>();
                                game = document.getData();

                                Object oId = game.get("ID");
                                Object oTitle = game.get("title");
                                Object oDeveloper = game.get("developer");
                                Object oPublisher = game.get("publisher");
                                Object oData = game.get("data");

                                int id = Integer.parseInt(oId.toString());
                                String title = oTitle.toString();
                                String developer = oDeveloper.toString();
                                String publisher = oPublisher.toString();
                                String data = oData.toString();

                                GameContent.Game gameFromFireBase = new GameContent.Game(id, title, developer, publisher, data);

                                GameContent.addItem(gameFromFireBase);
                                GameContent.lastID = id+1;
                            }
                            ((GameFragment) getSupportFragmentManager().findFragmentById(R.id.GameFragment)).notifyDataChange();
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }


    public void startGameInfoActivity(GameContent.Game game){
        Intent intent = new Intent(this, GameInfoActivity.class);
        intent.putExtra(GameExtra, game);
        startActivity(intent);
    }

    public void startEditGameActivity(GameContent.Game game){
        Intent intent = new Intent(this, EditGameActivity.class);
        intent.putExtra(GameExtra, game);
        startActivity(intent);
    }

    @Override
    public void onListFragmentClickInteraction(GameContent.Game item, View view) {

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            displayGameInFragment(item);
        } else {
            startGameInfoActivity(item);
        }
    }

    @Override
    public void onListFragmentLongClickInteraction(GameContent.Game item) {
        EditDialog.newInstance(item).show(getSupportFragmentManager(), getString(R.string.callingdialog));
    }

    @Override
    public void onDeleteButtonClick(int position) {
        selectedDeleteItem = position;
        DeleteDialog.newInstance().show(getSupportFragmentManager(), getString(R.string.delete_dialog_tag));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if (selectedDeleteItem >= 0 && selectedDeleteItem < Games.size()) {
            GameContent.deleteItem(selectedDeleteItem);


            ((GameFragment) getSupportFragmentManager().findFragmentById(R.id.GameFragment)).notifyDataChange();

            GameInfoFragment gameInfoFragment = ( (GameInfoFragment) getSupportFragmentManager().findFragmentById(R.id.GameInfoFragment));
            if(gameInfoFragment != null){
                gameInfoFragment.displayEmptyGame();
            }
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onEditDialogPositiveClick(DialogFragment dialog, GameContent.Game game) {

        startEditGameActivity(game);

    }

    @Override
    public void onEditDialogNegativeClick(DialogFragment dialog) {

    }

    public void displayGameInFragment(GameContent.Game game){
        GameInfoFragment gameInfoFragment = ( (GameInfoFragment) getSupportFragmentManager().findFragmentById(R.id.GameInfoFragment));
        if(gameInfoFragment != null){
            gameInfoFragment.displayGame(game);
        }
    }




}
