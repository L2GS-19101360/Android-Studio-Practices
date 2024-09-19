package com.example.userinfodisplayer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText enterUserId, enterUserFirstName, enterUserLastName, enterUserCourse;
    Button submitButton;
    TextView userInfoDisplay;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        enterUserId = findViewById(R.id.enteruserid);
        enterUserFirstName = findViewById(R.id.enteruserfirstname);
        enterUserLastName = findViewById(R.id.enteruserlastname);
        enterUserCourse = findViewById(R.id.enterusercourse);

        submitButton = findViewById(R.id.submitbutton);

        userInfoDisplay = findViewById(R.id.userinfodisplay);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInfo = "ID: " + enterUserId.getText().toString() + "\n" +
                                  "First Name: " + enterUserFirstName.getText().toString() + "\n" +
                                  "Last Name: " + enterUserLastName.getText().toString() + "\n" +
                                  "Course: " + enterUserCourse.getText().toString() + "\n";

                userInfoDisplay.setText(userInfo);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}