package com.example.pokemonlistview;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    String [] pokemonNames = {"Bulbasaur", "Charmander", "Cubone", "Eevee", "Pikachu", "Squirtle"};
    int [] pokemonImages = {R.drawable.bulbasaur, R.drawable.charmander, R.drawable.cubone, R.drawable.eevee, R.drawable.pikachu, R.drawable.squirtle};

    ListView pokemonListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        pokemonListView = findViewById(R.id.pokemonlistview);

        ListViewAdapter listViewAdapter = new ListViewAdapter(getApplicationContext(), pokemonNames, pokemonImages);

        pokemonListView.setAdapter(listViewAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pokemonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), pokemonNames[position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}