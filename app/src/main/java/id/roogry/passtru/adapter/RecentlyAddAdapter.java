package id.roogry.passtru.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.roogry.passtru.R;

public class RecentlyAddAdapter extends RecyclerView.Adapter<RecentlyAddAdapter.MyViewHolder> {

    Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_account, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvSosmed, tvUsername;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSosmed = itemView.findViewById(R.id.tvSosmed);
            tvUsername = itemView.findViewById(R.id.tvUsername);
        }
    }
}
