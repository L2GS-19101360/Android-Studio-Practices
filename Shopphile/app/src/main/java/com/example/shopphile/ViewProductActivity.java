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

    TextView productNameDisplay, productPriceDisplay, productQuantityDisplay, productDescriptionDisplay;

    Button addProductToCart;

    int productQuantityCount = 1;
    float newTotalPrice;

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
        productDescriptionDisplay = findViewById(R.id.productdescriptiondisplay);

        // Use getIntent() to receive data from MainActivity
        Intent viewProductIntent = getIntent();

        int getProductImage = viewProductIntent.getIntExtra("product_image_data", -1);
        String getProductName = viewProductIntent.getStringExtra("product_name_data");
        float getProductPrice = viewProductIntent.getFloatExtra("product_price_data", 0.0F);
        String getProductDescription = viewProductIntent.getStringExtra("product_description_data");

        // Set the received data to UI components
        if (getProductImage != -1) {
            productImageDisplay.setImageResource(getProductImage);
        }
        productNameDisplay.setText(getProductName);
        productDescriptionDisplay.setText(getProductDescription);
        productPriceDisplay.setText(String.format("$%.2f", getProductPrice));

        addProductQuantityButton = findViewById(R.id.addproductquantitybutton);
        minusProductQuantityButton = findViewById(R.id.minusproductquantitybutton);

        productQuantityDisplay = findViewById(R.id.productquantitydisplay);

        newTotalPrice = getProductPrice;

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
                // Get the product details
                int productImage = getProductImage;
                String productName = getProductName;
                float productPrice = newTotalPrice;

                // Get the quantity
                int productQuantity = productQuantityCount;

                // Create a DatabaseHelper instance
                DatabaseHelper dbHelper = new DatabaseHelper(ViewProductActivity.this);

                // Add the product to the cart in the database
                dbHelper.addToCart(productImage, productName, productPrice, productQuantity);

                // Show a message to the user
                Toast.makeText(ViewProductActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
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