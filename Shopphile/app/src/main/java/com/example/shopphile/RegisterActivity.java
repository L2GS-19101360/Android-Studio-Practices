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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText enterNewFirstName, enterNewLastName, enterNewAddress, enterNewContactNumber, enterNewEmail, enterNewPassword, reEnterNewPassword;
    Button registerButton;

    TextView toLoginActivity;

    private FirebaseAuth fbAuth;

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

        fbAuth = FirebaseAuth.getInstance();

        registerButton = findViewById(R.id.registerbutton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUserAccount();
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

    public void registerUserAccount() {
        if (enterNewFirstName.getText().toString().trim().isEmpty()) {
            enterNewFirstName.setError("First Name Required!");
            return;
        }
        if (enterNewLastName.getText().toString().trim().isEmpty()) {
            enterNewLastName.setError("Last Name Required!");
            return;
        }

        if (enterNewAddress.getText().toString().trim().isEmpty()) {
            enterNewAddress.setError("Address Required!");
            return;
        }

        if (enterNewContactNumber.getText().toString().trim().isEmpty()) {
            enterNewContactNumber.setError("Contact Number Required!");
            return;
        }

        if (enterNewEmail.getText().toString().trim().isEmpty()) {
            enterNewEmail.setError("Email Required!");
            return;
        } else if (!isValidEmailPattern(enterNewEmail.getText().toString())) {
            enterNewEmail.setError("Invalid Email Address!");
            return;
        }

        if (enterNewPassword.getText().toString().trim().isEmpty()) {
            enterNewPassword.setError("Password Required!");
            return;
        } else if (!isValidPasswordPattern(enterNewPassword.getText().toString())) {
            enterNewPassword.setError("Password must be at least 8 characters long!");
            return;
        }
        if (reEnterNewPassword.getText().toString().trim().isEmpty()) {
            reEnterNewPassword.setError("Password Required!");
            return;
        } else if (!isValidPasswordPattern(reEnterNewPassword.getText().toString())) {
            reEnterNewPassword.setError("Password must be at least 8 characters long!");
            return;
        }

        if (!enterNewPassword.getText().toString().trim().equals(reEnterNewPassword.getText().toString().trim())) {
            enterNewPassword.setError("Passwords Mismatched!");
            reEnterNewPassword.setError("Password Mismatched!");
            return;
        }

        String newFirstName = enterNewFirstName.getText().toString().trim();
        String newLastName = enterNewLastName.getText().toString().trim();
        String newAddress = enterNewAddress.getText().toString().trim();
        int newContactNumber = Integer.parseInt(enterNewContactNumber.getText().toString().trim());
        String newEmail = enterNewEmail.getText().toString().trim();
        String newPassword = enterNewPassword.getText().toString();

        fbAuth.createUserWithEmailAndPassword(newEmail, newPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference userDataRef = database.getReference("users");

                    String key = userDataRef.push().getKey();
                    HashMap<String, Object> dataHashMap = new HashMap<>();
                    dataHashMap.put("key", key);
                    dataHashMap.put("firstname", newFirstName);
                    dataHashMap.put("lastname", newLastName);
                    dataHashMap.put("address", newAddress);
                    dataHashMap.put("contactnumber", newContactNumber);
                    dataHashMap.put("email", newEmail);
                    dataHashMap.put("password", newPassword);

                    // Create UserData object
                    UserData userData = new UserData(key, newFirstName, newLastName, newAddress, newEmail, newPassword, newContactNumber);

                    userDataRef.child(key).setValue(dataHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "User registered success!", Toast.LENGTH_SHORT).show();

                            // Pass the UserData object via Intent
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra("userData", userData); // Pass the object here
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "User registered failed!", Toast.LENGTH_SHORT).show();
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

    public boolean isValidPasswordPattern(final String password) {
        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^.{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
}