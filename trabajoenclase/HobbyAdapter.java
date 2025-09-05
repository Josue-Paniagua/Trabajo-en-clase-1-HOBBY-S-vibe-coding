package com.example.trabajoenclase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HobbyAdapter extends RecyclerView.Adapter<HobbyAdapter.HobbyViewHolder> {
    private List<Hobby> hobbies;
    private OnHobbyClickListener listener;

    public interface OnHobbyClickListener {
        void onHobbyClick(Hobby hobby);
        void onHobbyLongClick(Hobby hobby);
    }

    public HobbyAdapter(List<Hobby> hobbies, OnHobbyClickListener listener) {
        this.hobbies = hobbies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HobbyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new HobbyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HobbyViewHolder holder, int position) {
        Hobby hobby = hobbies.get(position);
        holder.text1.setText(hobby.getNombre());
        holder.text2.setText("Dificultad: " + hobby.getDificultad());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onHobbyClick(hobby);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onHobbyLongClick(hobby);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return hobbies != null ? hobbies.size() : 0;
    }

    public void updateHobbies(List<Hobby> newHobbies) {
        this.hobbies = newHobbies;
        notifyDataSetChanged();
    }

    static class HobbyViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;

        HobbyViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }
}
