package id.roogry.passtru.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    private Dialog dialog;
    private final SosmedRepository sosmedRepository;

    public CustomDialog(Activity activity) {
        this.activity = activity;
        sosmedRepository = new SosmedRepository(activity.getApplication());
    }

    public void startAlertDialog(String type, int id, int view) {
        dialog = new Dialog(activity);
        dialog.setContentView(view);
        ImageView btnClose = dialog.findViewById(R.id.btnClose);

        if (type.equals("form")) {
            Button btnSave = dialog.findViewById(R.id.btnSubmit);
            EditText inputSosmed = dialog.findViewById(R.id.edtSosmed);

            btnSave.setOnClickListener(v -> {
                Sosmed sosmed = new Sosmed(inputSosmed.getText().toString());
                sosmedRepository.insert(sosmed);
                Toast.makeText(activity, "Social Media Added! ", Toast.LENGTH_LONG).show();
                dismissDialog();
            });
        }else if(type.equals("more option")){
            Button edit = dialog.findViewById(R.id.btnEdit);
            Button delete = dialog.findViewById(R.id.btnDelete);
            Button copy = dialog.findViewById(R.id.btnCopy);
            SosmedAdapter sosmedAdapter = new SosmedAdapter(activity);

            delete.setOnClickListener(v -> {
               sosmedAdapter.removeItem(id);
            });
        }

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        btnClose.setOnClickListener(v -> {
            dismissDialog();
        });
    }

    public void dismissDialog() {
        dialog.dismiss();
    }

}
