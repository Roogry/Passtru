package id.roogry.passtru.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import id.roogry.passtru.R;
import id.roogry.passtru.database.PasstruRoomDatabase;
import id.roogry.passtru.models.Sosmed;

public class CustomDialog {
    private final Activity activity;
    private Dialog dialog;

    public CustomDialog(Activity myActivity) {
        activity = myActivity;
    }

    public void startAlertDialog(String type, String message, Integer view) {
        dialog = new Dialog(activity);
        dialog.setContentView(view);

        if (type.equals("form")) {
            Button btnSave = dialog.findViewById(R.id.btnSubmit);
            EditText inputSosmed = dialog.findViewById(R.id.edtSosmed);
            Sosmed sosmed = new Sosmed();

            btnSave.setOnClickListener(v -> {
                sosmed.setTitle(inputSosmed.getText().toString());
                insertData(sosmed);
            });
        }

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView btnClose = dialog.findViewById(R.id.btnClose);
        dialog.show();

        btnClose.setOnClickListener(v -> {
            dismissDialog();
        });
    }

    public void dismissDialog() {
        dialog.dismiss();
    }

    private void insertData(final Sosmed sosmed) {
        PasstruRoomDatabase passtruRoomDatabase = PasstruRoomDatabase.getDatabase(activity);
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                // melakukan proses insert data
                long status = passtruRoomDatabase.sosmedDao().insert(sosmed);
                return status;
            }

            @Override
            protected void onPostExecute(Long status) {
                Toast.makeText(activity, "Social Media Added! ", Toast.LENGTH_LONG).show();
                dismissDialog();
            }
        }.execute();
    }
}
