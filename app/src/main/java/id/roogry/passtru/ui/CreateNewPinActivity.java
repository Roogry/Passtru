package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ActivityCreateNewPinBinding;
import id.roogry.passtru.databinding.ActivityResetPinBinding;

public class CreateNewPinActivity extends AppCompatActivity {
    private ActivityCreateNewPinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNewPinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.btnSubmit.setOnClickListener(v -> {
            String pin = binding.edtNewPin.getText().toString();
            String confrimPin = binding.edtConfimPin.getText().toString();

            if (pin.trim().equals("") || confrimPin.trim().equals("")){
                Toast.makeText(CreateNewPinActivity.this, "Form Must be fill", Toast.LENGTH_SHORT).show();
            }else{
                if (pin.equals(confrimPin)){

                    SharedPreferences sharedPref = getSharedPreferences("PREF", 0);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear();
                    editor.putString("PIN", pin);
                    editor.apply();

                    Intent intent = new Intent(CreateNewPinActivity.this, LockScreenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(CreateNewPinActivity.this, "Pin not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}