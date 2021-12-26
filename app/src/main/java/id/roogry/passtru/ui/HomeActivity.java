package id.roogry.passtru.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import id.roogry.passtru.adapter.RecentlyAddAdapter;
import id.roogry.passtru.adapter.SosmedBadgeAdapter;
import id.roogry.passtru.databinding.ActivityHomeBinding;
import id.roogry.passtru.helpers.ViewModelFactory;
import id.roogry.passtru.models.AccountAndSosmed;
import id.roogry.passtru.models.Sosmed;
import id.roogry.passtru.viewmodel.HomeViewModel;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private SosmedBadgeAdapter sosmedBadgeAdapter;
    private RecentlyAddAdapter recentlyAddAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        HomeViewModel mainViewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
        mainViewModel.getSosmeds().observe(this, sosmedObserver);
        mainViewModel.getAccounts().observe(this, accountObserver);

        sosmedBadgeAdapter = new SosmedBadgeAdapter(HomeActivity.this);
        recentlyAddAdapter = new RecentlyAddAdapter(HomeActivity.this);

        binding.rvSosmed.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvRecent.setNestedScrollingEnabled(false);
        binding.rvSosmed.setAdapter(sosmedBadgeAdapter);

        binding.rvRecent.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRecent.setNestedScrollingEnabled(false);
        binding.rvRecent.setAdapter(recentlyAddAdapter);

        binding.sosmedAll.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ListSosmedActivity.class);
            startActivity(intent);
        });

        binding.sosmedSeeAll.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ListSosmedActivity.class);
            startActivity(intent);
        });

        binding.recentAll.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ListAccountActivity.class);
            startActivity(intent);
        });

        binding.rlNewAccount.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, FormManageAccountActivity.class);
            startActivity(intent);
        });
    }

    private final Observer<List<Sosmed>> sosmedObserver = sosmedList -> {
        if (sosmedList != null) {
            sosmedBadgeAdapter.setListSosmeds(sosmedList);
        }

        if (sosmedList.size() > 0){
            binding.sosmedTitle.setVisibility(View.VISIBLE);
            binding.sosmedAll.setVisibility(View.VISIBLE);
            binding.sosmedSeeAll.setVisibility(View.VISIBLE);
            binding.rvSosmed.setVisibility(View.VISIBLE);
        }else{
            binding.sosmedTitle.setVisibility(View.GONE);
            binding.sosmedAll.setVisibility(View.GONE);
            binding.sosmedSeeAll.setVisibility(View.GONE);
            binding.rvSosmed.setVisibility(View.GONE);
        }
    };

    private final Observer<List<AccountAndSosmed>> accountObserver = accountList -> {
        if (accountList != null) {
            recentlyAddAdapter.setListAccounts(accountList);
        }

        if (accountList.size() > 0){
            binding.recentTitle.setVisibility(View.VISIBLE);
            binding.recentAll.setVisibility(View.VISIBLE);
            binding.rvRecent.setVisibility(View.VISIBLE);
        }else{
            binding.recentTitle.setVisibility(View.GONE);
            binding.recentAll.setVisibility(View.GONE);
            binding.rvRecent.setVisibility(View.GONE);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}