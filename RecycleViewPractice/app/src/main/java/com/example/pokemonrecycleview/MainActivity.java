package com.example.pokemonrecycleview;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Pokemon> pokemons = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize the ArrayList with Pokemon objects
        pokemons.add(new Pokemon("Bulbasaur", R.drawable.bulbasaur));
        pokemons.add(new Pokemon("Charmander", R.drawable.charmander));
        pokemons.add(new Pokemon("Cubone", R.drawable.cubone));
        pokemons.add(new Pokemon("Eevee", R.drawable.eevee));
        pokemons.add(new Pokemon("Pikachu", R.drawable.pikachu));
        pokemons.add(new Pokemon("Squirtle", R.drawable.squirtle));

        RecyclerView pokemonRecycleView = findViewById(R.id.pokemonrecycleview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        pokemonRecycleView.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(pokemons);
        pokemonRecycleView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
