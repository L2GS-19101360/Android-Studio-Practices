package com.example.shopphile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    ImageView backToHomePage;
    RecyclerView cartRecyclerView;
    CartAdapter cartAdapter;

    TextView totalPriceDisplay, shipmentPaymentDisplay, overallTotalDisplay;

    float shipmentValue = 10.00F;

    ArrayList<CartData> cartDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        SharedPreferences sharedPreferences = getSharedPreferences("CartData", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();

        float totalPrice = 0.0f; // Initialize total price
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String[] productData = entry.getValue().toString().split(",");
            int productImage = Integer.parseInt(productData[0]);
            float productPrice = Float.parseFloat(productData[1]);
            int productQuantity = Integer.parseInt(productData[2]);

            // Calculate total price for this product
            totalPrice += productPrice * productQuantity;

            CartData cartData = new CartData(productImage, entry.getKey().replace("_data", ""), productPrice, productQuantity);
            cartDataArrayList.add(cartData);
        }

        // Set up RecyclerView
        cartRecyclerView = findViewById(R.id.cartrecycleview);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartDataArrayList);
        cartRecyclerView.setAdapter(cartAdapter);

        // Update total price display
        totalPriceDisplay = findViewById(R.id.totalpricedisplay);
        shipmentPaymentDisplay = findViewById(R.id.shipmentpaymentdisplay);
        overallTotalDisplay = findViewById(R.id.overalltotaldisplay);

        shipmentPaymentDisplay.setText(String.format("$%.2f", shipmentValue));
        totalPriceDisplay.setText(String.format("$%.2f", totalPrice));

        // Calculate and display overall total (Total + Shipment)
        float overallTotal = totalPrice + shipmentValue;
        overallTotalDisplay.setText(String.format("$%.2f", overallTotal));

        backToHomePage = findViewById(R.id.backtohomepagebutton);

        backToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ordersIntent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(ordersIntent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


}
