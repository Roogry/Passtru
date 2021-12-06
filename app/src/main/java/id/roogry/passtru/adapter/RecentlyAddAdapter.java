package id.roogry.passtru.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ItemAccountHomeBinding;
import id.roogry.passtru.helpers.AccountDiffCallback;
import id.roogry.passtru.models.Account;
import id.roogry.passtru.models.Account;

public class RecentlyAddAdapter extends RecyclerView.Adapter<RecentlyAddAdapter.AccountViewHolder> {

    private final ArrayList<Account> listAccounts = new ArrayList<>();
    private final Activity activity;

    public RecentlyAddAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListAccounts(List<Account> listAccounts) {
        final AccountDiffCallback diffCallback = new AccountDiffCallback(this.listAccounts, listAccounts);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.listAccounts.clear();
        this.listAccounts.addAll(listAccounts);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAccountHomeBinding binding = ItemAccountHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecentlyAddAdapter.AccountViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        holder.bind(listAccounts.get(position));
    }

    @Override
    public int getItemCount() {
        if (listAccounts.size() > 6){
            return 6;
        }else{
            return listAccounts.size();
        }
    }

    class AccountViewHolder extends RecyclerView.ViewHolder {
        final ItemAccountHomeBinding binding;

        AccountViewHolder(ItemAccountHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Account account) {
            binding.tvSosmed.setText(account.getIdSosmed());
            binding.tvUsername.setText(account.getUsername());
            binding.cardAccount.setOnClickListener(view -> {
                // implementasi intent
                Intent intent = new Intent();
            });
        }
    }
}
