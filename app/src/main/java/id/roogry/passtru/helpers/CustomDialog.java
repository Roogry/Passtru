package id.roogry.passtru.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id.roogry.passtru.R;
import id.roogry.passtru.ui.ListAccountActivity;

public class CustomDialog {
    private Activity activity;
    private Dialog dialog;

    public CustomDialog(Activity myActivity){
        activity = myActivity;
    }

    public void startAlertDialog(String type, String message, Integer view){
        dialog = new Dialog(activity);
        dialog.setContentView(view);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView btnClose = dialog.findViewById(R.id.btnClose);
        dialog.show();

        btnClose.setOnClickListener(v ->{
            dismissDialog();
        });
    }

    public void dismissDialog(){
        dialog.dismiss();
    }


}
