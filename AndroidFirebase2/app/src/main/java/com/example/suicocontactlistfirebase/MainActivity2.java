package com.example.suicocontactlistfirebase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    EditText updateContactNameInput, updateContactNumberInput;
    Button updateContactButton, deleteContactButton;

    String getContactKey, getContactName, getContactNumber;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        updateContactNameInput = findViewById(R.id.updatecontactnameinput);
        updateContactNumberInput = findViewById(R.id.updatecontactnumberinput);

        getContactKey = getIntent().getStringExtra("selected_contactkey");
        getContactName = getIntent().getStringExtra("selected_contactname");
        getContactNumber = getIntent().getStringExtra("selected_contactnumber");

        updateContactNameInput.setText(getContactName);
        updateContactNumberInput.setText(getContactNumber);

        updateContactButton = findViewById(R.id.updatecontactbutton);
        updateContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        deleteContactButton = findViewById(R.id.deletecontactbutton);
        deleteContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}