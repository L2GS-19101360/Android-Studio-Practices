package com.example.shopphile;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductDisplayViewAdapter extends RecyclerView.Adapter<ProductDisplayViewAdapter.ViewHolder> {

    ArrayList<ProductData> productDataArrayList;

    public ProductDisplayViewAdapter(ArrayList<ProductData> productDataArrayList) {
        this.productDataArrayList = productDataArrayList;
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
    }

    @Override
    public int getItemCount() {
        return productDataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageDisplay;
        TextView productNameDisplay, productPriceDisplay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageDisplay = itemView.findViewById(R.id.productimagedisplay);
            productNameDisplay = itemView.findViewById(R.id.productnamedisplay);
            productPriceDisplay = itemView.findViewById(R.id.productpricedisplay);
        }
    }
}
