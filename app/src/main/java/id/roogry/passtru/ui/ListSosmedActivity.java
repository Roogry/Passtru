package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import id.roogry.passtru.R;
import id.roogry.passtru.adapter.SosmedAdapter;
import id.roogry.passtru.databinding.ActivityListSosmedBinding;
import id.roogry.passtru.helpers.dialog.CustomDialog;
import id.roogry.passtru.helpers.dialog.SosmedFormInterface;
import id.roogry.passtru.helpers.ToastMessage;
import id.roogry.passtru.helpers.ViewModelFactory;
import id.roogry.passtru.models.Sosmed;
import id.roogry.passtru.viewmodel.ListSosmedViewModel;

public class ListSosmedActivity extends AppCompatActivity implements SosmedFormInterface {

    private ActivityListSosmedBinding binding;
    private SosmedAdapter sosmedAdapter;
    private ListSosmedViewModel sosmedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListSosmedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CustomDialog customDialog = new CustomDialog(ListSosmedActivity.this, R.layout.dialog_add_sosmed);
        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        sosmedViewModel = new ViewModelProvider(this, factory).get(ListSosmedViewModel.class);

        sosmedViewModel.getSosmeds().observe(this, sosmedObserver);

        sosmedAdapter = new SosmedAdapter(this);
        binding.rvSosmed.setLayoutManager(new LinearLayoutManager(this));
        binding.rvSosmed.setHasFixedSize(true);
        binding.rvSosmed.setAdapter(sosmedAdapter);

        binding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.fabAddSosmed.setOnClickListener(v ->{
            customDialog.startFormSosmed(-1, null, this);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private final Observer<List<Sosmed>> sosmedObserver = sosmedList -> {
        if (sosmedList != null) {
            sosmedAdapter.setListSosmeds(sosmedList);
        }

        if (sosmedList.size() > 0){
            binding.emptyHolder.setVisibility(View.INVISIBLE);
        }else{
            binding.emptyHolder.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void insert(String title) {
        Sosmed sosmed = new Sosmed(title);
        sosmedViewModel.insert(sosmed);
        ToastMessage.showInsertedMessage(this, title);
    }

    @Override
    public void update(int position, String sosmedTitle) {

    }
}