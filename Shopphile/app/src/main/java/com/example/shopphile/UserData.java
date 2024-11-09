package com.example.shopphile;

import android.os.Parcel;
import android.os.Parcelable;

public class UserData implements Parcelable {

    private String firstname, lastname, address, email, password;
    private int contactnumber;

    public UserData() {
    }

    public UserData(String firstname, String lastname, String address, String email, String password, int contactnumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.email = email;
        this.password = password;
        this.contactnumber = contactnumber;
    }

    // Parcelable implementation
    protected UserData(Parcel in) {
        firstname = in.readString();
        lastname = in.readString();
        address = in.readString();
        email = in.readString();
        password = in.readString();
        contactnumber = in.readInt();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(address);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeInt(contactnumber);
    }

    // Getters and setters (same as in your code)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(int contactnumber) {
        this.contactnumber = contactnumber;
    }
}
