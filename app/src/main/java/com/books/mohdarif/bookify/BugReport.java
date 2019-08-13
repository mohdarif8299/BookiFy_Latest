package com.books.mohdarif.bookify;

/**
 * Created by Mohd Arif on 28-07-2019.
 */

public class BugReport {
    private String subject,description,email;
    public BugReport(){}
    public BugReport(String subject,String description,String email) {
        this.subject = subject;
        this.description = description;
        this.email = email;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getDescription() {
        return description;
    }
    public String getSubject() {
        return subject;
    }
    public void setEmail(String email) {this.email = email;}
    public String getEmail() {return email;}
}
