package id.roogry.passtru.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ActivityFormManageAccountBinding;
import id.roogry.passtru.helpers.ViewModelFactory;
import id.roogry.passtru.models.Account;
import id.roogry.passtru.models.Sosmed;
import id.roogry.passtru.repository.AccountRepository;
import id.roogry.passtru.viewmodel.ListSosmedViewModel;

public class FormManageAccountActivity extends AppCompatActivity {
    private ActivityFormManageAccountBinding binding;
    private ArrayAdapter<Sosmed> spinnerAdapter;
    private ArrayList<Sosmed> listSosmeds;
    private AccountRepository accountRepository;
    private String username, password;
    private Integer idSosmed;
    private Bundle bundle;
    private SimpleDateFormat createdAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormManageAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        accountRepository = new AccountRepository(this.getApplication());


        //load spinner data
        loadSosmedSpinner();
        if (getIntent().getBundleExtra("isEdit") != null){
            binding.titleForm.setText("Edit Account");
            binding.subtitleForm.setText("Edit your account information here");
            editForm();
        }
        binding.btnSubmit.setOnClickListener(v -> {
            if (getIntent().getBundleExtra("isEdit") != null) {
                updateAccount();
            }else{
                saveAccount();

            }
            Intent intent = new Intent(FormManageAccountActivity.this, ListAccountActivity.class);
            startActivity(intent);
            finish();
        });

        binding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.ivMore.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.getMenuInflater().inflate(R.menu.delete_create, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.deleteAccount:
                        Toast.makeText(FormManageAccountActivity.this, "Delete Account Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.createSosmed:
                        Toast.makeText(FormManageAccountActivity.this, "Create Social Media Clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            });
            popupMenu.show();
        });

        binding.spSosmed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) {
                    Sosmed sosmed = (Sosmed) parent.getItemAtPosition(position);
                    idSosmed = sosmed.id;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void loadSosmedSpinner() {
        listSosmeds = new ArrayList<>();
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listSosmeds) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                TextView textView = ((TextView) v);
                textView.setText(listSosmeds.get(position).title);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                return v;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                TextView textView = ((TextView) v);
                textView.setText(listSosmeds.get(position).title);
                return v;
            }
        };

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spSosmed.setAdapter(spinnerAdapter);

        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        ListSosmedViewModel sosmedViewModel = new ViewModelProvider(this, factory).get(ListSosmedViewModel.class);

        sosmedViewModel.getSosmeds().observe(this, new Observer<List<Sosmed>>() {
            @Override
            public void onChanged(List<Sosmed> sosmedList) {
                listSosmeds.clear();
                listSosmeds.addAll(sosmedList);

                //notifyDataSetChanged after update termsList variable here
                spinnerAdapter.notifyDataSetChanged();

                //if edit form active
                if (getIntent().getBundleExtra("isEdit") != null){
                    binding.spSosmed.setSelection(getSelectionSpinner(listSosmeds));
                }

            }


        });

    }

    private void saveAccount() {
        username = binding.edtUsername.getText().toString();
        password = binding.edtPassword.getText().toString();
        createdAt = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");

        Account account = new Account(idSosmed, username, password, createdAt.format(new Date()));
        accountRepository.insert(account);
    }
    private void updateAccount() {
        username = binding.edtUsername.getText().toString();
        password = binding.edtPassword.getText().toString();
        createdAt = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");


    }

    private void editForm(){
        bundle = getIntent().getBundleExtra("isEdit");
        binding.edtUsername.setText(bundle.getString("username"));
        binding.edtPassword.setText(bundle.getString("password"));

    }

    private int getSelectionSpinner(ArrayList<Sosmed> listSosmeds){
        for (int i = 0; i < binding.spSosmed.getCount(); i++) {
            if (listSosmeds.get(i).getId() == bundle.getInt("idSosmed")) {
                return i;
            }
        }
        return 0;
    }

}