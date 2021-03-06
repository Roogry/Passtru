package id.roogry.passtru.helpers.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import id.roogry.passtru.R;

public class CustomDialog {
    private final Dialog dialog;
    private final Activity activity;

    public CustomDialog(Activity activity, int view) {
        this.activity = activity;
        dialog = new Dialog(activity);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ImageView btnClose = dialog.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(v -> {
            dismissDialog();
        });
    }

    public void startConfirmDeleteSosmed(int position, SosmedConfirmDeleteInterface callback) {
        dialog.show();

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnDelete = dialog.findViewById(R.id.btnDelete);

        btnCancel.setOnClickListener(view -> dismissDialog());

        btnDelete.setOnClickListener(v -> {
            callback.delete(position);
            dismissDialog();
        });
    }

    public void startFormSosmed(int position, String sosmedTitle, SosmedFormInterface callback) {
        dialog.show();

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        TextView tvSubtitle = dialog.findViewById(R.id.tvSubtitle);
        Button btnSave = dialog.findViewById(R.id.btnSubmit);
        EditText edtTitle = dialog.findViewById(R.id.edtSosmed);

        edtTitle.setText(sosmedTitle);
        if (sosmedTitle != null) {
            tvTitle.setText("Update Social Media");
            tvSubtitle.setText(String.format("Input new title for %s", sosmedTitle));
        }

        btnSave.setOnClickListener(v -> {
            String title = edtTitle.getText().toString();

            if (title.trim().length() != 0){
                if (sosmedTitle == null) {
                    callback.insert(title);
                } else {
                    callback.update(position, title);
                }

                dismissDialog();
            }else{
                edtTitle.setError(activity.getString(R.string.err_required));
                edtTitle.setBackground(ContextCompat.getDrawable(
                        activity,
                        R.drawable.bg_input_light_error
                ));
            }

        });
    }

    public void startSosmedOption(int position, SosmedOptionInterface callback) {
        dialog.show();
        Button edit = dialog.findViewById(R.id.btnEdit);
        Button delete = dialog.findViewById(R.id.btnDelete);

        edit.setOnClickListener(v -> {
            callback.sosmedByPos(position);
            dismissDialog();
        });

        delete.setOnClickListener(v -> {
            callback.deleteConfirm(position);
            dismissDialog();
        });
    }

    public void startAccountOption(int position, String username, AccountOptionInterface callback) {
        dialog.show();

        TextView tvUsername = dialog.findViewById(R.id.tvUsername);
        Button edit = dialog.findViewById(R.id.btnEdit);
        Button delete = dialog.findViewById(R.id.btnDelete);
        Button copy = dialog.findViewById(R.id.btnCopy);

        tvUsername.setText(username);

        copy.setOnClickListener(v -> {
            callback.copyPassword(position);
            dismissDialog();
        });

        edit.setOnClickListener(v -> {
            callback.getDataByPos(position);
            dismissDialog();
        });

        delete.setOnClickListener(v -> {
            callback.delete(position);
            dismissDialog();
        });
    }

    public void dismissDialog() {
        dialog.dismiss();
    }

}
