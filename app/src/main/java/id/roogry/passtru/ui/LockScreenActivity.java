package id.roogry.passtru.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Locale;

import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ActivityLockScreenBinding;
import id.roogry.passtru.helpers.FingerprintHandler;

public class LockScreenActivity extends AppCompatActivity {
    private String KEY_NAME = "AndroidKey" ;
    private ActivityLockScreenBinding binding;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private Cipher chiper;
    private String pinCheck;
    private SharedPreferences pin;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLocale(this, "en");
        super.onCreate(savedInstanceState);
        binding = ActivityLockScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        pin = getSharedPreferences("PREF", 0);
        pinCheck = pin.getString("PIN", "");

//    FingerPrint Security
        fingerPrintAuth();

//     Pin Security
        binding.edtPIN.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String pinInput = binding.edtPIN.getText().toString();
                if (pinInput.equals(pinCheck)){
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)){
                        Intent intent = new Intent(LockScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    return true;
                }else{
                    binding.tvStatusFingerPrint.setTextColor(ContextCompat.getColor(LockScreenActivity.this, R.color.red));
                    binding.tvStatusFingerPrint.setText("Pin Is Wrong");
                    return false;
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (pinCheck.equals("")){
            Intent intent = new Intent(LockScreenActivity.this, SetPinActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fingerPrintAuth();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateKey(){
        try {
            keyStore = KeyStore.getInstance("AndroidKeySTore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore");
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

        } catch (KeyStoreException | IOException | CertificateException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e){
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean chiperInit(){
        try {
            chiper = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e){
            throw  new RuntimeException("failed to get Chiper");
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            chiper.init(Cipher.ENCRYPT_MODE, key);
            return true;
        }catch (KeyPermanentlyInvalidatedException e){
            return  false;
        }catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e){
            throw new RuntimeException("failed to init Chiper", e);
        }
    }

    private  void fingerPrintAuth(){
        //        FingerPrint Security
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            binding.viewFingerprint.setVisibility(View.GONE);

            if (!fingerprintManager.isHardwareDetected()){
                binding.tvStatusFingerPrint.setText("Fingeprint not found. Cannot Active fingerPrint security");

            }else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
                binding.tvStatusFingerPrint.setText("FingerPrint Permission Denied");

            }else if(!keyguardManager.isKeyguardSecure()){
                binding.tvStatusFingerPrint.setText("Secure Your Lockcreen First And Active The FingerPrint From Setting To Active FingerPrint Security");

            }else if(!fingerprintManager.hasEnrolledFingerprints()){
                binding.tvStatusFingerPrint.setText("To Active FingerPrint Security, Please Turn On The FingerPrint In Setting");

            }else{
                binding.pinInput.setVisibility(View.GONE);
                binding.viewFingerprint.setVisibility(View.VISIBLE);
                binding.tvStatusFingerPrint.setText("Scan your fingeprint");
                generateKey();
                if (chiperInit()){
                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(chiper);
                    FingerprintHandler fingerprintHandler = new FingerprintHandler(this, binding);
                    fingerprintHandler.startAuth(fingerprintManager, cryptoObject);
                }
            }
        }
    }
    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}