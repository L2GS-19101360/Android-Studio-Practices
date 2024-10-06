package com.example.shopphile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
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

    DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        // Example data insertion, replace these with actual image resources or IDs
        dbHelper.addProduct(R.drawable.tshirt_image, "T-Shirts", 100.00f, "This is the product t-shirt products");
        dbHelper.addProduct(R.drawable.jeans_image, "Jeans", 200.00f, "This is the product jeans products");
        dbHelper.addProduct(R.drawable.hat_image, "Hats", 50.00f, "This is the product hats products");
        dbHelper.addProduct(R.drawable.jeans_image, "Jackets", 300.00f, "This is the product jackets products");
        dbHelper.addProduct(R.drawable.suit_image, "Suits", 500.00f, "This is the product suits products");
        // Add more products as necessary...

        // Load products into your RecyclerView
        loadProducts();

        productDisplayRecycleView = findViewById(R.id.productdisplayrecyclerviewer);

        // Set the layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        productDisplayRecycleView.setLayoutManager(layoutManager);

        // Initialize and set the adapter with the button click listener
        ProductDisplayViewAdapter adapter = new ProductDisplayViewAdapter(productDataArrayList, new ProductDisplayViewAdapter.OnProductButtonClickListener() {
            @Override
            public void onProductButtonClick(ProductData product) {
                // Show the product details in a Toast
                //Toast.makeText(MainActivity.this, "Product: " + product.getProductName() + ", Price: $" + product.getProductPrice(), Toast.LENGTH_SHORT).show();
                Intent mainActivityIntent = new Intent(MainActivity.this, ViewProductActivity.class);
                mainActivityIntent.putExtra("product_image_data", product.getProductImage());
                mainActivityIntent.putExtra("product_name_data", product.getProductName());
                mainActivityIntent.putExtra("product_price_data", product.getProductPrice());
                startActivity(mainActivityIntent);
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

        notificationBell = findViewById(R.id.notificationbell);
        notificationBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toShoppingCart = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(toShoppingCart);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void loadProducts() {
        Cursor cursor = dbHelper.getAllProducts();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_IMAGE));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_NAME));
                @SuppressLint("Range") float price = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_PRICE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_DESCRIPTION));
                // Use this data to populate your RecyclerView or other UI components
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Toast.makeText(this, "No products found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Toast.makeText(getApplicationContext(), deliverToOptions[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}