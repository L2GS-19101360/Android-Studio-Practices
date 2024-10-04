package com.example.androidsqlite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddRecordActivity extends AppCompatActivity {

    EditText enterBookTitle, enterBookAuthor, enterBookPages;
    Button addNewRecordButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_record);

        enterBookTitle = findViewById(R.id.enterbooktitile);
        enterBookAuthor = findViewById(R.id.enterbookauthor);
        enterBookPages = findViewById(R.id.enterbookpages);
        addNewRecordButton = findViewById(R.id.addnewrecordbutton);

        addNewRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDBHelper = new MyDatabaseHelper(AddRecordActivity.this);
                myDBHelper.createNewBookRecord(enterBookTitle.getText().toString().trim(),
                        enterBookAuthor.getText().toString().trim(),
                        Integer.valueOf(enterBookPages.getText().toString().trim()));

                Toast.makeText(AddRecordActivity.this, "Book Added Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddRecordActivity.this, MainActivity.class)); // Navigate back to MainActivity
                finish(); // Close the current activity
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
