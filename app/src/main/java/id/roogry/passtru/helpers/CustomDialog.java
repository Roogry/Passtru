package id.roogry.passtru.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import id.roogry.passtru.R;
import id.roogry.passtru.adapter.SosmedAdapter;
import id.roogry.passtru.models.Sosmed;
import id.roogry.passtru.repository.SosmedRepository;

public class CustomDialog {
    private final Activity activity;
    private final SosmedRepository sosmedRepository;
    private final int view;

    private Dialog dialog;

    public CustomDialog(Activity activity, int view) {
        this.activity = activity;
        this.view = view;
        sosmedRepository = new SosmedRepository(activity.getApplication());

        dialog = new Dialog(activity);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ImageView btnClose = dialog.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(v -> {
            dismissDialog();
        });
    }

    public void startFormSosmed(int position, String sosmedTitle, MoreOptionInterface callback){
        dialog.show();

        Button btnSave = dialog.findViewById(R.id.btnSubmit);
        EditText inputSosmed = dialog.findViewById(R.id.edtSosmed);

        inputSosmed.setText(sosmedTitle);

        btnSave.setOnClickListener(v -> {
            String title = inputSosmed.getText().toString();

            if (sosmedTitle == null){
                Sosmed newSosmed = new Sosmed(title);
                sosmedRepository.insert(newSosmed);
                Toast.makeText(activity, "Social Media Added! ", Toast.LENGTH_LONG).show();
            }else{
                callback.updateSosmed(position, title);
            }

            dismissDialog();
        });
    }

    public void startAlertDialog(String type, int position, MoreOptionInterface callback) {
        dialog.show();
        if(type.equals("more option")){
            Button edit = dialog.findViewById(R.id.btnEdit);
            Button delete = dialog.findViewById(R.id.btnDelete);
            edit.setOnClickListener(v ->{
                callback.getDataByPos(position);
                dismissDialog();
            });

            delete.setOnClickListener(v -> {
                callback.delete(position);
                dismissDialog();
            });
        }
    }

    public void startAlertDialogOptionAccount(String type, int position, MoreOptionInterface callback) {
        dialog.show();
        if(type.equals("more option")){
            Button edit = dialog.findViewById(R.id.btnEdit);
            Button delete = dialog.findViewById(R.id.btnDelete);
            Button copy = dialog.findViewById(R.id.btnCopy);
            copy.setOnClickListener(v->{
                callback.copyPassword(position);
                dismissDialog();
            });

            edit.setOnClickListener(v ->{
                callback.getDataByPos(position);
                dismissDialog();
            });

            delete.setOnClickListener(v -> {
                callback.delete(position);
                dismissDialog();
            });
        }
    }

    public void dismissDialog() {
        dialog.dismiss();
    }

}
