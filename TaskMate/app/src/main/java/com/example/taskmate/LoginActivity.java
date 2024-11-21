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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText userEmailInput, userPasswordInput;
    Button loginButton;
    TextView toRegisterPage;

    boolean isPasswordVisible = false;

    private FirebaseAuth myAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    UserData userData;

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

        myAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User Accounts");

        loginButton = findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
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

    public void loginUserAccount() {
        String email = userEmailInput.getText().toString().trim();
        String password = userPasswordInput.getText().toString().trim();

        if (inputDataFilled(email, password)) {
            return;
        }

        myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userId = myAuth.getCurrentUser().getUid();

                    databaseReference.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful() && task.getResult().exists()) {
                                DataSnapshot dataSnapshot = task.getResult();

                                // Get first name and last name
                                String firstName = dataSnapshot.child("userFirstName").getValue(String.class);
                                String lastName = dataSnapshot.child("userLastName").getValue(String.class);
                                String email = dataSnapshot.child("userEmail").getValue(String.class);
                                String password = dataSnapshot.child("userPassword").getValue(String.class);

                                userData = new UserData(userId, firstName, lastName, email, password);

                                // Proceed to MainActivity
                                Toast.makeText(getApplicationContext(), "User Account success to logged in", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("userData", userData);
                                startActivity(intent);
                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "User Account failed to logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean inputDataFilled(String email, String password) {
        if (email.isEmpty()) {
            userEmailInput.setError("User Email Required!");
            return true;
        } else if (password.isEmpty()) {
            userPasswordInput.setError("User Password Required!");
            return true;
        }
        return false;
    }
}