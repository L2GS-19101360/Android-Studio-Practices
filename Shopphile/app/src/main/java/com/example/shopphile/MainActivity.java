package com.example.shopphile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView notificationBell, shoppingCart;
    Spinner deliverToOptionSpinner;

    String [] deliverToOptions = {"Home", "School", "Office"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        deliverToOptionSpinner = findViewById(R.id.delivertooptions);

        ArrayAdapter arrayDeliverToOptions = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, deliverToOptions);
        arrayDeliverToOptions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deliverToOptionSpinner.setAdapter(arrayDeliverToOptions);
        deliverToOptionSpinner.setOnItemSelectedListener(this);

        shoppingCart = findViewById(R.id.shoppingcart);

        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toShoppingCart = new Intent(MainActivity.this, CartActivity.class);
                startActivity(toShoppingCart);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Toast.makeText(getApplicationContext(), deliverToOptions[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}