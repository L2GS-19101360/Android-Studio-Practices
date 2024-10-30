package com.example.suicocontactlistfirebase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    EditText contactNameInput, contactNumberInput;
    Button createContactButton;

    RecyclerView displayAllContacts;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an instance of DatabaseHelper and write a message to Firebase
        DatabaseHelper databaseHelper = new DatabaseHelper();
//        databaseHelper.writeMessage("Hello World!");

        contactNameInput = findViewById(R.id.contactnameinput);
        contactNumberInput = findViewById(R.id.contactnumberinput);

        displayAllContacts = findViewById(R.id.displayallcontacts);

        createContactButton = findViewById(R.id.createcontactbutton);
        createContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contactName = contactNameInput.getText().toString().trim();
                String contactNumberStr = contactNumberInput.getText().toString().trim();

                if (contactName.isEmpty()) {
                    contactNameInput.setError("Contact Name Required!");
                    return;
                }
                if (contactNumberStr.isEmpty()) {
                    contactNumberInput.setError("Contact Number Required!");
                    return;
                }

                try {
                    int contactNumber = Integer.parseInt(contactNumberStr);

                    // Call the method with a callback
                    databaseHelper.addDataToDB(contactName, contactNumber, new DatabaseCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(MainActivity.this, "User added", Toast.LENGTH_SHORT).show();
                            contactNameInput.getText().clear();
                            contactNumberInput.getText().clear();
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(MainActivity.this, "Failed to add user", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (NumberFormatException e) {
                    contactNumberInput.setError("Invalid Contact Number!");
                }
            }
        });


    }
}
