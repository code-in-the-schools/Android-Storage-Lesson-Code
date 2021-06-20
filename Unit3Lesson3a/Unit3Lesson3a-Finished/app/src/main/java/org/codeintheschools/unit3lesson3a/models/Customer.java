package org.codeintheschools.unit3lesson3a.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Customer {

    public String name;
    public String email;
    public String phone;
    public int zipcode;
    public Boolean receiveMarketing;

    public Customer(){
        // Important!! This is needed for models that will be used with Firebase Realtime Database
        // Default constructor required for calls to DataSnapshot.getValue
    }

    public Customer(String name, String email, String phone, int zipcode, Boolean receiveMarketing){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.zipcode = zipcode;
        this.receiveMarketing = receiveMarketing;
    }
}
