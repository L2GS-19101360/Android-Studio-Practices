package com.example.androidsqlite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView bookRecycleView;
    FloatingActionButton createNewRecord;

    MyDatabaseHelper myDatabaseHelper;
    ArrayList<String> bookId, bookTitle, bookAuthor, bookPages;

    CustomAdapter customAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        createNewRecord = findViewById(R.id.createnewrecord);
        bookRecycleView = findViewById(R.id.bookrecycleview);

        myDatabaseHelper = new MyDatabaseHelper(MainActivity.this);
        bookId = new ArrayList<>();
        bookTitle = new ArrayList<>();
        bookAuthor = new ArrayList<>();
        bookPages = new ArrayList<>();

        storeBookDataArrayList();

        customAdapter = new CustomAdapter(MainActivity.this, bookId, bookTitle, bookAuthor, bookPages);

        bookRecycleView.setAdapter(customAdapter);
        bookRecycleView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        createNewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRecordIntent = new Intent(MainActivity.this, AddRecordActivity.class);
                startActivity(addRecordIntent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void storeBookDataArrayList() {
        Cursor cursor = myDatabaseHelper.readAllBookData();
        if (cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No Records Found!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                bookId.add(cursor.getString(0));
                bookTitle.add(cursor.getString(1));
                bookAuthor.add(cursor.getString(2));
                bookPages.add(cursor.getString(3));
            }
        }
    }
}