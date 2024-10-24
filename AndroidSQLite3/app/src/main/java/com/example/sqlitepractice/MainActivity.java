package com.example.sqlitepractice;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText enterNewUsername, enterNewPassword;
    Button addUserButton, viewUsersButton;
    RecyclerView displayUserRecycleView;

    DatabaseAdapter dbAdapterHelper;
    ArrayList<UserData> userList;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterNewUsername = findViewById(R.id.enternewusername);
        enterNewPassword = findViewById(R.id.enternewpassword);
        displayUserRecycleView = findViewById(R.id.displayuserrecycleview);

        addUserButton = findViewById(R.id.adduserbutton);
        viewUsersButton = findViewById(R.id.viewusersbutton);

        dbAdapterHelper = new DatabaseAdapter(this);
        userList = new ArrayList<>();

        displayUserRecycleView.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter(userList, user -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("userId", user.getUserId());
            intent.putExtra("username", user.getUserName());
            intent.putExtra("password", user.getUserPassword());
            startActivity(intent);
        });

        displayUserRecycleView.setAdapter(userAdapter);

        addUserButton.setOnClickListener(view -> {
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
        });

        viewUsersButton.setOnClickListener(view -> fetchAndDisplayUsers());
    }

    private void fetchAndDisplayUsers() {
        userList.clear();
        Cursor cursor = dbAdapterHelper.getData();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String password = cursor.getString(2);
                userList.add(new UserData(id, name, password));
            } while (cursor.moveToNext());
            cursor.close();
        }

        userAdapter.notifyDataSetChanged();
    }
}
