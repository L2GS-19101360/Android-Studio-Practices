package com.example.androidsqlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.myViewHolder> {

    private Context context;
    private ArrayList bookIdArrayList, bookTitleArrayList, bookAuthorArrayList, bookPagesArrayList;

    int position;

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
    public void onBindViewHolder(@NonNull CustomAdapter.myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bookIdDisplay.setText(String.valueOf(bookIdArrayList.get(position)));
        holder.bookTitleDisplay.setText("Title: " + String.valueOf(bookTitleArrayList.get(position)));
        holder.bookAuthorDisplay.setText("Author: " +String.valueOf(bookAuthorArrayList.get(position)));
        holder.bookPagesDisplay.setText("Pages: " +String.valueOf(bookPagesArrayList.get(position)));
        holder.mainLayoutRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Data " + position + " is selected", Toast.LENGTH_SHORT).show();
                Intent modifyRecordIntent = new Intent(context, ModifyRecordActivity.class);
                modifyRecordIntent.putExtra("modify_book_id", String.valueOf(bookIdArrayList.get(position)));
                modifyRecordIntent.putExtra("modify_book_title", String.valueOf(bookTitleArrayList.get(position)));
                modifyRecordIntent.putExtra("modify_book_author", String.valueOf(bookAuthorArrayList.get(position)));
                modifyRecordIntent.putExtra("modify_book_pages", String.valueOf(bookPagesArrayList.get(position)));
                context.startActivity(modifyRecordIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookIdArrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView bookIdDisplay, bookTitleDisplay, bookAuthorDisplay, bookPagesDisplay;
        LinearLayout mainLayoutRow;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            bookIdDisplay = itemView.findViewById(R.id.bookId);
            bookTitleDisplay = itemView.findViewById(R.id.bookTitle);
            bookAuthorDisplay = itemView.findViewById(R.id.bookAuthor);
            bookPagesDisplay = itemView.findViewById(R.id.bookPages);
            mainLayoutRow = itemView.findViewById(R.id.mainlayoutrow);
        }
    }
}
