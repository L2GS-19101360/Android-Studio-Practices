package com.example.taskmate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView displayName;

    UserData userData;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        displayName = findViewById(R.id.displayname);

        userData = getIntent().getParcelableExtra("userData");
        if (userData != null) {
            displayName.setText(userData.getUserId() + ": " +userData.getUserFirstName() + " " + userData.getUserLastName());
        } else {
            displayName.setText("No user data available");
        }
    }
}