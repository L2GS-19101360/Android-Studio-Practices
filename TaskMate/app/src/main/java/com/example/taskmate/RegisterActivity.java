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

public class RegisterActivity extends AppCompatActivity {

    EditText newUserFirstNameInput, newUserLastNameInput;
    EditText newUserEmailInput;
    EditText newUserPasswordInput, confirmUserPasswordInput;

    Button registerButton;

    TextView toLoginPage;

    boolean isPasswordVisible = false, isConfirmPasswordVisible = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        newUserFirstNameInput = findViewById(R.id.newuserfirstnameinput);
        newUserLastNameInput = findViewById(R.id.newuserlastnameinput);
        newUserEmailInput = findViewById(R.id.newuseremailinput);
        newUserPasswordInput = findViewById(R.id.newuserpasswordinput);
        newUserPasswordInput.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Check if the user tapped the drawable end
                Drawable drawableEnd = newUserPasswordInput.getCompoundDrawables()[2];
                if (drawableEnd != null && event.getRawX() >= (newUserPasswordInput.getRight() - drawableEnd.getBounds().width())) {
                    // Toggle password visibility
                    if (isPasswordVisible) {
                        newUserPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        newUserPasswordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password_24px, 0);
                        isPasswordVisible = false;
                    } else {
                        newUserPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        newUserPasswordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hide_password_24px, 0);
                        isPasswordVisible = true;
                    }
                    // Set the cursor at the end of the text
                    newUserPasswordInput.setSelection(newUserPasswordInput.length());
                    return true;
                }
            }
            return false;
        });

        confirmUserPasswordInput = findViewById(R.id.confirmuserpasswordinput);
        confirmUserPasswordInput.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Check if the user tapped the drawable end
                Drawable drawableEnd = confirmUserPasswordInput.getCompoundDrawables()[2];
                if (drawableEnd != null && event.getRawX() >= (confirmUserPasswordInput.getRight() - drawableEnd.getBounds().width())) {
                    // Toggle password visibility
                    if (isConfirmPasswordVisible) {
                        confirmUserPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        confirmUserPasswordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password_24px, 0);
                        isConfirmPasswordVisible = false;
                    } else {
                        confirmUserPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        confirmUserPasswordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hide_password_24px, 0);
                        isConfirmPasswordVisible = true;
                    }
                    // Set the cursor at the end of the text
                    confirmUserPasswordInput.setSelection(confirmUserPasswordInput.length());
                    return true;
                }
            }
            return false;
        });

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        toLoginPage = findViewById(R.id.tologinpage);
        toLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}