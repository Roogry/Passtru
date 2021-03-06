package id.roogry.passtru.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ItemSocialMediaListBinding;
import id.roogry.passtru.helpers.dialog.CustomDialog;
import id.roogry.passtru.helpers.dialog.SosmedConfirmDeleteInterface;
import id.roogry.passtru.helpers.SosmedDiffCallback;
import id.roogry.passtru.helpers.dialog.SosmedFormInterface;
import id.roogry.passtru.helpers.dialog.SosmedOptionInterface;
import id.roogry.passtru.helpers.ToastMessage;
import id.roogry.passtru.models.Sosmed;
import id.roogry.passtru.repository.SosmedRepository;

public class SosmedAdapter extends RecyclerView.Adapter<SosmedAdapter.SosmedViewHolder> {
    private final ArrayList<Sosmed> listSosmeds = new ArrayList<>();
    private Activity activity;
    private SosmedRepository sosmedRepository;

    public SosmedAdapter(Activity activity) {
        this.activity = activity;
        sosmedRepository = new SosmedRepository(activity.getApplication());
    }

    public void setListSosmeds(List<Sosmed> listSosmeds) {
        final SosmedDiffCallback diffCallback = new SosmedDiffCallback(this.listSosmeds, listSosmeds);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.listSosmeds.clear();
        this.listSosmeds.addAll(listSosmeds);
        diffResult.dispatchUpdatesTo(this);
        this.notifyDataSetChanged();
    }

    public void updateData() {
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SosmedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSocialMediaListBinding binding = ItemSocialMediaListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SosmedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SosmedViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return listSosmeds.size();
    }

    class SosmedViewHolder extends RecyclerView.ViewHolder implements SosmedOptionInterface, SosmedConfirmDeleteInterface, SosmedFormInterface {
        final ItemSocialMediaListBinding binding;

        SosmedViewHolder(ItemSocialMediaListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            Sosmed sosmed = listSosmeds.get(position);
            binding.tvSosmed.setText(sosmed.getTitle());
            binding.cardSosmed.setOnClickListener(v -> {
                CustomDialog customDialog = new CustomDialog(activity, R.layout.dialog_more_sosmed);
                customDialog.startSosmedOption(position, this);
            });
        }

        @Override
        public void deleteConfirm(int position) {
            CustomDialog customDialog = new CustomDialog(activity, R.layout.dialog_confirm_delete);
            customDialog.startConfirmDeleteSosmed(position, this);
        }

        @Override
        public void delete(int position) {
            sosmedRepository.deleteAccountSosmed(listSosmeds.get(position));
            sosmedRepository.delete(listSosmeds.get(position));
            ToastMessage.showDeletedMessage(activity, listSosmeds.get(position).getTitle());
        }

        @Override
        public void sosmedByPos(int position) {
            CustomDialog customDialog = new CustomDialog(activity, R.layout.dialog_add_sosmed);
            customDialog.startFormSosmed(position, listSosmeds.get(position).getTitle(), this);
        }

        @Override
        public void update(int position, String sosmedTitle) {
            listSosmeds.get(position).setTitle(sosmedTitle);
            sosmedRepository.update(listSosmeds.get(position));

            updateData();
            ToastMessage.showUpdatedMessage(activity, listSosmeds.get(position).getTitle());
        }

        @Override
        public void insert(String title) {

        }
    }
}
