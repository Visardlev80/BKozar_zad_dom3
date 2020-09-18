package com.example.hw3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.hw3.dummy.GameContent;

public class EditDialog extends DialogFragment {

    private String title;
    private GameContent.Game mGame;

    public EditDialog(GameContent.Game game) {
        this.title = game.title;
        this.mGame = game;
    }

    static EditDialog newInstance(GameContent.Game game){
        return new EditDialog(game);
    }

    public interface OnEditDialogInteractionListener {
        void onEditDialogPositiveClick(DialogFragment dialog, GameContent.Game game);
        void onEditDialogNegativeClick(DialogFragment dialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Edit "+ title +"?");

        builder.setPositiveButton(getString(R.string.dialog_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OnEditDialogInteractionListener mListener =  (OnEditDialogInteractionListener) getActivity();
                mListener.onEditDialogPositiveClick(EditDialog.this, mGame);
            }
        });

        builder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OnEditDialogInteractionListener mListener =  (OnEditDialogInteractionListener) getActivity();
                mListener.onEditDialogNegativeClick(EditDialog.this);
            }
        });
        return builder.create();
    }
}
