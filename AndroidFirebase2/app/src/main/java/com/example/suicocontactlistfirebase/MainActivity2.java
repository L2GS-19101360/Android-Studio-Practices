package com.example.suicocontactlistfirebase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    EditText updateContactNameInput, updateContactNumberInput;
    Button updateContactButton, deleteContactButton;

    String getContactKey, getContactName, getContactNumber;

    DatabaseHelper databaseHelper;

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

        databaseHelper = new DatabaseHelper();

        updateContactButton = findViewById(R.id.updatecontactbutton);
        updateContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedContactName = updateContactNameInput.getText().toString().trim();
                String updatedContactNumber = updateContactNumberInput.getText().toString().trim();

                if (updatedContactName.isEmpty()) {
                    updateContactNameInput.setError("Contact Name Required!");
                    return;
                }
                if (updatedContactNumber.isEmpty()) {
                    updateContactNumberInput.setError("Contact Number Required!");
                    return;
                }

                try {
                    int contactNumber = Integer.parseInt(updatedContactNumber);
                    databaseHelper.updateDataInDB(getContactKey, updatedContactName, contactNumber, new DatabaseCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(MainActivity2.this, "Contact updated", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(MainActivity2.this, "Failed to update contact", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (NumberFormatException e) {
                    updateContactNumberInput.setError("Invalid Contact Number!");
                }
            }
        });

        deleteContactButton = findViewById(R.id.deletecontactbutton);
        deleteContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteDataFromDB(getContactKey, new DatabaseCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity2.this, "Contact deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(MainActivity2.this, "Failed to delete contact", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}