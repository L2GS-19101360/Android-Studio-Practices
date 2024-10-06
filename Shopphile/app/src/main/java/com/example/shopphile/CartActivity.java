package com.example.shopphile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

    float shipmentValue = 10.00F; // Shipment value

    ArrayList<CartData> cartDataArrayList = new ArrayList<>(); // Cart data list

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        SharedPreferences sharedPreferences = getSharedPreferences("CartData", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();

        float totalPrice = 0.0f; // Initialize total price

        databaseHelper = new DatabaseHelper(this);

        // Retrieve all cart items from the database
        Cursor cursor = databaseHelper.getAllCartItems();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CART_IMAGE));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CART_NAME));
                @SuppressLint("Range") float price = cursor.getFloat(cursor.getColumnIndex(DatabaseHelper.COLUMN_CART_PRICE));
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CART_QUANTITY));

                // Create a CartData object and add it to the list
                CartData cartData = new CartData(0, image, name, price, quantity);
                cartDataArrayList.add(cartData);
                totalPrice += price; // Update total price calculation here
            } while (cursor.moveToNext());
            cursor.close(); // Close the cursor after use
        }

        // Set up RecyclerView
        cartRecyclerView = findViewById(R.id.cartrecycleview);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartDataArrayList);
        cartRecyclerView.setAdapter(cartAdapter);

        // Implement swipe-to-delete (right) and swipe-to-change-order (left)
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;  // We are not implementing drag & drop
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.RIGHT) {
                    // Handle the DELETE action
                    CartData cartData = cartDataArrayList.get(position);
                    databaseHelper.deleteCartItem(cartData.getProductId()); // Call method to delete from database using the cart ID
                    cartDataArrayList.remove(position);
                    cartAdapter.notifyItemRemoved(position);
                    updateTotalPrice(); // Update total price after deletion
                } else if (direction == ItemTouchHelper.LEFT) {
                    // Handle the CHANGE ORDER action
                    cartAdapter.notifyItemChanged(position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    Paint paint = new Paint();
                    Drawable icon = null;
                    String actionText = "";
                    int iconLeft = 0, iconRight, iconTop, iconBottom;
                    float textX;

                    if (dX > 0) { // Swiping to the right (DELETE)
                        // Red background for DELETE
                        paint.setColor(Color.RED);
                        c.drawRect(
                                (float) viewHolder.itemView.getLeft(),
                                (float) viewHolder.itemView.getTop(),
                                dX,
                                (float) viewHolder.itemView.getBottom(),
                                paint
                        );

                        // Draw DELETE text
                        actionText = "DELETE";
                        paint.setColor(Color.WHITE);
                        paint.setTextSize(50);
                        paint.setTextAlign(Paint.Align.LEFT);
                        textX = viewHolder.itemView.getLeft() + 150;

                        c.drawText(actionText, textX, viewHolder.itemView.getTop() + (viewHolder.itemView.getHeight() / 2) + 20, paint);

                        // Draw delete icon
                        icon = ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.delete_24px);
                        if (icon != null) {
                            iconLeft = viewHolder.itemView.getLeft() + 50;
                        }
                    } else { // Swiping to the left (CHANGE ORDER)
                        // Green background for CHANGE ORDER
                        paint.setColor(Color.GREEN);
                        c.drawRect(
                                (float) viewHolder.itemView.getRight() + dX,
                                (float) viewHolder.itemView.getTop(),
                                (float) viewHolder.itemView.getRight(),
                                (float) viewHolder.itemView.getBottom(),
                                paint
                        );

                        // Draw CHANGE ORDER text
                        actionText = "CHANGE ORDER";
                        paint.setColor(Color.WHITE);
                        paint.setTextSize(50);
                        paint.setTextAlign(Paint.Align.RIGHT);
                        textX = viewHolder.itemView.getRight() - 150;

                        c.drawText(actionText, textX, viewHolder.itemView.getTop() + (viewHolder.itemView.getHeight() / 2) + 20, paint);

                        // Draw update icon
                        icon = ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.update_24px);
                        if (icon != null) {
                            iconLeft = viewHolder.itemView.getRight() - 50 - icon.getIntrinsicWidth();
                        }
                    }

                    // Draw the icon beside the text
                    if (icon != null) {
                        int iconMargin = (viewHolder.itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                        iconTop = viewHolder.itemView.getTop() + iconMargin;
                        iconBottom = iconTop + icon.getIntrinsicHeight();
                        iconRight = iconLeft + icon.getIntrinsicWidth();
                        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                        icon.draw(c);
                    }

                    // Draw the item view itself
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        });

        // Attach the ItemTouchHelper to the RecyclerView
        itemTouchHelper.attachToRecyclerView(cartRecyclerView);

        // Initialize TextViews
        totalPriceDisplay = findViewById(R.id.totalpricedisplay);
        shipmentPaymentDisplay = findViewById(R.id.shipmentpaymentdisplay);
        overallTotalDisplay = findViewById(R.id.overalltotaldisplay);

        // Set shipment value
        shipmentPaymentDisplay.setText(String.format("$%.2f", shipmentValue));

        // Set initial total price and overall total
        totalPriceDisplay.setText(String.format("$%.2f", totalPrice));
        float overallTotal = totalPrice + shipmentValue;
        overallTotalDisplay.setText(String.format("$%.2f", overallTotal));

        // Back to HomePage button click listener
        backToHomePage = findViewById(R.id.backtohomepagebutton);
        backToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ordersIntent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(ordersIntent);
            }
        });

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.ime());
            return WindowInsetsCompat.CONSUMED;
        });
    }

    // Update total price method
    private void updateTotalPrice() {
        float totalPrice = 0.0f; // Reset total price
        for (CartData cartData : cartDataArrayList) {
            totalPrice += cartData.getProductCartTotalPrice();
        }
        totalPriceDisplay.setText(String.format("$%.2f", totalPrice));
        float overallTotal = totalPrice + shipmentValue;
        overallTotalDisplay.setText(String.format("$%.2f", overallTotal)); // Update overall total display
    }
}
