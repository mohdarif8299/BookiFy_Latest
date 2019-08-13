package com.books.mohdarif.bookify;

/**
 * Created by Mohd Arif on 23-07-2019.
 */

public class User {
    private String fname,lname,email,password,number;
    public User() {
    }
    public User(String email,String password){
        this.email = email;
        this.password = password;
    }
    public User(String fname,String lname,String email,String password,String number){
        this.fname= fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.number = number;
    }
    public String getFname() {return this.fname;}
    public String getLname() {return this.lname;}
    public String getEmail() {return this.email;}
    public String getPassword() {return this.password;}
    public String getNumber(){ return this.number;}
}
