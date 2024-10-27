package com.example.androidfirebase;

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

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ModifyActivity extends AppCompatActivity {

    EditText updateNewUsername, updateNewPassword;
    Button updateUserButton, deleteUserButton;

    String getKey, getUsername, getPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modify);

        updateNewUsername = findViewById(R.id.updatenewusername);
        updateNewPassword = findViewById(R.id.updatenewpassword);

        // Corrected the intent extra keys
        getKey = getIntent().getStringExtra("selected_key");
        getUsername = getIntent().getStringExtra("selected_user");
        getPassword = getIntent().getStringExtra("selected_password");

        updateNewUsername.setText(getUsername);
        updateNewPassword.setText(getPassword);

        updateUserButton = findViewById(R.id.updateuserbutton);
        updateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updateUsername = updateNewUsername.getText().toString().trim();
                String updatePassword = updateNewPassword.getText().toString().trim();

                if (updateUsername.isEmpty()) {
                    updateNewUsername.setError("Username required");
                    return;
                }
                if (updatePassword.isEmpty()) {
                    updateNewPassword.setError("Password required");
                    return;
                }

                updateDataInDB(getKey, updateUsername, updatePassword);
            }
        });

        deleteUserButton = findViewById(R.id.deleteuserbutton);
        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDataInDB(getKey);
            }
        });

    }

    private void updateDataInDB(String key, String username, String password) {
        FirebaseDatabase.getInstance().getReference("users").child(key).updateChildren(new HashMap<String, Object>() {{
            put("username", username);
            put("password", password);
        }}).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                finish();
            } else {

            }
        });
    }

    private void deleteDataInDB(String key) {
        FirebaseDatabase.getInstance().getReference("users").child(key).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                finish();
            } else {

            }
        });
    }
}