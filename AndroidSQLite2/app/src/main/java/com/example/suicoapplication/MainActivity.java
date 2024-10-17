package com.example.suicoapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText enterUsername, enterPassword;
    Button addUserButton, viewUsersButtons;
    ListView displayUserInformation;

    DatabaseAdapter dbAdapterHelper;
    ArrayList<String> userList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        enterUsername = findViewById(R.id.enterusername);
        enterPassword = findViewById(R.id.enterpassword);
        displayUserInformation = findViewById(R.id.displayuserinformation);

        dbAdapterHelper = new DatabaseAdapter(this);
        userList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        displayUserInformation.setAdapter(adapter);

        addUserButton = findViewById(R.id.adduserbutton);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = enterUsername.getText().toString();
                String password = enterPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Snackbar.make(view, "Enter Required Information", Snackbar.LENGTH_SHORT).show();
                } else {
                    long id = dbAdapterHelper.insertData(username, password);
                    if (id <= 0) {
                        Snackbar.make(view, "Failed to add User", Snackbar.LENGTH_SHORT).show();
                        enterUsername.setText("");
                        enterPassword.setText("");
                    } else {
                        Snackbar.make(view, "Added User Successfully", Snackbar.LENGTH_SHORT).show();
                        enterUsername.setText("");
                        enterPassword.setText("");
                    }
                }
            }
        });

        viewUsersButtons = findViewById(R.id.viewusersbutton);
        viewUsersButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userList.clear();

                String data = dbAdapterHelper.getData();
                String[] rows = data.split("\n");
                for (String row : rows) {
                    if (!row.isEmpty()) {
                        userList.add(row);
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });
    }
}
