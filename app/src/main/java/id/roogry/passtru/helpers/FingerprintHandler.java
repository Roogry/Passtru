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
import id.roogry.passtru.ui.HomeActivity;
import id.roogry.passtru.ui.LockScreenActivity;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private final Context context;
    private final ActivityLockScreenBinding binding;
    private final CancellationSignal cancellationSignal;

    public FingerprintHandler(Context context, ActivityLockScreenBinding binding) {
        this.context = context;
        this.binding = binding;
        this.cancellationSignal = new CancellationSignal();
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
        binding.ivFingerprint.setColorFilter(ContextCompat.getColor(context, R.color.white));
        binding.tvStatusFingerprint.setTextColor(ContextCompat.getColor(context, R.color.white));
    }

    public void cancelFingerprint(){
        cancellationSignal.cancel();
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        if (errorCode == 7) {
            binding.pinContainer.setVisibility(View.VISIBLE);
            binding.fingerprintContainer.setVisibility(View.GONE);
            this.update("Sorry too many attempts. Please wait 30s to use FingerPrint again.", false, errorCode);
        }else if (errorCode == 5){
            this.update("Scan your fingerprint", false, errorCode);
        }
        else {
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

        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
        ((LockScreenActivity) context).finish();
    }

    private void update(String string, boolean status , Integer code) {
        binding.tvStatusFingerprint.setText(string);
        if (!status) {
            if (code != 5){
                binding.tvStatusFingerprint.setTextColor(ContextCompat.getColor(context, R.color.red));
                binding.ivFingerprint.setColorFilter(ContextCompat.getColor(context, R.color.red));
            }

        } else {
            binding.ivFingerprint.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_check_circle_outline_24));
            binding.ivFingerprint.setColorFilter(ContextCompat.getColor(context, R.color.white));
            binding.tvStatusFingerprint.setTextColor(ContextCompat.getColor(context, R.color.white));

        }
    }

}
