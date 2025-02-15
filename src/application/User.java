package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The User class represents a user entity in the system.
 * It contains the user's details such as userName, password, and role.
 */
public class User {
    private final StringProperty userName;
    private final StringProperty password;
    private final StringProperty role;
    
    public User(String userName, String password, String role) {
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
    }

    // Constructor
    public User(String userName, String role) {
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty("");  // Default or empty password
        this.role = new SimpleStringProperty(role);
    }

    // Getters and setters for JavaFX TableView binding
    public String getUserName() { return userName.get(); }
    public void setUserName(String userName) { this.userName.set(userName); }
    public StringProperty userNameProperty() { return userName; }

    public String getPassword() { return password.get(); }
    public void setPassword(String password) { this.password.set(password); }
    public StringProperty passwordProperty() { return password; }

    public String getRole() { return role.get(); }
    public void setRole(String role) { this.role.set(role); }
    public StringProperty roleProperty() { return role; }
}

