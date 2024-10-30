package com.example.suicocontactlistfirebase;

public class ContactData {

    private String contactKey, contactName;
    private int contactNumber;

    public ContactData(String contactKey, String contactName, int contactNumber) {
        this.contactKey = contactKey;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public String getContactKey() {
        return contactKey;
    }

    public void setContactKey(String contactKey) {
        this.contactKey = contactKey;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }
}
