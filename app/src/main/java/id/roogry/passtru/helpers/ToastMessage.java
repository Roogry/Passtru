package id.roogry.passtru.helpers;

import android.app.Activity;
import android.widget.Toast;

import id.roogry.passtru.R;

public class ToastMessage {
    public static void showInsertedMessage(Activity activity, String name){
        Toast.makeText(activity, name + " "+ activity.getResources().getString(R.string.created), Toast.LENGTH_SHORT).show();
    }

    public static void showUpdatedMessage(Activity activity, String name){
        Toast.makeText(activity, name + " "+ activity.getResources().getString(R.string.updated), Toast.LENGTH_SHORT).show();
    }

    public static void showDeletedMessage(Activity activity, String name){
        Toast.makeText(activity, name + " "+ activity.getResources().getString(R.string.deleted), Toast.LENGTH_SHORT).show();
    }
}
