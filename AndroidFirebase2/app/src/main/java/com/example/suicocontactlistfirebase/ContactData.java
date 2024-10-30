package com.example.suicocontactlistfirebase;

public class ContactData {

    private String key, name;
    private int number;

    public ContactData(String key, String name, int number) {
        this.key = key;
        this.name = name;
        this.number = number;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
