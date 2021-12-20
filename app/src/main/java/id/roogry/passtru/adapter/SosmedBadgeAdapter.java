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
import id.roogry.passtru.databinding.ItemSosmedHomeBinding;
import id.roogry.passtru.helpers.CustomDialog;
import id.roogry.passtru.helpers.MoreOptionInterface;
import id.roogry.passtru.helpers.SosmedDiffCallback;
import id.roogry.passtru.models.Sosmed;

public class SosmedBadgeAdapter extends RecyclerView.Adapter<SosmedBadgeAdapter.SosmedViewHolder> {

    private final ArrayList<Sosmed> listSosmeds = new ArrayList<>();
    private final Activity activity;

    public SosmedBadgeAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListSosmeds(List<Sosmed> listSosmeds) {
        final SosmedDiffCallback diffCallback = new SosmedDiffCallback(this.listSosmeds, listSosmeds);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.listSosmeds.clear();
        this.listSosmeds.addAll(listSosmeds);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public SosmedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSosmedHomeBinding binding = ItemSosmedHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SosmedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SosmedViewHolder holder, int position) {
        holder.bind(listSosmeds.get(position));
    }

    @Override
    public int getItemCount() {
        if (listSosmeds.size() > 6){
            return 6;
        }else{
            return listSosmeds.size();
        }
    }

    class SosmedViewHolder extends RecyclerView.ViewHolder implements MoreOptionInterface {
        final ItemSosmedHomeBinding binding;

        SosmedViewHolder(ItemSosmedHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        
        public void bind(Sosmed sosmed) {
            binding.sosmedName.setText(sosmed.getTitle());
            binding.cardSosmed.setOnClickListener(view -> {
                CustomDialog customDialog = new CustomDialog(activity, R.layout.dialog_more_sosmed);
                customDialog.startAlertDialog("more option", sosmed.getId(), this);
            });
        }

        @Override
        public void getDataByPos(int position) {

        }

        @Override
        public void delete(int position) {

        }

        @Override
        public void updateSosmed(int position, String sosmedTitle) {

        }
    }
}
