package com.books.mohdarif.bookify;

/**
 * Created by Mohd Arif on 28-07-2019.
 */

public class RecommendedBooks {
    private String language,title,edition,auther,email;
    RecommendedBooks(){}
    RecommendedBooks(String language,String title,String edition,String auther,String email){
        this.language = language;
        this.auther = auther;
        this.edition = edition;
        this.title = title;
        this.email = email;
    }
    public void setAuther(String auther) {
        this.auther = auther;
    }
    public void setEdition(String edition) {
        this.edition = edition;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuther() {
        return auther;
    }
    public String getEdition() {
        return edition;
    }
    public String getLanguage() {
        return language;
    }
    public String getTitle() {
        return title;
    }
    public void setEmail(String email) {this.email = email;}
    public String getEmail() {return email;}
}
