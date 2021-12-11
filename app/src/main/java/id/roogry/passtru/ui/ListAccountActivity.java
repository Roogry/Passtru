package id.roogry.passtru.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import id.roogry.passtru.adapter.AccountAdapter;
import id.roogry.passtru.adapter.RecentlyAddAdapter;
import id.roogry.passtru.adapter.SosmedAdapter;
import id.roogry.passtru.databinding.ActivityListAccountBinding;
import id.roogry.passtru.helpers.ViewModelFactory;
import id.roogry.passtru.models.Account;
import id.roogry.passtru.viewmodel.HomeViewModel;

public class ListAccountActivity extends AppCompatActivity {

    private ActivityListAccountBinding binding;
    private AccountAdapter accountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        HomeViewModel mainViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
        mainViewModel.getAccounts().observe(this, accountObserver);

        accountAdapter = new AccountAdapter();
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

    private final Observer<List<Account>> accountObserver = accountList -> {
        if (accountList != null) {
            accountAdapter.setListAccounts(accountList);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}