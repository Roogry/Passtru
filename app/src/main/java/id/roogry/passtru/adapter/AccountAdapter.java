package id.roogry.passtru.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ItemListAccountBinding;
import id.roogry.passtru.helpers.AccountDiffCallback;
import id.roogry.passtru.models.Account;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {
    private final ArrayList<Account> listAccounts = new ArrayList<>();

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
        ItemListAccountBinding binding = ItemListAccountBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AccountViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        holder.bind(listAccounts.get(position));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder {
        final ItemListAccountBinding binding;

        public AccountViewHolder(ItemListAccountBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Account account) {
            binding.tvSosmed.setText(account.getUsername());
            binding.tvUsername.setText(account.getUsername());
        }
    }
}
