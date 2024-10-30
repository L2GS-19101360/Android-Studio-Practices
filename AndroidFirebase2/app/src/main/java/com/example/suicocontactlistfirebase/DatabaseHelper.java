package com.example.suicocontactlistfirebase;

import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DatabaseHelper {

    // Firebase instance variables
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public DatabaseHelper() {
        // Initialize Firebase database and reference in the constructor
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
    }

    // Method to write a message to the database
    public void writeMessage(String message) {
        myRef.setValue(message).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("Message written successfully.");
            } else {
                System.err.println("Failed to write message: " + task.getException());
            }
        });
    }

    public void addDataToDB(String contactName, int contactNumber, DatabaseCallback callback) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference("contacts");

        String key = dbRef.push().getKey();
        HashMap<String, Object> dataHashMap = new HashMap<>();
        dataHashMap.put("contact_key", key);
        dataHashMap.put("contact_name", contactName);
        dataHashMap.put("contact_number", contactNumber);

        dbRef.child(key).setValue(dataHashMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess();  // Call success callback
            } else {
                callback.onFailure();  // Call failure callback
            }
        });
    }
}
