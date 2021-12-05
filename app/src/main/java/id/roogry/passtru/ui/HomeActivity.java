package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import id.roogry.passtru.R;
import id.roogry.passtru.adapter.RecentlyAddAdapter;
import id.roogry.passtru.adapter.SosmedBadgeAdapter;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvBadgeSosmed, recentlyAdd;
    private SosmedBadgeAdapter sosmedBadgeAdapter;
    private RecentlyAddAdapter recentlyAddAdapter;
    private RecyclerView.LayoutManager layoutManagerBadge, layoutManagerRecently;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvBadgeSosmed = findViewById(R.id.rvSosmed);
        sosmedBadgeAdapter = new SosmedBadgeAdapter();
        layoutManagerBadge = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        rvBadgeSosmed.setLayoutManager(layoutManagerBadge);
        rvBadgeSosmed.setAdapter(sosmedBadgeAdapter);

        recentlyAdd = findViewById(R.id.rvRecent);
        recentlyAddAdapter = new RecentlyAddAdapter();
        layoutManagerRecently = new LinearLayoutManager(this);
        recentlyAdd.setLayoutManager(layoutManagerRecently);
        recentlyAdd.setAdapter(recentlyAddAdapter);


    }
}