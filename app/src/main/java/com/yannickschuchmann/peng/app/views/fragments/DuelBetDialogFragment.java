package com.yannickschuchmann.peng.app.views.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.yannickschuchmann.peng.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DuelBetDialogFragment extends DialogFragment {

    @Bind(R.id.bet) EditText mBet;

    public DuelBetDialogFragment() {
    }

    public static DuelBetDialogFragment newInstance() {
        DuelBetDialogFragment frag = new DuelBetDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;

    }

    public String getBet() {
        return mBet.getText().toString();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_duel_bet, null);

        ButterKnife.bind(this, view);

        builder.setView(view);
        builder.setTitle("Um was wird gespielt?"); // TODO use strings xml

        final DuelBetDialogFragment frag = this;
        builder.setPositiveButton("Fordern", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    mListener.onDialogPositiveClick(frag);
                }
            })
            .setNegativeButton("abbrechen", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    mListener.onDialogNegativeClick(frag);
                }
            });

        return builder.create();
    }

    public interface DuelBetDialogListener {
        public void onDialogPositiveClick(DuelBetDialogFragment dialog);
        public void onDialogNegativeClick(DuelBetDialogFragment dialog);
    }

    DuelBetDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (DuelBetDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement DuelBetDialogListener");
        }
    }

}
