package com.example.dawaatracker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ConfirmDeleteDialog extends DialogFragment {

    public interface DeleteListener {
        void onConfirmDelete(int medicineId);
    }

    private DeleteListener listener;

    public static ConfirmDeleteDialog newInstance(int id, String name) {
        ConfirmDeleteDialog dialog = new ConfirmDeleteDialog();
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putString("name", name);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DeleteListener) {
            listener = (DeleteListener) context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int id = getArguments().getInt("id");
        String name = getArguments().getString("name");

        return new AlertDialog.Builder(requireActivity())
                .setTitle("Delete medicine")
                .setMessage("Are you sure you want to delete " + name + "?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onConfirmDelete(id);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
    }
}
