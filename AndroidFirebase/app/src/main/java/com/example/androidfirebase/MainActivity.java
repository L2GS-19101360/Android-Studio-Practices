package com.example.androidfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText createNewUsername, createNewPassword;
    Button addUserButton;
    RecyclerView viewAllUsersRecycleView;

    ArrayList<UserData> userList = new ArrayList<>();
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNewUsername = findViewById(R.id.createnewusername);
        createNewPassword = findViewById(R.id.createnewpassword);
        addUserButton = findViewById(R.id.adduserbutton);
        viewAllUsersRecycleView = findViewById(R.id.viewallusersrecycleview);

        viewAllUsersRecycleView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(userList, user -> {
            // Handle item click here
//            Toast.makeText(MainActivity.this, "Clicked: " + user.getUsername(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ModifyActivity.class);
            intent.putExtra("selected_key", user.getKey());
            intent.putExtra("selected_user", user.getUsername());
            intent.putExtra("selected_password", user.getPassword());
            startActivity(intent);
        });
        viewAllUsersRecycleView.setAdapter(userAdapter);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = createNewUsername.getText().toString().trim();
                String password = createNewPassword.getText().toString().trim();

                if (username.isEmpty()) {
                    createNewUsername.setError("Username required");
                    return;
                }
                if (password.isEmpty()) {
                    createNewPassword.setError("Password required");
                    return;
                }

                addDataToDB(username, password);
            }
        });

        fetchDataFromDB();
    }

    private void addDataToDB(String username, String password) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userDataRef = database.getReference("users");

        String key = userDataRef.push().getKey();
        HashMap<String, Object> dataHashMap = new HashMap<>();
        dataHashMap.put("key", key);
        dataHashMap.put("username", username);
        dataHashMap.put("password", password);

        userDataRef.child(key).setValue(dataHashMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(MainActivity.this, "User added", Toast.LENGTH_SHORT).show();
                createNewUsername.getText().clear();
                createNewPassword.getText().clear();
            } else {
                Toast.makeText(MainActivity.this, "Failed to add user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchDataFromDB() {
        DatabaseReference userDataRef = FirebaseDatabase.getInstance().getReference("users");
        userDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    UserData user = userSnapshot.getValue(UserData.class);
                    if (user != null) userList.add(user);
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
