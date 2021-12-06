package id.roogry.passtru.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import id.roogry.passtru.R;
import id.roogry.passtru.adapter.AccountListAdapter;

public class ListAccountActivity extends AppCompatActivity {

    private RecyclerView accountList;
    private AccountListAdapter accountListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView ivMoreAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_account);

        ivMoreAccount = findViewById(R.id.ivMoreAccount);
        accountList = findViewById(R.id.rvAccountList);
        accountListAdapter = new AccountListAdapter();
        layoutManager = new LinearLayoutManager(this);
        accountList.setLayoutManager(layoutManager);
        accountList.setAdapter(accountListAdapter);

        ivMoreAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}