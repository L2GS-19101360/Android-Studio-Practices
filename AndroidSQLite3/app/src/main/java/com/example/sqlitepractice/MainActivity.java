package com.example.sqlitepractice;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    EditText enterNewUsername, enterNewPassword;
    Button addUserButton, viewUsersButton;
    RecyclerView displayUserRecycleView;

    DatabaseAdapter dbAdapterHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        enterNewUsername = findViewById(R.id.enternewusername);
        enterNewPassword = findViewById(R.id.enternewpassword);
        displayUserRecycleView = findViewById(R.id.displayuserrecycleview);

        addUserButton = findViewById(R.id.adduserbutton);
        viewUsersButton = findViewById(R.id.viewusersbutton);

        dbAdapterHelper = new DatabaseAdapter(this);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = enterNewUsername.getText().toString().trim();
                String password = enterNewPassword.getText().toString().trim();

                if (!username.isEmpty() && !password.isEmpty()) {
                    long id = dbAdapterHelper.insertData(username, password);
                    if (id > 0) {
                        Toast.makeText(MainActivity.this, "User added successfully!", Toast.LENGTH_SHORT).show();
                        enterNewUsername.setText("");
                        enterNewPassword.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "Insertion failed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
