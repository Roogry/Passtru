package id.roogry.passtru.adapter;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ItemListAccountBinding;
import id.roogry.passtru.helpers.AccountDiffCallback;
import id.roogry.passtru.helpers.CustomDialog;
import id.roogry.passtru.helpers.MoreOptionInterface;
import id.roogry.passtru.models.Account;
import id.roogry.passtru.repository.AccountRepository;
import id.roogry.passtru.repository.SosmedRepository;
import id.roogry.passtru.ui.FormManageAccountActivity;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {
    private final ArrayList<Account> listAccounts = new ArrayList<>();
    private Activity activity;
    private AccountRepository accountRepositor;

    public AccountAdapter(Activity activity){
        this.activity = activity;
        accountRepositor = new AccountRepository(activity.getApplication());
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
        ItemListAccountBinding binding = ItemListAccountBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AccountViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return listAccounts.size();
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder implements MoreOptionInterface {
        final ItemListAccountBinding binding;

        public AccountViewHolder(ItemListAccountBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            Account accounts = listAccounts.get(position);
            binding.tvSosmed.setText(accounts.getUsername());
            binding.tvUsername.setText(accounts.getUsername());
            binding.ivMore.setOnClickListener(v ->{
                CustomDialog customDialog = new CustomDialog(activity, R.layout.dialog_more_account);
                customDialog.startAlertDialogOptionAccount(position, this);
            });
        }

        @Override
        public void getDataByPos(int position) {
            Intent intent = new Intent(activity, FormManageAccountActivity.class);
            intent.putExtra(FormManageAccountActivity.EXTRA_ACCOUNT,listAccounts.get(position));
            activity.startActivity(intent);
        }

        @Override
        public void copyPassword(int position) {
            ClipboardManager myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
            String text;
            text =  FormManageAccountActivity.decrypt(listAccounts.get(position).getPassword());

            ClipData myClip = ClipData.newPlainText("password", text);
            myClipboard.setPrimaryClip(myClip);

            Toast.makeText(activity.getApplicationContext(), "Password Copied",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void delete(int position) {
            accountRepositor.delete(listAccounts.get(position));
            Toast.makeText(activity, R.string.deleted_account, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void updateSosmed(int position, String sosmedTitle) {

        }
    }
}
