package com.example.androidsqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ModifyRecordActivity extends AppCompatActivity {

    EditText enterBookTitle2, enterBookAuthor2, enterBookPages2;
    Button updateRecordButton, deleteRecordButton;

    String getBookId, getBookTitle, getBookAuthor, getBookPages;

    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modify_record);

        enterBookTitle2 = findViewById(R.id.enterbooktitile2);
        enterBookAuthor2 = findViewById(R.id.enterbookauthor2);
        enterBookPages2 = findViewById(R.id.enterbookpages2);

        getandSetSelectedIntentData();
        myDatabaseHelper = new MyDatabaseHelper(ModifyRecordActivity.this);

        updateRecordButton = findViewById(R.id.updaterecordbutton);
        updateRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    // Get input data
                    String updatedTitle = enterBookTitle2.getText().toString();
                    String updatedAuthor = enterBookAuthor2.getText().toString();
                    int updatedPages = Integer.parseInt(enterBookPages2.getText().toString());

                    // Check for valid ID
                    if (getBookId != null && !getBookId.isEmpty()) {
                        myDatabaseHelper.updateBookRecord(updatedTitle, updatedAuthor, updatedPages, Integer.parseInt(getBookId));
                        Toast.makeText(ModifyRecordActivity.this, "Record Updated", Toast.LENGTH_SHORT).show();

                        // Navigate back to MainActivity
                        Intent intent = new Intent(ModifyRecordActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the activity stack
                        startActivity(intent);
                        finish(); // Close the current activity
                    } else {
                        Toast.makeText(ModifyRecordActivity.this, "Invalid Book ID", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(ModifyRecordActivity.this, "Invalid input for pages", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ModifyRecordActivity.this, "Update Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteRecordButton = findViewById(R.id.deleterecordbutton);
        deleteRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void confirmDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Delete " + getBookTitle + "?");
        alertBuilder.setMessage("Are you sure you want to delete " + getBookTitle + "?");
        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDatabaseHelper.deleteBookRecord(Integer.parseInt(getBookId));
                Toast.makeText(ModifyRecordActivity.this, getBookTitle + " Deleted", Toast.LENGTH_SHORT).show();

                // Navigate back to MainActivity after deleting
                Intent intent = new Intent(ModifyRecordActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss the dialog
                dialogInterface.dismiss();
            }
        });

        // Don't forget to show the dialog
        alertBuilder.create().show();
    }

    void getandSetSelectedIntentData() {
        if (getIntent().hasExtra("modify_book_id") && getIntent().hasExtra("modify_book_title") && getIntent().hasExtra("modify_book_author") && getIntent().hasExtra("modify_book_pages")) {
            getBookId = getIntent().getStringExtra("modify_book_id");
            getBookTitle = getIntent().getStringExtra("modify_book_title");
            getBookAuthor = getIntent().getStringExtra("modify_book_author");
            getBookPages = getIntent().getStringExtra("modify_book_pages");

            enterBookTitle2.setText(getBookTitle);
            enterBookAuthor2.setText(getBookAuthor);
            enterBookPages2.setText(getBookPages);
        } else {
            Toast.makeText(this, "No Record Data!", Toast.LENGTH_SHORT).show();
        }
    }
}
