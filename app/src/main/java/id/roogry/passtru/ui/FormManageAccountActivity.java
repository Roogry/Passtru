package id.roogry.passtru.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
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
import id.roogry.passtru.helpers.CustomDialog;
import id.roogry.passtru.helpers.MoreOptionInterface;
import id.roogry.passtru.helpers.ViewModelFactory;
import id.roogry.passtru.models.Account;
import id.roogry.passtru.models.Sosmed;
import id.roogry.passtru.repository.AccountRepository;
import id.roogry.passtru.viewmodel.ListSosmedViewModel;

public class FormManageAccountActivity extends AppCompatActivity implements MoreOptionInterface {
    public static final String EXTRA_ACCOUNT = "EXTRA_ACCOUNT";

    private Account account;

    private ActivityFormManageAccountBinding binding;
    private ArrayAdapter<Sosmed> spinnerAdapter;
    private ArrayList<Sosmed> listSosmeds;
    private AccountRepository accountRepository;
    private int selectedSosmedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormManageAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        accountRepository = new AccountRepository(this.getApplication());

        //load spinner data
        loadSosmedSpinner();
        account = getIntent().getParcelableExtra(EXTRA_ACCOUNT);
        if (account != null) {
            setupEditForm();
        }else {
            account = new Account();
        }

        binding.btnSubmit.setOnClickListener(v -> {
            String username = binding.edtUsername.getText().toString();
            String password = binding.edtPassword.getText().toString();

            account.setIdSosmed(selectedSosmedId);
            account.setUsername(username);
            account.setPassword(password);

            Log.d("BLabla", "id is : " + account.getId());

            if (account.getId() != 0) {
                updateAccount();
            } else {
                saveAccount();
            }

            Intent intent = new Intent(FormManageAccountActivity.this, ListAccountActivity.class);
            startActivity(intent);
            finish();
        });

        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.ivMore.setOnClickListener(v -> showPopupMenu(v));

        binding.spSosmed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) {
                    Sosmed sosmed = (Sosmed) parent.getItemAtPosition(position);
                    selectedSosmedId = sosmed.id;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.delete_create, popupMenu.getMenu());

        if (account.getId() == 0){
            popupMenu.getMenu().findItem(R.id.deleteAccount).setEnabled(false);
        }

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.deleteAccount:
                    accountRepository.delete(account);
                    onBackPressed();
                    break;
                case R.id.createSosmed:
                    CustomDialog customDialog = new CustomDialog(FormManageAccountActivity.this, R.layout.dialog_add_sosmed);
                    customDialog.startFormSosmed(-1, null, this);
                    break;
            }
            return true;
        });
        popupMenu.show();
    }

    private void setupEditForm() {
        binding.titleForm.setText("Edit Account");
        binding.subtitleForm.setText("Edit your account information here");
        binding.edtUsername.setText(account.getUsername());
        binding.edtPassword.setText(account.getPassword());
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

        sosmedViewModel.getSosmeds().observe(this, sosmedList -> {
            listSosmeds.clear();
            listSosmeds.addAll(sosmedList);

            //notifyDataSetChanged after update termsList variable here
            spinnerAdapter.notifyDataSetChanged();

            //if edit form active
            if (account.getId() != 0) {
                binding.spSosmed.setSelection(getSelectionSpinner(listSosmeds));
            }
        });
    }

    private void saveAccount() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        account.setCreatedAt(dateFormat.format(new Date()));
        Log.d("BLabla", account.getUsername());
        accountRepository.insert(account);
    }

    private void updateAccount() {
        accountRepository.update(account);
    }

    private int getSelectionSpinner(ArrayList<Sosmed> listSosmeds) {
        for (int i = 0; i < binding.spSosmed.getCount(); i++) {
            if (listSosmeds.get(i).getId() == account.getIdSosmed()) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void getDataByPos(int position) {

    }

    @Override
    public void copyPassword(int position) {

    }

    @Override
    public void delete(int position) {

    }

    @Override
    public void updateSosmed(int position, String sosmedTitle) {

    }
}