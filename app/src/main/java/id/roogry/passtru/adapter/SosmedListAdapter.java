package id.roogry.passtru.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ItemAccountHomeBinding;
import id.roogry.passtru.databinding.ItemSocialMediaListBinding;
import id.roogry.passtru.models.Account;

public class SosmedListAdapter extends RecyclerView.Adapter<SosmedListAdapter.SosmedListViewHolder> {

    Context context;

    @NonNull
    @Override
    public SosmedListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSocialMediaListBinding binding = ItemSocialMediaListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SosmedListAdapter.SosmedListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SosmedListViewHolder holder, int position) {
//        holder.bind(listAccounts.get(position));
    }

    @Override
    public int getItemCount() {
        return 15;
    }


    class SosmedListViewHolder extends RecyclerView.ViewHolder {
        final ItemSocialMediaListBinding binding;

        SosmedListViewHolder(ItemSocialMediaListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Account account) {
            binding.socialMediaItem.setText(account.getIdSosmed());

        }
    }
}
