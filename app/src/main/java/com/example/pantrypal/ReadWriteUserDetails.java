package com.example.pantrypal;

public class ReadWriteUserDetails {
    public String name, surname, lastname, phone, iin, date,roles,gender;

    public ReadWriteUserDetails(String name, String surname, String lastname, String phone, String iin, String date,String roles,String gender) {
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.phone = phone;
        this.iin = iin;
        this.date = date;
        this.roles = roles;
        this.gender = gender;
    }
}
