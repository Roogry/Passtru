package id.roogry.passtru.adapter;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ItemAccountHomeBinding;
import id.roogry.passtru.helpers.AccountDiffCallback;
import id.roogry.passtru.helpers.CustomDialog;
import id.roogry.passtru.helpers.MoreOptionInterface;
import id.roogry.passtru.models.Account;
import id.roogry.passtru.ui.FormManageAccountActivity;

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
        return Math.min(listAccounts.size(), 4);
    }

    class AccountViewHolder extends RecyclerView.ViewHolder implements MoreOptionInterface {
        final ItemAccountHomeBinding binding;

        AccountViewHolder(ItemAccountHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Account account) {
            binding.tvSosmed.setText(account.getUsername());
            binding.tvUsername.setText(account.getUsername());
            binding.cardAccount.setOnClickListener(view -> {
                CustomDialog customDialog = new CustomDialog(activity, R.layout.dialog_more_account);
                customDialog.startAlertDialogOptionSosmed(account.getId(), this);
            });

            binding.btnCopy.setOnClickListener(view -> {
                ClipboardManager myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
                String text;
                text =  FormManageAccountActivity.decrypt(account.getPassword());

                ClipData myClip = ClipData.newPlainText("password", text);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(activity, "Password copied", Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public void getDataByPos(int position) {

        }

        @Override
        public void copyPassword(int position) {

        }

        @Override
        public void delete(int position) {

        }

        @Override
        public void updateSosmed(int position, String sosmedTitle) {

        }
    }
}
