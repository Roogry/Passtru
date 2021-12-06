package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import id.roogry.passtru.R;
import id.roogry.passtru.adapter.SosmedListAdapter;
import id.roogry.passtru.databinding.ActivityListAccountBinding;
import id.roogry.passtru.databinding.ActivityListSosmedBinding;

public class ListSosmedActivity extends AppCompatActivity {

    private ActivityListSosmedBinding binding;
    private SosmedListAdapter sosmedListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListSosmedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sosmedListAdapter = new SosmedListAdapter();
        binding.rvSosmedList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvSosmedList.setAdapter(sosmedListAdapter);

        binding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}