package com.example.taskmate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    EditText userEmailInput, userPasswordInput;
    Button loginButton;
    TextView toRegisterPage;

    boolean isPasswordVisible = false;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        userEmailInput = findViewById(R.id.useremailinput);
        userPasswordInput = findViewById(R.id.userpasswordinput);
        userPasswordInput.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Check if the user tapped the drawable end
                Drawable drawableEnd = userPasswordInput.getCompoundDrawables()[2];
                if (drawableEnd != null && event.getRawX() >= (userPasswordInput.getRight() - drawableEnd.getBounds().width())) {
                    // Toggle password visibility
                    if (isPasswordVisible) {
                        userPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        userPasswordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password_24px, 0);
                        isPasswordVisible = false;
                    } else {
                        userPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        userPasswordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hide_password_24px, 0);
                        isPasswordVisible = true;
                    }
                    // Set the cursor at the end of the text
                    userPasswordInput.setSelection(userPasswordInput.length());
                    return true;
                }
            }
            return false;
        });

        loginButton = findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        toRegisterPage = findViewById(R.id.toregisterpage);
        toRegisterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}