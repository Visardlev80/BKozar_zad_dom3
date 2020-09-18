package com.example.hw3.dummy;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class GameContent {


    public static final List<Game> Games = new ArrayList<Game>();

    public static int lastID;

    public static void addItem(Game item) {
        Games.add(item);
    }

    public static void deleteItem(int position) {

        Game temp = Games.get(position);
        int id = temp.ID;

        //TODO delete from database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Games").document(Integer.toString(id)).delete();

        Games.remove(position);
    }

    public static class Game implements Parcelable {
        public final int ID;
        public final String title;
        public final String developer;
        public final String publisher;
        public final String data;

        public Game(int id, String title, String developer, String publisher, String data) {
            this.ID = id;
            this.title = title;
            this.developer = developer;
            this.publisher = publisher;
            this.data = data;
        }

        public Game(String title, String developer, String publisher, String data) {
            this.ID = lastID;
            this.title = title;
            this.developer = developer;
            this.publisher = publisher;
            this.data = data;
        }

        protected Game(Parcel in) {
            ID = in.readInt();
            title = in.readString();
            developer = in.readString();
            publisher = in.readString();
            data = in.readString();
        }

        public static final Creator<Game> CREATOR = new Creator<Game>() {
            @Override
            public Game createFromParcel(Parcel in) {
                return new Game(in);
            }

            @Override
            public Game[] newArray(int size) {
                return new Game[size];
            }
        };

        public static void clear() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ID);
            dest.writeString(title);
            dest.writeString(developer);
            dest.writeString(publisher);
            dest.writeString(data);
        }

    }

}
