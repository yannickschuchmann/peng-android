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
public class EditUserDialogFragment extends DialogFragment {

    @Bind(R.id.nick) EditText mNick;
    @Bind(R.id.slogan) EditText mSlogan;

    public EditUserDialogFragment() {
    }

    public static EditUserDialogFragment newInstance(String nick, String slogan) {
        EditUserDialogFragment frag = new EditUserDialogFragment();
        Bundle args = new Bundle();
        args.putString("nick", nick);
        args.putString("slogan", slogan);
        frag.setArguments(args);
        return frag;
    }

    public String getNick() {
        return mNick.getText().toString();
    }

    public String getSlogan() {
        return mSlogan.getText().toString();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_edit_user, null);

        ButterKnife.bind(this, view);

        mNick.setText(getArguments().getString("nick"));
        mSlogan.setText(getArguments().getString("slogan"));

        builder.setView(view);
        builder.setTitle(getString(R.string.dialogTitleEditUser)); // TODO use strings xml

        final EditUserDialogFragment frag = this;
        builder.setPositiveButton("update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    mListener.onDialogPositiveClick(frag);
                }
            })
            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    mListener.onDialogNegativeClick(frag);
                }
            });

        return builder.create();
    }

    public interface EditUserDialogListener {
        public void onDialogPositiveClick(EditUserDialogFragment dialog);
        public void onDialogNegativeClick(EditUserDialogFragment dialog);
    }

    EditUserDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (EditUserDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement EditUserDialogListener");
        }
    }

}
