package id.roogry.passtru.helpers;

import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import id.roogry.passtru.R;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.io.Console;

import id.roogry.passtru.databinding.ActivityLockScreenBinding;
import id.roogry.passtru.ui.LockScreenActivity;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private Context context;
    private ActivityLockScreenBinding binding;
    public FingerprintHandler(Context context, ActivityLockScreenBinding binding){
        this.context = context;
        this.binding = binding;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal , 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        if (errorCode == 7){
            binding.pinInput.setVisibility(View.VISIBLE);
            binding.viewFingerprint.setVisibility(View.GONE);
        }
        this.update("Mohon maaf " + errString, false);
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();

        this.update("Finger Print Tidak Cocok", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
        this.update("Mohon maaf " + helpString, false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        this.update("Success", true);
    }

    private  void update(String string, boolean status){
        binding.tvStatusFingerPrint.setText(string);
        if (!status){
            binding.tvStatusFingerPrint.setTextColor(ContextCompat.getColor(context, R.color.red));
            binding.viewFingerprint.setColorFilter(ContextCompat.getColor(context, R.color.red));

        }else{
            binding.viewFingerprint.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_check_circle_outline_24));
            binding.viewFingerprint.setColorFilter(ContextCompat.getColor(context, R.color.white));
            binding.tvStatusFingerPrint.setTextColor(ContextCompat.getColor(context, R.color.white));


        }
    }

}
