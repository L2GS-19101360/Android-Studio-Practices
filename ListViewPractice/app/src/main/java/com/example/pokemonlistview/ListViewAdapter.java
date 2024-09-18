package com.example.pokemonlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

    Context context;
    String pokemonNames[];
    int pokemonImages[];
    LayoutInflater inflater;

    public ListViewAdapter(Context context, String[] pokemonNames, int[] pokemonImages) {
        this.context = context;
        this.pokemonNames = pokemonNames;
        this.pokemonImages = pokemonImages;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return pokemonNames.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.custom_pokemonlistview, null);
        TextView pokemonTextView = view.findViewById(R.id.pokemontextview);
        ImageView pokemonImageView = view.findViewById(R.id.pokemonimageview);
        pokemonTextView.setText(pokemonNames[position]);
        pokemonImageView.setImageResource(pokemonImages[position]);
        return view;
    }
}
