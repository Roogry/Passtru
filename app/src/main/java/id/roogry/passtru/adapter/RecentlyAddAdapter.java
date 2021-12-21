package id.roogry.passtru.adapter;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ItemAccountHomeBinding;
import id.roogry.passtru.helpers.CustomDialog;
import id.roogry.passtru.helpers.MoreOptionInterface;
import id.roogry.passtru.models.AccountAndSosmed;

public class RecentlyAddAdapter extends RecyclerView.Adapter<RecentlyAddAdapter.AccountViewHolder> {

    private final ArrayList<AccountAndSosmed> listAccounts = new ArrayList<>();
    private final Activity activity;

    public RecentlyAddAdapter(Activity activity) {
        this.activity = activity;
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
        return Math.min(listAccounts.size(), 4);
    }

    class AccountViewHolder extends RecyclerView.ViewHolder implements MoreOptionInterface {
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
                customDialog.startAlertDialog(position, this);
            });

            binding.btnCopy.setOnClickListener(view -> {
                ClipboardManager myClipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
                String text;
                text = accountWithSosmed.getAccount().getPassword();

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
