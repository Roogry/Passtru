package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import id.roogry.passtru.R;
import id.roogry.passtru.adapter.SosmedListAdapter;
import id.roogry.passtru.databinding.ActivityListAccountBinding;
import id.roogry.passtru.databinding.ActivityListSosmedBinding;
import id.roogry.passtru.helpers.CustomDialog;

public class ListSosmedActivity extends AppCompatActivity {

    private ActivityListSosmedBinding binding;
    private SosmedListAdapter sosmedListAdapter;
    private CustomDialog customDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListSosmedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        customDialog = new CustomDialog(ListSosmedActivity.this);
        sosmedListAdapter = new SosmedListAdapter();
        binding.rvSosmedList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvSosmedList.setAdapter(sosmedListAdapter);

        binding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.addBtnSosmed.setOnClickListener(v ->{
         customDialog.startAlertDialog("form",null,R.layout.dialog_add_sosmed);
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}