package id.roogry.passtru.adapter;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ItemAccountHomeBinding;
import id.roogry.passtru.helpers.dialog.CustomDialog;
import id.roogry.passtru.helpers.dialog.AccountOptionInterface;
import id.roogry.passtru.helpers.ToastMessage;
import id.roogry.passtru.models.AccountAndSosmed;
import id.roogry.passtru.repository.AccountRepository;
import id.roogry.passtru.ui.FormManageAccountActivity;
import id.roogry.passtru.ui.HomeActivity;

public class RecentlyAddAdapter extends RecyclerView.Adapter<RecentlyAddAdapter.AccountViewHolder> {

    private final ArrayList<AccountAndSosmed> listAccounts = new ArrayList<>();
    private final Activity activity;
    private final AccountRepository accountRepositor;

    public RecentlyAddAdapter(Activity activity) {
        this.activity = activity;
        accountRepositor = new AccountRepository(activity.getApplication());
    }

    public void setListAccounts(List<AccountAndSosmed> listAccounts) {
        this.listAccounts.clear();
        this.listAccounts.addAll(listAccounts);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAccountHomeBinding binding = ItemAccountHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecentlyAddAdapter.AccountViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return listAccounts.size();
    }

    class AccountViewHolder extends RecyclerView.ViewHolder implements AccountOptionInterface {
        final ItemAccountHomeBinding binding;

        AccountViewHolder(ItemAccountHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            AccountAndSosmed accountWithSosmed = listAccounts.get(position);
            binding.tvSosmed.setText(accountWithSosmed.getSosmed().getTitle());
            binding.tvUsername.setText(accountWithSosmed.getAccount().getUsername());
            binding.cardAccount.setOnClickListener(view -> {
                CustomDialog customDialog = new CustomDialog(activity, R.layout.dialog_more_account);
                customDialog.startAccountOption(position, accountWithSosmed.getAccount().getUsername(), this);
            });

            binding.btnCopy.setOnClickListener(view -> copyPassword(accountWithSosmed));
        }

        private void copyPassword(AccountAndSosmed accountWithSosmed){
            ClipboardManager myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
            String text = FormManageAccountActivity.decrypt(accountWithSosmed.getAccount().getPassword());

            ClipData myClip = ClipData.newPlainText("password", text);
            myClipboard.setPrimaryClip(myClip);
            Toast.makeText(activity, "Password copied", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void getDataByPos(int position) {
            Intent intent = new Intent(activity, FormManageAccountActivity.class);
            intent.putExtra(FormManageAccountActivity.EXTRA_ACCOUNT, listAccounts.get(position).getAccount());
            activity.startActivity(intent);
        }

        @Override
        public void copyPassword(int position) {
            copyPassword(listAccounts.get(position));
        }

        @Override
        public void delete(int position) {
            accountRepositor.delete(listAccounts.get(position).getAccount());
            ToastMessage.showDeletedMessage(activity, listAccounts.get(position).getAccount().getUsername());
        }
    }
}
