package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ActivityFormManageAccountBinding;
import id.roogry.passtru.databinding.ActivityHomeBinding;

public class FormManageAccountActivity extends AppCompatActivity {
    private ActivityFormManageAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFormManageAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}