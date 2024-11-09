package com.example.shopphile;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    EditText enterNewFirstName, enterNewLastName, enterNewAddress, enterNewContactNumber, enterNewEmail, enterNewPassword, reEnterNewPassword;
    Button registerButton;

    TextView toLoginActivity;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        enterNewFirstName = findViewById(R.id.enternewfirstname);
        enterNewLastName = findViewById(R.id.enternewlastname);

        enterNewAddress = findViewById(R.id.enternewaddress);

        enterNewContactNumber = findViewById(R.id.enternewcontactnumber);

        enterNewEmail = findViewById(R.id.enternewemail);

        enterNewPassword = findViewById(R.id.enternewpassword);
        reEnterNewPassword = findViewById(R.id.reenternewpassword);

        registerButton = findViewById(R.id.registerbutton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        toLoginActivity = findViewById(R.id.tologinactivity);
        toLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}