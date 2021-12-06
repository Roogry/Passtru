package id.roogry.passtru.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
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

import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import id.roogry.passtru.R;
import id.roogry.passtru.helpers.FingerprintHandler;

public class LockScreenActivity extends AppCompatActivity {
    private String KEY_NAME = "AndroidKey" ;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private TextView fingerprintStatus;
    private KeyStore keyStore;
    private Cipher chiper;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        fingerprintStatus = findViewById(R.id.tvStatusFingerPrint);

     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
         fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
         keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

         if (!fingerprintManager.isHardwareDetected()){
             fingerprintStatus.setText("Fingeprint not found");

         }else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
             fingerprintStatus.setText("Permission Denied");

         }else if(!keyguardManager.isKeyguardSecure()){
             fingerprintStatus.setText("Secure Your Lockcreen First");

         }else if(!fingerprintManager.hasEnrolledFingerprints()){
             fingerprintStatus.setText("Add fingeprint atleast 1");

         }else{
             fingerprintStatus.setText("Use your fingeprint");
             generateKey();
             if (chiperInit()){
                 FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(chiper);
                 FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                 fingerprintHandler.startAuth(fingerprintManager, cryptoObject);
             }
         }
     }

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
}