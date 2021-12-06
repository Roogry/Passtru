package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import id.roogry.passtru.R;
import id.roogry.passtru.adapter.AccountListAdapter;
import id.roogry.passtru.databinding.ActivityListAccountBinding;

public class ListAccountActivity extends AppCompatActivity {

    private ActivityListAccountBinding binding;
    private AccountListAdapter accountListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        accountListAdapter = new AccountListAdapter();
        binding.rvAccountList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvAccountList.setAdapter(accountListAdapter);

        binding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.addBtnAccount.setOnClickListener(v ->{
            Intent intent = new Intent(ListAccountActivity.this, FormManageAccountActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }


}