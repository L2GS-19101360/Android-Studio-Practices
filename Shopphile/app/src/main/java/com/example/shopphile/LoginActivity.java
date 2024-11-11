package com.example.shopphile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText userEmailInput, userPasswordInput;
    Button loginButton;

    TextView toRegisterActivity;

    private FirebaseAuth fbAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        userEmailInput = findViewById(R.id.useremailinput);
        userPasswordInput = findViewById(R.id.userpasswordinput);

        fbAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
            }
        });

        toRegisterActivity = findViewById(R.id.toregisteractivity);
        toRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public void loginUserAccount() {
        if (userEmailInput.getText().toString().trim().isEmpty()) {
            userEmailInput.setError("Email Required!");
            return;
        } else if (!isValidEmailPattern(userEmailInput.getText().toString())) {
            userEmailInput.setError("Invalid Email Address!");
            return;
        }
        if (userPasswordInput.getText().toString().trim().isEmpty()) {
            userPasswordInput.setError("Password Required!");
            return;
        }

        String userEmail = userEmailInput.getText().toString().trim();
        String userPassword = userPasswordInput.getText().toString().trim();

        fbAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // After login success, retrieve user data from Firebase
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference userDataRef = database.getReference("users");

                    // Query the database to find the user by email
                    userDataRef.orderByChild("email").equalTo(userEmail).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                DataSnapshot dataSnapshot = task.getResult();
                                if (dataSnapshot.exists()) {
                                    // Iterate through the result (there could be multiple users with the same email)
                                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                        // Get user data from the snapshot
                                        String key = userSnapshot.child("key").getValue(String.class);
                                        String firstname = userSnapshot.child("firstname").getValue(String.class);
                                        String lastname = userSnapshot.child("lastname").getValue(String.class);
                                        String address = userSnapshot.child("address").getValue(String.class);
                                        String email = userSnapshot.child("email").getValue(String.class);
                                        String password = userSnapshot.child("password").getValue(String.class);
                                        int contactnumber = userSnapshot.child("contactnumber").getValue(Integer.class);

                                        // Create a UserData object
                                        UserData userData = new UserData(key, firstname, lastname, address, email, password, contactnumber);

                                        // Pass the user data to the next activity via Intent
                                        Toast.makeText(getApplicationContext(), "User logged in successed!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("userData", userData);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "User data not found!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to retrieve user data!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "User logged in failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public boolean isValidEmailPattern(final String email) {
        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";


        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();
    }
}