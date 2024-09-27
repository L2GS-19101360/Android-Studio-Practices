package com.example.shopphile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewProductActivity extends AppCompatActivity {

    ImageView backToMainActivity, productImageDisplay, addProductQuantityButton, minusProductQuantityButton;

    TextView productNameDisplay, productPriceDisplay, productQuantityDisplay;

    Button addProductToCart;

    int productQuantityCount = 0;
    float newTotalPrice = 0;

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

        addProductQuantityButton = findViewById(R.id.addproductquantitybutton);
        minusProductQuantityButton = findViewById(R.id.minusproductquantitybutton);

        productQuantityDisplay = findViewById(R.id.productquantitydisplay);

        addProductQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productQuantityCount += 1;
                productQuantityDisplay.setText(String.valueOf(productQuantityCount));
                productQuantityDisplay.setTextSize(20);

                newTotalPrice += getProductPrice;
                productPriceDisplay.setText(String.format("$%.2f", newTotalPrice));
                productPriceDisplay.setTextSize(20);
            }
        });

        minusProductQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productQuantityCount > 1) {
                    productQuantityCount -= 1;
                    productQuantityDisplay.setText(String.valueOf(productQuantityCount));
                    productQuantityDisplay.setTextSize(20);

                    newTotalPrice -= getProductPrice;
                    productPriceDisplay.setText(String.format("$%.2f", newTotalPrice));
                    productPriceDisplay.setTextSize(20);
                } else {
                    Toast.makeText(ViewProductActivity.this, "Quantity Cannot Decrease More", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addProductToCart = findViewById(R.id.addproducttocart);

        addProductToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save data to SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("CartData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Create a unique key for each product based on its name
                String key = getProductName + "_data";
                String productData = getProductImage + "," + newTotalPrice + "," + productQuantityCount;

                editor.putString(key, productData);
                editor.apply();

                Toast.makeText(ViewProductActivity.this, "Product is Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

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