package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ActivityResetPinBinding;
import id.roogry.passtru.helpers.AESUtils;
import id.roogry.passtru.helpers.ViewModelFactory;
import id.roogry.passtru.models.Account;
import id.roogry.passtru.repository.AccountRepository;
import id.roogry.passtru.repository.SosmedRepository;
import id.roogry.passtru.viewmodel.HomeViewModel;
import id.roogry.passtru.viewmodel.ResetPinViewModel;

public class ResetPinActivity extends AppCompatActivity {
    private ActivityResetPinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSubmit.setOnClickListener(v -> {
            checkAccount();
        });

        binding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void checkAccount(){
        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        ResetPinViewModel resetPinViewModel = new ViewModelProvider(this, factory).get(ResetPinViewModel.class);

        String username = binding.edtUsername.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();

        resetPinViewModel.getAccounts(username, encrypt(password)).observe(ResetPinActivity.this, accounts -> {
            if (accounts.size() > 0){
                Intent intent = new Intent(ResetPinActivity.this, CreateNewPinActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(ResetPinActivity.this, "Account Not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String encrypt(String password) {
        String encrypted = "";
        try {
            encrypted = AESUtils.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }
}

