package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import id.roogry.passtru.databinding.ActivityHomeBinding;
import id.roogry.passtru.databinding.ActivityLockScreenBinding;
import id.roogry.passtru.databinding.ActivitySetPinBinding;

public class SetPinActivity extends AppCompatActivity {
    private ActivitySetPinBinding binding;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySetPinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submitPin.setOnClickListener(v -> {
            String pin = binding.inputPin.getText().toString();
            String confrimPin = binding.confirmPin.getText().toString();

            if (pin.trim().equals("") || confrimPin.trim().equals("")){
                Toast.makeText(SetPinActivity.this, "Form Must be fill", Toast.LENGTH_SHORT).show();
            }else{
                if (pin.equals(confrimPin)){

                    sharedPref = getSharedPreferences("PREF", 0);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("PIN", pin);
                    editor.apply();

                    Intent intent = new Intent(SetPinActivity.this, LockScreenActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(SetPinActivity.this, "Pin not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}