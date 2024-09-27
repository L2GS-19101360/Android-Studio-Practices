package com.example.shopphile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CartData> cartDataArrayList;

    public CartAdapter(Context context, ArrayList<CartData> cartDataArrayList) {
        this.context = context;
        this.cartDataArrayList = cartDataArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartData cartData = cartDataArrayList.get(position);

        holder.productImage.setImageResource(cartData.getProductCartImage());
        holder.productName.setText(cartData.getProductCartName());
        holder.productQuantity.setText(String.valueOf(cartData.getProductCartQuantity()));
        holder.productPrice.setText(String.format("$%.2f", cartData.getProductCartTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return cartDataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productQuantity, productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.cart_product_image);
            productName = itemView.findViewById(R.id.cart_product_name);
            productQuantity = itemView.findViewById(R.id.cart_product_quantity);
            productPrice = itemView.findViewById(R.id.cart_product_price);
        }
    }
}
