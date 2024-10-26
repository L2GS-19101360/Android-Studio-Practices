package com.example.androidfirebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText createNewUsername, createNewPassword;
    Button addUserButton;

    RecyclerView viewAllUsersRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello Lorenz!");

        createNewUsername = findViewById(R.id.createnewusername);
        createNewPassword = findViewById(R.id.createnewpassword);

        viewAllUsersRecycleView = findViewById(R.id.viewallusersrecycleview);

        addUserButton = findViewById(R.id.adduserbutton);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String createUsername = createNewUsername.getText().toString();
                String createPassword = createNewPassword.getText().toString();

                if (createUsername.isEmpty()) {
                    createNewUsername.setError("Input Required!");
                    return;
                }
                if (createPassword.isEmpty()) {
                    createNewUsername.setError("Input Required!");
                    return;
                }

                addDataToDB(createUsername, createPassword);
            }
        });

    }

    public void addDataToDB(String username, String password) {
        HashMap<String, Object> dataHashMap = new HashMap<>();
        dataHashMap.put("username", username);
        dataHashMap.put("password", password);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userDataRef = database.getReference("users");

        String key = userDataRef.push().getKey();
        dataHashMap.put("key", key);

        userDataRef.child(key).setValue(dataHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
                createNewUsername.getText().clear();
                createNewPassword.getText().clear();
            }
        });


    }
}