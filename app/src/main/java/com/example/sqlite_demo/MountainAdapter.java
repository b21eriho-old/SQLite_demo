package com.example.sqlite_demo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MountainAdapter extends RecyclerView.Adapter<MountainAdapter.MountainViewHolder>{

    private List<Mountain> mountains;

    public MountainAdapter(List<Mountain> mountains) {
        this.mountains = mountains;
    }

    @NonNull
    @Override
    public MountainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MountainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MountainViewHolder holder, int position) {
        holder.mountainName.setText(mountains.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mountains.size();
    }

    public class MountainViewHolder extends RecyclerView.ViewHolder{

        private TextView mountainName;


        public MountainViewHolder(@NonNull View itemView) {
            super(itemView);
            mountainName = itemView.findViewById(R.id.mountain_name);
        }
    }
}
