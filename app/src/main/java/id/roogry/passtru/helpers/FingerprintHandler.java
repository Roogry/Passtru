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

import id.roogry.passtru.ui.LockScreenActivity;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private Context context;

    public FingerprintHandler(Context context){
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal , 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        LinearLayout linearLayout = ((Activity)context).findViewById(R.id.pinInput);
        ImageView fingerPrintIcon = ((Activity)context).findViewById(R.id.viewFingerprint);
        if (errorCode == 7){
            linearLayout.setVisibility(View.VISIBLE);
            fingerPrintIcon.setVisibility(View.GONE);
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
        TextView paraLabel = ((Activity)context).findViewById(R.id.tvStatusFingerPrint);
        ImageView iconLabel = ((Activity)context).findViewById(R.id.viewFingerprint);
        paraLabel.setText(string);
        if (status == false){
            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.red));
            iconLabel.setColorFilter(ContextCompat.getColor(context, R.color.red));

        }else{
            iconLabel.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_check_circle_outline_24));
            iconLabel.setColorFilter(ContextCompat.getColor(context, R.color.white));
            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.white));


        }
    }

}
