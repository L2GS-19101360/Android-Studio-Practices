package com.example.taskmate;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateAccountActivity extends AppCompatActivity {

    EditText updateUserFirstNameInput, updateUserLastNameInput;
    EditText updateUserEmail;
    EditText updateUserPassword, updateConfirmUserPasswordInput;
    Button updateUserAccountButton;

    boolean isPasswordVisible = false, isConfirmPasswordVisible = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_account);

        updateUserFirstNameInput = findViewById(R.id.updateuserfirstnameinput);
        updateUserLastNameInput = findViewById(R.id.updateuserlastnameinput);

        updateUserEmail = findViewById(R.id.updateuseremailinput);

        updateUserPassword = findViewById(R.id.updateuserpasswordinput);
        updateUserPassword.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Check if the user tapped the drawable end
                Drawable drawableEnd = updateUserPassword.getCompoundDrawables()[2];
                if (drawableEnd != null && event.getRawX() >= (updateUserPassword.getRight() - drawableEnd.getBounds().width())) {
                    // Toggle password visibility
                    if (isPasswordVisible) {
                        updateUserPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        updateUserPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password_24px, 0);
                        isPasswordVisible = false;
                    } else {
                        updateUserPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        updateUserPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hide_password_24px, 0);
                        isPasswordVisible = true;
                    }
                    // Set the cursor at the end of the text
                    updateUserPassword.setSelection(updateUserPassword.length());
                    return true;
                }
            }
            return false;
        });

        updateConfirmUserPasswordInput = findViewById(R.id.updateconfirmuserpasswordinput);
        updateConfirmUserPasswordInput.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Check if the user tapped the drawable end
                Drawable drawableEnd = updateConfirmUserPasswordInput.getCompoundDrawables()[2];
                if (drawableEnd != null && event.getRawX() >= (updateConfirmUserPasswordInput.getRight() - drawableEnd.getBounds().width())) {
                    // Toggle password visibility
                    if (isConfirmPasswordVisible) {
                        updateConfirmUserPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        updateConfirmUserPasswordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password_24px, 0);
                        isConfirmPasswordVisible = false;
                    } else {
                        updateConfirmUserPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        updateConfirmUserPasswordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hide_password_24px, 0);
                        isConfirmPasswordVisible = true;
                    }
                    // Set the cursor at the end of the text
                    updateConfirmUserPasswordInput.setSelection(updateConfirmUserPasswordInput.length());
                    return true;
                }
            }
            return false;
        });

        updateUserAccountButton = findViewById(R.id.updateuseraccountbutton);
        updateUserAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}