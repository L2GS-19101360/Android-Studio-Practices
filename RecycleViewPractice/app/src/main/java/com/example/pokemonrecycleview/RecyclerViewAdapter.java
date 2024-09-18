package com.example.pokemonrecycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private String[] pokemonNames;
    private int[] pokemonImages;

    public RecyclerViewAdapter(String[] pokemonNames, int[] pokemonImages) {
        this.pokemonNames = pokemonNames;
        this.pokemonImages = pokemonImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_pokemoncard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pokemonImageView.setImageResource(pokemonImages[position]);
        holder.pokemonTextView.setText(pokemonNames[position]);
    }

    @Override
    public int getItemCount() {
        return pokemonNames.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pokemonImageView;
        TextView pokemonTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonImageView = itemView.findViewById(R.id.pokemonimageview);
            pokemonTextView = itemView.findViewById(R.id.pokemontextview);
        }
    }
}
