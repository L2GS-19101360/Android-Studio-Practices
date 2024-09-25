package com.example.shopphile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView notificationBell, shoppingCart;
    Spinner deliverToOptionSpinner;

    RecyclerView productDisplayRecycleView;

    String [] deliverToOptions = {"Home", "School", "Office"};

    ArrayList<ProductData> productDataArrayList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize your product data
        productDataArrayList.add(new ProductData(R.drawable.tshirt_image, "T-Shirts", 100F));
        productDataArrayList.add(new ProductData(R.drawable.jeans_image, "Jeans", 200F));
        productDataArrayList.add(new ProductData(R.drawable.hat_image, "Hats", 50F));
        productDataArrayList.add(new ProductData(R.drawable.jacket_image, "Jackets", 300F));
        productDataArrayList.add(new ProductData(R.drawable.suit_image, "Suits", 500F));

        productDisplayRecycleView = findViewById(R.id.productdisplayrecyclerviewer);

        // Set the layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        productDisplayRecycleView.setLayoutManager(layoutManager);

        // Initialize and set the adapter with the button click listener
        ProductDisplayViewAdapter adapter = new ProductDisplayViewAdapter(productDataArrayList, new ProductDisplayViewAdapter.OnProductButtonClickListener() {
            @Override
            public void onProductButtonClick(ProductData product) {
                // Show the product details in a Toast
                Toast.makeText(MainActivity.this, "Product: " + product.getProductName() + ", Price: $" + product.getProductPrice(), Toast.LENGTH_SHORT).show();
            }
        });
        productDisplayRecycleView.setAdapter(adapter);

        // Spinner setup
        deliverToOptionSpinner = findViewById(R.id.delivertooptions);
        ArrayAdapter<String> arrayDeliverToOptions = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, deliverToOptions);
        arrayDeliverToOptions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deliverToOptionSpinner.setAdapter(arrayDeliverToOptions);
        deliverToOptionSpinner.setOnItemSelectedListener(this);

        // Shopping cart click listener
        shoppingCart = findViewById(R.id.shoppingcart);
        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toShoppingCart = new Intent(MainActivity.this, CartActivity.class);
                startActivity(toShoppingCart);
            }
        });

        // Apply window insets
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