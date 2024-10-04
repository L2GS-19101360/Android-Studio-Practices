package com.example.androidsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.myViewHolder> {

    private Context context;
    private ArrayList bookIdArrayList, bookTitleArrayList, bookAuthorArrayList, bookPagesArrayList;

    CustomAdapter(Context context, ArrayList bookIdArrayList, ArrayList bookTitleArrayList, ArrayList bookAuthorArrayList, ArrayList bookPagesArrayList) {
        this.context = context;
        this.bookIdArrayList = bookIdArrayList;
        this.bookTitleArrayList = bookTitleArrayList;
        this.bookAuthorArrayList = bookAuthorArrayList;
        this.bookPagesArrayList = bookPagesArrayList;
    }

    @NonNull
    @Override
    public CustomAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycleview_row, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.myViewHolder holder, int position) {
        holder.bookIdDisplay.setText(String.valueOf(bookIdArrayList.get(position)));
        holder.bookTitleDisplay.setText("Title: " + String.valueOf(bookTitleArrayList.get(position)));
        holder.bookAuthorDisplay.setText("Author: " +String.valueOf(bookAuthorArrayList.get(position)));
        holder.bookPagesDisplay.setText("Pages: " +String.valueOf(bookPagesArrayList.get(position)));
    }

    @Override
    public int getItemCount() {
        return bookIdArrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView bookIdDisplay, bookTitleDisplay, bookAuthorDisplay, bookPagesDisplay;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            bookIdDisplay = itemView.findViewById(R.id.bookiddisplay);
            bookTitleDisplay = itemView.findViewById(R.id.booktitledisplay);
            bookAuthorDisplay = itemView.findViewById(R.id.bookauthordisplay);
            bookPagesDisplay = itemView.findViewById(R.id.bookpagesdisplay);
        }
    }
}
