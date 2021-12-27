package id.roogry.passtru.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ActivityFormManageAccountBinding;
import id.roogry.passtru.helpers.dialog.CustomDialog;
import id.roogry.passtru.helpers.dialog.SosmedFormInterface;
import id.roogry.passtru.helpers.ToastMessage;
import id.roogry.passtru.helpers.AESUtils;
import id.roogry.passtru.helpers.ViewModelFactory;
import id.roogry.passtru.models.Account;
import id.roogry.passtru.models.Sosmed;
import id.roogry.passtru.viewmodel.FormManageAccountViewModel;

public class FormManageAccountActivity extends AppCompatActivity implements SosmedFormInterface {
    public static final String EXTRA_ACCOUNT = "EXTRA_ACCOUNT";

    private Account account;

    private ActivityFormManageAccountBinding binding;
    private ArrayAdapter<Sosmed> spinnerAdapter;
    private ArrayList<Sosmed> listSosmeds;
    private FormManageAccountViewModel viewModel;
    private int selectedSosmedId;

    public static String decrypt(String password) {
        String decrypted = "";
        try {
            decrypted = AESUtils.decrypt(password);
            Log.d("TEST", "decrypted:" + decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decrypted;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormManageAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        viewModel = new ViewModelProvider(this, factory).get(FormManageAccountViewModel.class);

        //load spinner data
        loadSosmedSpinner();
        account = getIntent().getParcelableExtra(EXTRA_ACCOUNT);
        if (account != null) {
            setupEditForm();
        } else {
            account = new Account();
        }

        binding.btnSubmit.setOnClickListener(v -> {
            String username = binding.edtUsername.getText().toString();
            String password = binding.edtPassword.getText().toString();
            String encryptedPwd = encrypt(password);

            account.setIdSosmed(selectedSosmedId);
            account.setUsername(username);
            account.setPassword(encryptedPwd);

            if (CheckAllFields()) {
                if (account.getId() != 0) {
                    updateAccount();
                } else {
                    saveAccount();
                }
                finish();
            }
        });

        binding.ivBack.setOnClickListener(v -> onBackPressed());
        binding.ivMore.setOnClickListener(v -> showPopupMenu(v));

        binding.spSosmed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) {
                    Sosmed sosmed = (Sosmed) parent.getItemAtPosition(position);
                    selectedSosmedId = sosmed.id;

                    resetErrorSpSosmed();
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

        if (account.getId() == 0) {
            popupMenu.getMenu().findItem(R.id.deleteAccount).setEnabled(false);
        }

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.deleteAccount:
                    viewModel.delete(account);
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
        binding.edtPassword.setText(decrypt(account.getPassword()));
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

        viewModel.getSosmeds().observe(this, sosmedList -> {
            listSosmeds.clear();
            listSosmeds.addAll(sosmedList);

            if (listSosmeds.isEmpty()){
                binding.alertToAddSosmed.setVisibility(View.VISIBLE);
                binding.spSosmed.setEnabled(false);
            }else{
                resetErrorSpSosmed();
            }
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

        viewModel.insert(account);
        ToastMessage.showInsertedMessage(this, account.getUsername());
    }

    private void updateAccount() {
        viewModel.update(account);
        ToastMessage.showUpdatedMessage(this, account.getUsername());
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
    public void insert(String title) {
        Sosmed sosmed = new Sosmed(title);
        viewModel.insertSosmed(sosmed);
        ToastMessage.showInsertedMessage(this, title);
    }

    @Override
    public void update(int position, String sosmedTitle) {

    }

    private String encrypt(String password) {
        String encrypted = "";
        try {
            encrypted = AESUtils.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    private void resetErrorSpSosmed() {
        binding.tvLblSosmed.setTextColor(ContextCompat.getColor(this, R.color.white));
        binding.alertToAddSosmed.setVisibility(View.GONE);
        binding.spSosmed.setEnabled(true);

        binding.spSosmed.setBackground(ContextCompat.getDrawable(
                this,
                R.drawable.bg_spinner_dark
        ));
    }

    private boolean CheckAllFields() {
        boolean isValid = true;

        if (binding.spSosmed.getSelectedItem() == null) {
            binding.tvLblSosmed.setTextColor(ContextCompat.getColor(this, R.color.red));
            binding.spSosmed.setBackground(ContextCompat.getDrawable(
                    this,
                    R.drawable.bg_spinner_dark_error
            ));
            isValid = false;
        }else{
            resetErrorSpSosmed();
        }

        if (binding.edtUsername.getText().toString().trim().length() == 0) {
            binding.tvLblUsername.setTextColor(ContextCompat.getColor(this, R.color.red));
            binding.edtUsername.setError("This field is required");
            binding.edtUsername.setBackground(ContextCompat.getDrawable(
                    this,
                    R.drawable.bg_input_dark_error
            ));
            isValid = false;
        }else{
            binding.tvLblUsername.setTextColor(ContextCompat.getColor(this, R.color.white));
            binding.edtUsername.setError(null);
            binding.edtUsername.setBackground(ContextCompat.getDrawable(
                    this,
                    R.drawable.bg_input_dark
            ));
        }

        if (binding.edtPassword.getText().toString().trim().length() == 0) {
            binding.tvLblPassword.setTextColor(ContextCompat.getColor(this, R.color.red));
            binding.edtPassword.setError("This field is required");
            binding.edtPassword.setBackground(ContextCompat.getDrawable(
                    this,
                    R.drawable.bg_input_dark_error
            ));
            isValid = false;
        }else{
            binding.tvLblPassword.setTextColor(ContextCompat.getColor(this, R.color.white));
            binding.edtPassword.setError(null);
            binding.edtPassword.setBackground(ContextCompat.getDrawable(
                    this,
                    R.drawable.bg_input_dark
            ));
        }

        // after all validation return true.
        return isValid;
    }
}