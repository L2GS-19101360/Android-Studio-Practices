package com.example.suicocontactlistfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText contactNameInput, contactNumberInput;
    Button createContactButton;
    RecyclerView displayAllContacts;

    ArrayList<ContactData> contactList = new ArrayList<>();
    ContactAdapter contactAdapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactNameInput = findViewById(R.id.contactnameinput);
        contactNumberInput = findViewById(R.id.contactnumberinput);
        createContactButton = findViewById(R.id.createcontactbutton);
        displayAllContacts = findViewById(R.id.displayallcontacts);

        // Initialize the database helper and adapter
        databaseHelper = new DatabaseHelper();
        contactAdapter = new ContactAdapter(contactList, contact -> {
            Toast.makeText(MainActivity.this, "Clicked: " + contact.getContactName(), Toast.LENGTH_SHORT).show();
        });

        displayAllContacts.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(contactList, contact -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("selected_contactkey", contact.getContactKey());
            intent.putExtra("selected_contactname", contact.getContactName());
            intent.putExtra("selected_contactnumber", String.valueOf(contact.getContactNumber()));
            startActivity(intent);
        });
        displayAllContacts.setAdapter(contactAdapter);

        // Fetch data from Firebase
        databaseHelper.fetchAllDatafromDB(contactList, contactAdapter);

        createContactButton.setOnClickListener(view -> {
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
        });
    }
}
