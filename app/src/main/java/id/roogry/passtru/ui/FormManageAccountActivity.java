package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ActivityFormManageAccountBinding;

public class FormManageAccountActivity extends AppCompatActivity {
    private ActivityFormManageAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFormManageAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.ivMore.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.getMenuInflater().inflate(R.menu.delete_create, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()){
                    case R.id.deleteAccount :
                        Toast.makeText(FormManageAccountActivity.this, "Delete Account Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.createSosmed :
                        Toast.makeText(FormManageAccountActivity.this, "Create Social Media Clicked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            });
            popupMenu.show();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}