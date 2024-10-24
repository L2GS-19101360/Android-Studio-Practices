package com.example.sqlitepractice;

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

public class MainActivity2 extends AppCompatActivity {

    EditText enterUpdateUsername, enterUpdatePassword;
    Button updateUserButton, deleteUserButton;

    DatabaseAdapter dbAdapterHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        enterUpdateUsername = findViewById(R.id.enterupdateusername);
        enterUpdatePassword = findViewById(R.id.enterupdatepassword);
        updateUserButton = findViewById(R.id.updateuserbutton);
        deleteUserButton = findViewById(R.id.deleteuserbutton);

        int userId = getIntent().getIntExtra("userId", -1);
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        enterUpdateUsername.setText(username);
        enterUpdatePassword.setText(password);

        dbAdapterHelper = new DatabaseAdapter(this);

        updateUserButton.setOnClickListener(view -> {

        });

        deleteUserButton.setOnClickListener(view -> {
            if (userId != -1) {
                dbAdapterHelper.deleteBookRecord(userId);
                Toast.makeText(MainActivity2.this, "User deleted successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity2.this, "Error: User ID not found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}