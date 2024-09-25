package com.example.shopphile;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductDisplayViewAdapter extends RecyclerView.Adapter<ProductDisplayViewAdapter.ViewHolder> {

    ArrayList<ProductData> productDataArrayList;
    private OnProductButtonClickListener onProductButtonClickListener;

    // Constructor accepting a button click listener
    public ProductDisplayViewAdapter(ArrayList<ProductData> productDataArrayList, OnProductButtonClickListener listener) {
        this.productDataArrayList = productDataArrayList;
        this.onProductButtonClickListener = listener;
    }

    @NonNull
    @Override
    public ProductDisplayViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_display_recycler_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDisplayViewAdapter.ViewHolder holder, int position) {
        ProductData productData = productDataArrayList.get(position);
        holder.productImageDisplay.setImageResource(productData.getProductImage());
        holder.productNameDisplay.setText(productData.getProductName());
        holder.productPriceDisplay.setText(String.format("$%.2f", productData.getProductPrice()));

        // Set the click listener for the viewProductButton
        holder.viewProductButton.setOnClickListener(v -> {
            if (onProductButtonClickListener != null) {
                onProductButtonClickListener.onProductButtonClick(productData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productDataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageDisplay;
        TextView productNameDisplay, productPriceDisplay;
        Button viewProductButton; // Add the button here

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageDisplay = itemView.findViewById(R.id.productimagedisplay);
            productNameDisplay = itemView.findViewById(R.id.productnamedisplay);
            productPriceDisplay = itemView.findViewById(R.id.productpricedisplay);
            viewProductButton = itemView.findViewById(R.id.viewproductbutton); // Initialize the button
        }
    }

    // Define an interface for button clicks
    public interface OnProductButtonClickListener {
        void onProductButtonClick(ProductData product);
    }
}


