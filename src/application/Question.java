package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Question {
    private String title;
    private String description;
    private String author;

    public Question(String title, String description, String author) {
        this.title = title;
        this.description = description;
        this.author = author;
    }

    // Getters for title, description, and author
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }
}