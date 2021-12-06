package id.roogry.passtru.helpers;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import id.roogry.passtru.models.Sosmed;

public class SosmedDiffCallback extends DiffUtil.Callback {

    private final List<Sosmed> mOldSosmedList;
    private final List<Sosmed> mNewSosmedList;

    public SosmedDiffCallback(List<Sosmed> oldSosmedList, List<Sosmed> newSosmedList) {
        this.mOldSosmedList = oldSosmedList;
        this.mNewSosmedList = newSosmedList;
    }

    @Override
    public int getOldListSize() {
        return mOldSosmedList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewSosmedList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldSosmedList.get(oldItemPosition).getId() == mNewSosmedList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Sosmed oldSosmed = mOldSosmedList.get(oldItemPosition);
        final Sosmed newSosmed = mNewSosmedList.get(newItemPosition);

        return oldSosmed.getTitle().equals(newSosmed.getTitle());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}