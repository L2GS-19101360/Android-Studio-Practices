package com.example.shopphile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewProductActivity extends AppCompatActivity {

    ImageView backToMainActivity, productImageDisplay;

    TextView productNameDisplay, productPriceDisplay;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_product);

        backToMainActivity = findViewById(R.id.backtomainactivity);
        productImageDisplay = findViewById(R.id.productimagedisplay);  // Initialize ImageView
        productNameDisplay = findViewById(R.id.productnamedisplay);    // Initialize TextView
        productPriceDisplay = findViewById(R.id.productpricedisplay);  // Initialize TextView

        // Use getIntent() to receive data from MainActivity
        Intent viewProductIntent = getIntent();

        int getProductImage = viewProductIntent.getIntExtra("product_image_data", -1);
        String getProductName = viewProductIntent.getStringExtra("product_name_data");
        float getProductPrice = viewProductIntent.getFloatExtra("product_price_data", 0.0F);

        // Set the received data to UI components
        if (getProductImage != -1) {
            productImageDisplay.setImageResource(getProductImage);
        }
        productNameDisplay.setText(getProductName);
        productPriceDisplay.setText(String.format("$%.2f", getProductPrice));

        backToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productDisplayIntent = new Intent(ViewProductActivity.this, MainActivity.class);
                startActivity(productDisplayIntent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}