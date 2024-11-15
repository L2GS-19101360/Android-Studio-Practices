package com.example.suicopracticeapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    EditText employeeNameInput, employeeAddressInput, employeeContactInput;
    Button saveEmployeeButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    EmployeeInfo employeeInfo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        employeeNameInput = findViewById(R.id.employeenameinput);
        employeeAddressInput = findViewById(R.id.employeeaddressinput);
        employeeContactInput = findViewById(R.id.employeecontactinput);

        firebaseDatabase = FirebaseDatabase.getInstance("https://suicopracticeapplication-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("EmployeeInfo");

        employeeInfo = new EmployeeInfo();

        saveEmployeeButton = findViewById(R.id.saveemployeebutton);
        saveEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Button is clicked", Toast.LENGTH_SHORT).show();
                String name = employeeNameInput.getText().toString();
                String address = employeeAddressInput.getText().toString();
                String contactnumber = employeeContactInput.getText().toString();

                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(address) && TextUtils.isEmpty(contactnumber)) {
                    Toast.makeText(getApplicationContext(), "Employee Data Fields Required!", Toast.LENGTH_SHORT).show();
                } else {
                    addEmployeeData(name, address, contactnumber);
                }

            }
        });

    }

    public void addEmployeeData(String name, String address, String contactnumber) {
        // Set the data to the employeeInfo object
        employeeInfo.setEmployeeName(name);
        employeeInfo.setEmployeeAddress(address);
        employeeInfo.setEmployeeContactNumber(contactnumber);

        // Use push() to create a new unique node for each employee
        databaseReference.push().setValue(employeeInfo)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Employee info added successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to add employee info!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}