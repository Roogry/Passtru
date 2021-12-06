package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import id.roogry.passtru.R;
import id.roogry.passtru.adapter.AccountListAdapter;
import id.roogry.passtru.databinding.ActivityHomeBinding;
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}