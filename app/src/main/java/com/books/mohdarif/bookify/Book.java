package com.books.mohdarif.bookify;

/**
 * Created by Mohd Arif on 23-07-2019.
 */

public class Book {
    private String title,image,language,auther,publisher,pages,description,edition,downloadURL;
    public Book(){}
    public Book (String title,String image,String language,String auther,String publisher,String pages,String description,String edition,String downloadURL) {
        this.title = title;
        this.image = image;
        this.auther = auther;
        this.description = description;
        this.edition = edition;
        this.pages = pages;
        this.language = language;
        this.publisher = publisher;
        this.downloadURL = downloadURL;
        }
    public String getTitle() { return this.title;}
    public String getImage() { return this.image;}
    public String getAuther() { return this.auther;}
    public String getLanguage() { return this.language;}
    public String getEdition() { return this.edition;}
    public String getPublisher() { return this.publisher;}
    public String getPages() { return this.pages;}
    public String getDescription() { return this.description;}
    public String getDownloadURL() {return downloadURL;}
}
