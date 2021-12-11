package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import java.util.List;

import id.roogry.passtru.R;
import id.roogry.passtru.adapter.SosmedAdapter;
import id.roogry.passtru.databinding.ActivityListSosmedBinding;
import id.roogry.passtru.helpers.CustomDialog;
import id.roogry.passtru.helpers.ViewModelFactory;
import id.roogry.passtru.models.Sosmed;
import id.roogry.passtru.viewmodel.ListSosmedViewModel;

public class ListSosmedActivity extends AppCompatActivity {

    private ActivityListSosmedBinding binding;
    private SosmedAdapter sosmedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListSosmedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CustomDialog customDialog = new CustomDialog(ListSosmedActivity.this);
        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        ListSosmedViewModel sosmedViewModel = new ViewModelProvider(this, factory).get(ListSosmedViewModel.class);

        sosmedViewModel.getSosmeds().observe(this, sosmedObersver);

        sosmedAdapter = new SosmedAdapter(this);
        binding.rvSosmed.setLayoutManager(new LinearLayoutManager(this));
        binding.rvSosmed.setHasFixedSize(true);
        binding.rvSosmed.setAdapter(sosmedAdapter);

        binding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.fabAddSosmed.setOnClickListener(v ->{
            customDialog.startAlertDialog("form",-1, R.layout.dialog_add_sosmed);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private final Observer<List<Sosmed>> sosmedObersver = sosmedList -> {
        if (sosmedList != null) {
            sosmedAdapter.setListSosmeds(sosmedList);
        }
    };
}