package com.example.taskmate;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateAccountActivity extends AppCompatActivity {

    EditText updateUserFirstNameInput, updateUserLastNameInput;
    EditText updateUserEmail;
    EditText updateUserPassword, updateConfirmUserPasswordInput;
    Button updateUserAccountButton;

    boolean isPasswordVisible = false, isConfirmPasswordVisible = false;

    UserData userData;

    private FirebaseAuth myAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_account);

        updateUserFirstNameInput = findViewById(R.id.updateuserfirstnameinput);
        updateUserLastNameInput = findViewById(R.id.updateuserlastnameinput);
        updateUserEmail = findViewById(R.id.updateuseremailinput);

        userData = getIntent().getParcelableExtra("userData");

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

        myAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User Accounts");

        if (userData != null) {
            // Populate input fields with userData
            updateUserFirstNameInput.setText(userData.getUserFirstName());
            updateUserLastNameInput.setText(userData.getUserLastName());
            updateUserEmail.setText(userData.getUserEmail());
            updateUserPassword.setText(userData.getUserPassword());
        } else {

        }

        updateUserAccountButton = findViewById(R.id.updateuseraccountbutton);
        updateUserAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAccount();
            }
        });

    }

    public void updateAccount() {
        String updateFirstName = updateUserFirstNameInput.getText().toString().trim();
        String updateLastName = updateUserLastNameInput.getText().toString().trim();
        String updateEmail = updateUserEmail.getText().toString().trim();
        String updatePassword = updateUserPassword.getText().toString().trim();
        String updateConfirmPassword = updateConfirmUserPasswordInput.getText().toString().trim();

        if (userDataFilled(updateFirstName, updateLastName, updateEmail, updatePassword, updateConfirmPassword)) {
            return; // Exit if validation fails
        }



    }

    public boolean userDataFilled(String firstName, String lastName, String email, String password, String confirmPassword) {
        if (firstName.isEmpty()) {
            updateUserFirstNameInput.setError("First Name Required!");
            return true;
        } else if (lastName.isEmpty()) {
            updateUserLastNameInput.setError("Last Name Required!");
            return true;
        } else if (email.isEmpty()) {
            updateUserEmail.setError("Email Address Required!");
            return true;
        } else if (!isValidEmailPattern(email)) {
            updateUserEmail.setError("Email Address Invalid!");
            return true;
        } else if (password.isEmpty()) {
            updateUserPassword.setError("Password Required!");
            return true;
        } else if (!isValidPasswordPattern(password)) {
            updateUserPassword.setError("8 Characters Password Required!");
            return true;
        } else if (confirmPassword.isEmpty()) {
            updateConfirmUserPasswordInput.setError("Confirm Password Required!");
            return true;
        } else if (!isValidPasswordPattern(confirmPassword)) {
            updateConfirmUserPasswordInput.setError("8 Characters Password Required!");
            return true;
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Passwords Mismatched!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean isValidEmailPattern(final String email) {
        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public boolean isValidPasswordPattern(final String password) {
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^.{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
}