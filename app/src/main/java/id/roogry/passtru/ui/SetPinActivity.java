package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import id.roogry.passtru.databinding.ActivityLockScreenBinding;
import id.roogry.passtru.databinding.ActivitySetPinBinding;

public class SetPinActivity extends AppCompatActivity {
    private ActivitySetPinBinding binding;
    private SharedPreferences pinSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetPinBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.submitPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = binding.inputPin.getText().toString();
                String confrimPin = binding.confirmPin.getText().toString();
                if (pin.trim().equals("") || confrimPin.trim().equals("")){
                    Toast.makeText(SetPinActivity.this, "Form Must be fill", Toast.LENGTH_SHORT).show();
                }else{
                    if (pin.equals(confrimPin)){

                        pinSave = getSharedPreferences("PREF", 0);
                        SharedPreferences.Editor editor = pinSave.edit();
                        editor.putString("PIN", pin);
                        editor.apply();
                        Intent intent = new Intent(SetPinActivity.this, LockScreenActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(SetPinActivity.this, "Pin not match", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }
}