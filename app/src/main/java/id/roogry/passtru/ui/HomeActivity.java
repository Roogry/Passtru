package id.roogry.passtru.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import id.roogry.passtru.R;
import id.roogry.passtru.adapter.RecentlyAddAdapter;
import id.roogry.passtru.adapter.SosmedBadgeAdapter;
import id.roogry.passtru.databinding.ActivityHomeBinding;
import id.roogry.passtru.helpers.ViewModelFactory;
import id.roogry.passtru.models.Account;
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
        binding.rvSosmed.setHasFixedSize(true);
        binding.rvSosmed.setAdapter(sosmedBadgeAdapter);

        binding.rvRecent.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRecent.setHasFixedSize(true);
        binding.rvRecent.setAdapter(recentlyAddAdapter);
    }

    private final Observer<List<Sosmed>> sosmedObserver = sosmedList -> {
        if (sosmedList != null) {
            sosmedBadgeAdapter.setListSosmeds(sosmedList);
        }
    };

    private final Observer<List<Account>> accountObserver = accountList -> {
        if (accountList != null) {
            recentlyAddAdapter.setListAccounts(accountList);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}