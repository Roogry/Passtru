package id.roogry.passtru.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ItemSocialMediaListBinding;
import id.roogry.passtru.helpers.CustomDialog;
import id.roogry.passtru.helpers.SosmedDiffCallback;
import id.roogry.passtru.models.Sosmed;
import id.roogry.passtru.repository.SosmedRepository;

public class SosmedAdapter extends RecyclerView.Adapter<SosmedAdapter.SosmedViewHolder> {
    private final ArrayList<Sosmed> listSosmeds = new ArrayList<>();
    private Activity activity;
    private SosmedRepository sosmedRepositor;

    public SosmedAdapter(Activity activity){
        this.activity = activity;
        sosmedRepositor = new SosmedRepository(activity.getApplication());
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
        ItemSocialMediaListBinding binding = ItemSocialMediaListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SosmedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SosmedViewHolder holder, int position) {
        holder.bind(listSosmeds.get(position));
    }

    @Override
    public int getItemCount() {
        return listSosmeds.size();
    }

    class SosmedViewHolder extends RecyclerView.ViewHolder {
        final ItemSocialMediaListBinding binding;

        SosmedViewHolder(ItemSocialMediaListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Sosmed sosmed) {
            binding.tvSosmed.setText(sosmed.getTitle());
            binding.ivMore.setOnClickListener(v ->{
                CustomDialog customDialog = new CustomDialog(activity);
                customDialog.startAlertDialog("more option", sosmed.getId(), R.layout.dialog_more_sosmed);
            });
        }
    }

    public void removeItem(int id) {
        sosmedRepositor.delete(listSosmeds.get(id));
        Toast.makeText(activity, String.valueOf(id), Toast.LENGTH_SHORT).show();
    }
}
