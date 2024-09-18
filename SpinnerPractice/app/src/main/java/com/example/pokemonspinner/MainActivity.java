package com.example.pokemonspinner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    String [] pokemonNames = {"Bulbasaur", "Charmander", "Cubone", "Eevee", "Pikachu", "Squirtle"};
    int [] pokemonImages = {R.drawable.bulbasaur, R.drawable.charmander, R.drawable.cubone, R.drawable.eevee, R.drawable.pikachu, R.drawable.squirtle};

    Spinner pokemonSpinner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        pokemonSpinner = findViewById(R.id.pokemonspinner);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, pokemonNames, pokemonImages);

        pokemonSpinner.setAdapter(spinnerAdapter);

        pokemonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), pokemonNames[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}