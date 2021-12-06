package id.roogry.passtru.helpers;

import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ActivityLockScreenBinding;
import id.roogry.passtru.ui.LockScreenActivity;
import id.roogry.passtru.ui.MainActivity;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private final Context context;
    private final ActivityLockScreenBinding binding;

    public FingerprintHandler(Context context, ActivityLockScreenBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
        binding.viewFingerprint.setColorFilter(ContextCompat.getColor(context, R.color.white));
        binding.tvStatusFingerPrint.setTextColor(ContextCompat.getColor(context, R.color.white));
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        if (errorCode == 7) {
            binding.pinInput.setVisibility(View.VISIBLE);
            binding.viewFingerprint.setVisibility(View.GONE);
            this.update("Sorry too many attempts. Please wait 30s to use FingerPrint again.", false, errorCode);
        }else {
            this.update(errString.toString(), false, errorCode);
        }


    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();

        this.update("Finger Print Invalid", false, 0);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
        this.update(helpString.toString(), false, 0);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        this.update("Success", true, 0);

        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((LockScreenActivity) context).finish();
    }


    private void update(String string, boolean status , Integer code) {
        binding.tvStatusFingerPrint.setText(string);
        if (!status) {
            if (code != 5){
                binding.tvStatusFingerPrint.setTextColor(ContextCompat.getColor(context, R.color.red));
                binding.viewFingerprint.setColorFilter(ContextCompat.getColor(context, R.color.red));
            }

        } else {
            binding.viewFingerprint.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_check_circle_outline_24));
            binding.viewFingerprint.setColorFilter(ContextCompat.getColor(context, R.color.white));
            binding.tvStatusFingerPrint.setTextColor(ContextCompat.getColor(context, R.color.white));

        }
    }

}
