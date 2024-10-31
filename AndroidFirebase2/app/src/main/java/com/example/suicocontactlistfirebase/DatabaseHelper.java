package com.example.suicocontactlistfirebase;

import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public DatabaseHelper() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
    }

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
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("contacts");
        String key = dbRef.push().getKey();
        HashMap<String, Object> dataHashMap = new HashMap<>();
        dataHashMap.put("contact_key", key);
        dataHashMap.put("contact_name", contactName);
        dataHashMap.put("contact_number", contactNumber);

        dbRef.child(key).setValue(dataHashMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess();
            } else {
                callback.onFailure();
            }
        });
    }

    public void fetchAllDatafromDB(ArrayList<ContactData> contactList, ContactAdapter contactAdapter) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("contacts");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String key = data.child("contact_key").getValue(String.class);
                        String name = data.child("contact_name").getValue(String.class);
                        Integer number = data.child("contact_number").getValue(Integer.class);
                        if (key != null && name != null && number != null) {
                            contactList.add(new ContactData(key, name, number));
                        }
                    }
                    contactAdapter.notifyDataSetChanged();
                } else {
                    System.out.println("No contacts found.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Failed to fetch data: " + error.getMessage());
            }
        });
    }

    public void updateDataInDB(String key, String contactName, int contactNumber) {

    }
}
