package Model;

import javafx.beans.property.SimpleStringProperty;

public class Users {
    private final SimpleStringProperty id;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty username;
    private final SimpleStringProperty address;
    private final int maxBookIssue = 5;

    public Users(String id, String firstName, String lastName, String username, String address) {
        this.id = new SimpleStringProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.username = new SimpleStringProperty(username);
        this.address = new SimpleStringProperty(address);
    }


    public String getId() {
        return id.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() { return lastName.get(); }

    public String getUsername() {
        return username.get();
    }

    public String getAddress() {
        return address.get();
    }

    public int getMaxBookIssue() {
        return maxBookIssue;
    }
}
