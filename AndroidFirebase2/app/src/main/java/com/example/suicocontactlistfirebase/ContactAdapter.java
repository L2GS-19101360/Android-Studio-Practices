package com.example.suicocontactlistfirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private ArrayList<ContactData> contactList;
    private OnUserClickListener listener;

    public ContactAdapter(ArrayList<ContactData> contactList, OnUserClickListener listener) {
        this.contactList = contactList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactData contact = contactList.get(position);
        holder.contactName.setText(contact.getContactName());
        holder.contactNumber.setText(String.valueOf(contact.getContactNumber()));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onUserClick(contact);
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView contactName, contactNumber;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.displaycontactname);
            contactNumber = itemView.findViewById(R.id.displaycontactnumber);
        }
    }

    public interface OnUserClickListener {
        void onUserClick(ContactData contact);
    }
}
