package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import id.roogry.passtru.R;
import id.roogry.passtru.adapter.SosmedListAdapter;

public class ListSosmedActivity extends AppCompatActivity {

    private RecyclerView sosmedList;
    private SosmedListAdapter sosmedListAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sosmed);

        sosmedList = findViewById(R.id.rvSosmedList);
        sosmedListAdapter = new SosmedListAdapter();
        layoutManager = new LinearLayoutManager(this);
        sosmedList.setLayoutManager(layoutManager);
        sosmedList.setAdapter(sosmedListAdapter);
    }
}