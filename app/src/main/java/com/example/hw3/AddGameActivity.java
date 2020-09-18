package com.example.hw3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw3.dummy.GameContent.Game;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        addGame();
    }

    public boolean validationDate(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        Date testDate = null;

        try
        {
            testDate = sdf.parse(date);
        }

        catch (ParseException e)
        {
            return false;
        }

        if (!sdf.format(testDate).equals(date))
        {
            return false;
        }
        return true;
    }

    @SuppressLint("WrongViewCast")
    private void addGame() {

        Button addButton;

        addButton = findViewById(R.id.addGameButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleEditTxt;
                EditText developerEditTxt;
                EditText publisherEditTxt;
                EditText dataEditTxt;

                titleEditTxt = findViewById(R.id.newTitle);
                developerEditTxt = findViewById(R.id.newDeveloper);
                publisherEditTxt = findViewById(R.id.newPublisher);
                dataEditTxt = findViewById(R.id.newData);

                String title;
                String developer;
                String publisher;
                String data;

                title = titleEditTxt.getText().toString();
                developer = developerEditTxt.getText().toString();
                publisher = publisherEditTxt.getText().toString();
                data = dataEditTxt.getText().toString();

                if(title.isEmpty()){
                    titleEditTxt.setError(getString(R.string.thisFieldCannotBeEmpty));
                    return;
                }

                if(developer.isEmpty()){
                    developerEditTxt.setError(getString(R.string.thisFieldCannotBeEmpty));
                    return;
                }

                if(publisher.isEmpty()){
                    publisherEditTxt.setError(getString(R.string.thisFieldCannotBeEmpty));
                    return;
                }

                if(!validationDate(data)){
                    dataEditTxt.setError("Use correct date format! (dd.MM.yyyy)");
                    return;
                }

                Game newGame = new Game(title, developer, publisher, data);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Games").document(Integer.toString(newGame.ID)).set(newGame);

                titleEditTxt.setText("");
                developerEditTxt.setText("");
                publisherEditTxt.setText("");
                dataEditTxt.setText("");

                finish();
            }
        });
    }
}
