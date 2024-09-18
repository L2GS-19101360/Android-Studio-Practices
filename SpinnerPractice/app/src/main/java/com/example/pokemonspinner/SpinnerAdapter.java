package com.example.pokemonspinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpinnerAdapter extends ArrayAdapter<String> {

    Context context;
    String[] pokemonNames;
    int[] pokemonImages;

    public SpinnerAdapter(Context context, String[] pokemonNames, int[] pokemonImages) {
        // Pass the pokemonNames array to the ArrayAdapter superclass
        super(context, R.layout.custom_pokemonspinner, pokemonNames);
        this.context = context;
        this.pokemonNames = pokemonNames;
        this.pokemonImages = pokemonImages;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Use convertView for optimization
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_pokemonspinner, parent, false);
        }

        TextView pokemonTextView = (TextView) convertView.findViewById(R.id.pokemontextview);
        ImageView pokemonImageView = (ImageView) convertView.findViewById(R.id.pokemonimageview);

        pokemonTextView.setText(pokemonNames[position]);
        pokemonImageView.setImageResource(pokemonImages[position]);

        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Use convertView for optimization
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_pokemonspinner, parent, false);
        }

        TextView pokemonTextView = (TextView) convertView.findViewById(R.id.pokemontextview);
        ImageView pokemonImageView = (ImageView) convertView.findViewById(R.id.pokemonimageview);

        pokemonTextView.setText(pokemonNames[position]);
        pokemonImageView.setImageResource(pokemonImages[position]);

        return convertView;
    }
}
