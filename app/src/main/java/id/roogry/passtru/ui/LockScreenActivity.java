package id.roogry.passtru.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ActivityLockScreenBinding;
import id.roogry.passtru.helpers.FingerprintHandler;
import id.roogry.passtru.helpers.ViewModelFactory;
import id.roogry.passtru.models.Account;
import id.roogry.passtru.models.AccountAndSosmed;
import id.roogry.passtru.viewmodel.ListAccountViewModel;
import id.roogry.passtru.viewmodel.ResetPinViewModel;

public class LockScreenActivity extends AppCompatActivity {
    private final String KEY_NAME = "AndroidKey";
    private ActivityLockScreenBinding binding;

    private FingerprintHandler fingerprintHandler;
    private KeyStore keyStore;
    private Cipher chipper;
    private String pinCheck;

    private boolean isFingerprint = true;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLocale(this, "en");
        super.onCreate(savedInstanceState);

        binding = ActivityLockScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //check Pin On Shared Preference
        SharedPreferences sharedPref = getSharedPreferences("PREF", 0);
        pinCheck = sharedPref.getString("PIN", "");
        //FingerPrint Security
        setFingerprintMethod();
        pinAuth();

        binding.tvMethodFinger.setOnClickListener(v -> setFingerprintMethod());
        binding.tvMethodPin.setOnClickListener(v -> setPinMethod());
        binding.tvForgotPIN.setOnClickListener(v -> checkAccountSize());
        binding.btnSubmit.setOnClickListener(v -> submitPin());
    }
    

    private void setPinMethod() {
        isFingerprint = false;

        binding.pinContainer.setVisibility(View.VISIBLE);
        binding.fingerprintContainer.setVisibility(View.GONE);

        fingerprintHandler.cancelFingerprint();
    }

    private void setFingerprintMethod() {
        isFingerprint = true;

        binding.pinContainer.setVisibility(View.GONE);
        binding.fingerprintContainer.setVisibility(View.VISIBLE);

        fingerPrintAuth();
    }

    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();

        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (pinCheck.equals("")) {
            fingerprintHandler = new FingerprintHandler(this, binding);
            fingerprintHandler.cancelFingerprint();
            Intent intent = new Intent(LockScreenActivity.this, SetPinActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isFingerprint) {
            setFingerprintMethod();
        } else {
            //check Pin On Shared Preference
            SharedPreferences sharedPref = getSharedPreferences("PREF", 0);
            pinCheck = sharedPref.getString("PIN", "");
            setPinMethod();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeySTore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();

        } catch (KeyStoreException | IOException | CertificateException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean chiperInit() {
        try {
            chipper = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("failed to get Chiper");
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            chipper.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("failed to init Chiper", e);
        }
    }

    private void fingerPrintAuth() {
        // FingerPrint Security
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if (!fingerprintManager.isHardwareDetected()) {
                binding.tvStatusFingerprint.setText("Fingeprint not found. Cannot Active fingerPrint security");

            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                binding.tvStatusFingerprint.setText("FingerPrint Permission Denied");

            } else if (!keyguardManager.isKeyguardSecure()) {
                binding.tvStatusFingerprint.setText("Secure Your Lockcreen First And Active The FingerPrint From Setting To Active FingerPrint Security");

            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                binding.tvStatusFingerprint.setText("To Active FingerPrint Security, Please Turn On The FingerPrint In Setting");

            } else {
                generateKey();

                if (chiperInit()) {
                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(chipper);
                    fingerprintHandler = new FingerprintHandler(this, binding);
                    fingerprintHandler.startAuth(fingerprintManager, cryptoObject);
                }
            }
        }
    }

    private void pinAuth() {
        binding.edtPIN.setOnKeyListener((v, keyCode, event) -> {
            String pinInput = binding.edtPIN.getText().toString();

            if (pinInput.trim().length() > 0){
                binding.edtPIN.setError(null);
                binding.edtPIN.setBackground(ContextCompat.getDrawable(
                        this,
                        R.drawable.bg_input_light
                ));
            }

            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                submitPin();
            }
            return false;
        });
    }

    private void submitPin() {
        String pinInput = binding.edtPIN.getText().toString();

        if (pinInput.equals(pinCheck)) {
            Intent intent = new Intent(LockScreenActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            binding.edtPIN.setError("PIN not correct");
            binding.edtPIN.setBackground(ContextCompat.getDrawable(
                    this,
                    R.drawable.bg_input_light_error
            ));
        }
    }

    private void checkAccountSize(){
        ViewModelFactory factory = ViewModelFactory.getInstance(this.getApplication());
        ListAccountViewModel listPinViewModel = new ViewModelProvider(this, factory).get(ListAccountViewModel.class);

        listPinViewModel.getAccounts().observe(LockScreenActivity.this, accountAndSosmeds -> {
            Intent intent;

            if (accountAndSosmeds.size() == 0){
                intent = new Intent(LockScreenActivity.this, CreateNewPinActivity.class);
            }else {
                accountAndSosmeds.size();
                intent = new Intent(LockScreenActivity.this, ResetPinActivity.class);
            }
            startActivity(intent);
        });
    }
}