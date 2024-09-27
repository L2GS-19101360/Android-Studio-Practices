package com.example.shopphile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
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

        // Implement swipe-to-delete
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;  // We are not implementing drag & drop
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Get the position of the swiped item
                int position = viewHolder.getAdapterPosition();

                // Remove item from the list and notify the adapter
                cartDataArrayList.remove(position);
                cartAdapter.notifyItemRemoved(position);

                // Optionally, update the total price display after deletion
                updateTotalPrice();
            }
        });

        // Attach the ItemTouchHelper to the RecyclerView
        itemTouchHelper.attachToRecyclerView(cartRecyclerView);

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.ime());
            return WindowInsetsCompat.CONSUMED;
        });
    }

    // Optional: Update total price method
    private void updateTotalPrice() {
        float totalPrice = 0.0f;
        for (CartData cartData : cartDataArrayList) {
            totalPrice += cartData.getProductCartTotalPrice();
        }
        totalPriceDisplay.setText(String.format("$%.2f", totalPrice));
        float overallTotal = totalPrice + shipmentValue;
        overallTotalDisplay.setText(String.format("$%.2f", overallTotal));
    }
}
