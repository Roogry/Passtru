package id.roogry.passtru.helpers;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import id.roogry.passtru.models.Account;

public class AccountDiffCallback extends DiffUtil.Callback {

    private final List<Account> mOldAccountList;
    private final List<Account> mNewAccountList;

    public AccountDiffCallback(List<Account> oldAccountList, List<Account> newAccountList) {
        this.mOldAccountList = oldAccountList;
        this.mNewAccountList = newAccountList;
    }

    @Override
    public int getOldListSize() {
        return mOldAccountList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewAccountList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldAccountList.get(oldItemPosition).getId() == mNewAccountList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Account oldAccount = mOldAccountList.get(oldItemPosition);
        final Account newAccount = mNewAccountList.get(newItemPosition);

        return oldAccount.getIdSosmed() == newAccount.getIdSosmed() &&
                oldAccount.getUsername().equals(newAccount.getUsername()) &&
                oldAccount.getPassword().equals(newAccount.getPassword()) &&
                oldAccount.getCreatedAt().equals(newAccount.getCreatedAt());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}