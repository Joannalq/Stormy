package com.example.joanna.stormy.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.joanna.stormy.R;

public class AlertError extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //for current context,use this or activity.this
        Context context=getActivity();
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
                  builder .setTitle(context.getString(R.string.title_error))
                     .setMessage(context.getString(R.string.message_error))
                     .setPositiveButton(context.getString(R.string.button),null);
        AlertDialog dialog=builder.create();
        return dialog;
    }
}
