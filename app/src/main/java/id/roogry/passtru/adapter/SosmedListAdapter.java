package id.roogry.passtru.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import id.roogry.passtru.R;
import id.roogry.passtru.databinding.ItemSocialMediaListBinding;
import id.roogry.passtru.helpers.CustomDialog;
import id.roogry.passtru.helpers.SosmedDiffCallback;
import id.roogry.passtru.models.Account;
import id.roogry.passtru.models.Sosmed;
import id.roogry.passtru.repository.SosmedRepository;

public class SosmedListAdapter extends RecyclerView.Adapter<SosmedListAdapter.SosmedListViewHolder> {
    private final ArrayList<Sosmed> listSosmeds = new ArrayList<>();
    private Activity activity;
    private SosmedRepository sosmedRepositor;

    public SosmedListAdapter(Activity activity){
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
    public SosmedListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSocialMediaListBinding binding = ItemSocialMediaListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SosmedListAdapter.SosmedListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SosmedListViewHolder holder, int position) {
        holder.bind(listSosmeds.get(position));
    }

    @Override
    public int getItemCount() {
        return listSosmeds.size();
    }



    class SosmedListViewHolder extends RecyclerView.ViewHolder {
        final ItemSocialMediaListBinding binding;

        SosmedListViewHolder(ItemSocialMediaListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Sosmed sosmed) {
            binding.socialMediaItem.setText(sosmed.getTitle());
            binding.moreOption.setOnClickListener(v ->{
                CustomDialog customDialog = new CustomDialog(activity);
                customDialog.startAlertDialog("more option", sosmed.getId(), null, R.layout.dialog_more_sosmed);
            });

        }

    }

    public void removeItem(Integer id) {
        sosmedRepositor.delete(listSosmeds.get(id));
        Toast.makeText(activity, String.valueOf(id), Toast.LENGTH_SHORT).show();
    }

}
