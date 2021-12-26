package id.roogry.passtru.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import id.roogry.passtru.adapter.AccountAdapter;
import id.roogry.passtru.databinding.ActivityListAccountBinding;
import id.roogry.passtru.helpers.ViewModelFactory;
import id.roogry.passtru.models.AccountAndSosmed;
import id.roogry.passtru.viewmodel.ListAccountViewModel;

public class ListAccountActivity extends AppCompatActivity {

    private ActivityListAccountBinding binding;
    private AccountAdapter accountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        ListAccountViewModel listAccountViewModel = new ViewModelProvider(this, factory).get(ListAccountViewModel.class);

        listAccountViewModel.getAccounts().observe(this, accountObersver);

        accountAdapter = new AccountAdapter(this);
        binding.rvAccount.setLayoutManager(new LinearLayoutManager(this));
        binding.rvAccount.setHasFixedSize(true);
        binding.rvAccount.setAdapter(accountAdapter);

        binding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.fabAddAccount.setOnClickListener(v ->{
            Intent intent = new Intent(ListAccountActivity.this, FormManageAccountActivity.class);
            startActivity(intent);
        });
    }


    private final Observer<List<AccountAndSosmed>> accountObersver = accountList -> {
        if (accountList != null) {
            accountAdapter.setListAccounts(accountList);
        }

        if (accountList.size() > 0){
            binding.emptyHolder.setVisibility(View.INVISIBLE);
        }else{
            binding.emptyHolder.setVisibility(View.VISIBLE);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}