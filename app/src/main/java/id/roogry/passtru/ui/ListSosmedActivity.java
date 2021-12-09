package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import id.roogry.passtru.R;
import id.roogry.passtru.adapter.SosmedListAdapter;
import id.roogry.passtru.databinding.ActivityListAccountBinding;
import id.roogry.passtru.databinding.ActivityListSosmedBinding;
import id.roogry.passtru.helpers.CustomDialog;
import id.roogry.passtru.helpers.ViewModelFactory;
import id.roogry.passtru.models.Account;
import id.roogry.passtru.models.Sosmed;
import id.roogry.passtru.viewmodel.HomeViewModel;
import id.roogry.passtru.viewmodel.ListSosmedViewModel;

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

        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        ListSosmedViewModel sosmedViewModel = new ViewModelProvider(this, factory).get(ListSosmedViewModel.class);
//        sosmedViewModel.getSosmeds().observe(this, sosmedObersver);


        sosmedListAdapter = new SosmedListAdapter();
        binding.rvSosmedList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvSosmedList.setHasFixedSize(true);
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

//    private final Observer<List<Sosmed>> sosmedObersver = sosmedList -> {
//        if (sosmedList != null) {
//            Log.d("testData", "ada datanya kok");
////            sosmedListAdapter.setListAccounts(accountList);
//        }
//    };
}