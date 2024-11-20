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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText newUserFirstNameInput, newUserLastNameInput;
    EditText newUserEmailInput;
    EditText newUserPasswordInput, confirmUserPasswordInput;

    Button registerButton;

    TextView toLoginPage;

    boolean isPasswordVisible = false, isConfirmPasswordVisible = false;

    private FirebaseAuth myAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    UserData userData;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
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

        myAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User Accounts");

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUserAccount();
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

    public void registerUserAccount() {
        String firstName = newUserFirstNameInput.getText().toString().trim();
        String lastName = newUserLastNameInput.getText().toString().trim();
        String email = newUserEmailInput.getText().toString().trim();
        String password = newUserPasswordInput.getText().toString();
        String confirmPassword = confirmUserPasswordInput.getText().toString();

        if (userDataFilled(firstName, lastName, email, password, confirmPassword)) {
            return; // Exit if validation fails
        }

        myAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Initialize userData
                    userData = new UserData(firstName, lastName, email, password);

                    String userId = myAuth.getCurrentUser().getUid();
                    databaseReference.child(userId).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "User Account successfully registered", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "User Account failed to register in Database", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "User Account failed to register in Authentication", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean userDataFilled(String firstName, String lastName, String email, String password, String confirmPassword) {
        if (firstName.isEmpty()) {
            newUserFirstNameInput.setError("First Name Required!");
            return true;
        } else if (lastName.isEmpty()) {
            newUserLastNameInput.setError("Last Name Required!");
            return true;
        } else if (email.isEmpty()) {
            newUserEmailInput.setError("Email Address Required!");
            return true;
        } else if (!isValidEmailPattern(email)) {
            newUserEmailInput.setError("Email Address Invalid!");
            return true;
        } else if (password.isEmpty()) {
            newUserPasswordInput.setError("Password Required!");
            return true;
        } else if (!isValidPasswordPattern(password)) {
            newUserPasswordInput.setError("8 Characters Password Required!");
            return true;
        } else if (confirmPassword.isEmpty()) {
            confirmUserPasswordInput.setError("Confirm Password Required!");
            return true;
        } else if (!isValidPasswordPattern(confirmPassword)) {
            confirmUserPasswordInput.setError("8 Characters Password Required!");
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