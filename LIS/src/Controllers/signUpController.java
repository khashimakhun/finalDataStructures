package Controllers;

import DatabaseHandler.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class signUpController extends SceneHandler {

    @FXML
    private TextField firstname_txt;

    @FXML
    private TextField lastname_txt;

    @FXML
    private PasswordField password1_txt;

    @FXML
    private PasswordField password2_txt;

    @FXML
    private TextField username_txt;

    @FXML
    private TextField address_txt;

    @FXML
    private Button signup_btn;

    @FXML
    private Button cancel_btn;

    @FXML
    void cancelHandler(ActionEvent event) {
        openNewScene("/fxml/login.fxml", cancel_btn);
    }

    @FXML
    void signUpHandler(ActionEvent event) {
        ConnectionDB connectionDB = new ConnectionDB();
        String firstame = firstname_txt.getText().trim();
        String lastname = lastname_txt.getText().trim();
        String username = username_txt.getText().trim();
        String password1 = password1_txt.getText().trim();
        String password2 = password2_txt.getText().trim();
        String address = address_txt.getText().trim();

        if(firstame.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter a firstname");
        } else if(lastname.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter a lastname");
        } else if(username.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter a username");
        } else if(password1.equals("") && password2.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter a password");
        } else if(address.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter a address");
        } else if(!password1.equals(password2)) {
            JOptionPane.showMessageDialog(null, "Passwords do not matches");
        } else if(password1.length() < 6 && password2.length() < 6) {
            JOptionPane.showMessageDialog(null, "Password has to be greater than 6 chars");
        } else if(username.length() < 5)
            JOptionPane.showMessageDialog(null, "Username length has to be greater 5 chars");
        else {
            try {
                String query = "INSERT INTO users_table (first_name, last_name, user_name, password, address) VALUES (?,?,?,?,?)";
                PreparedStatement ps = connectionDB.getDbConnection().prepareStatement(query);

                ps.setString(1, firstame);
                ps.setString(2, lastname);
                ps.setString(3, username);
                ps.setString(4, password1);
                ps.setString(5, address);

                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "You successfully registered!");
                    openNewScene("/fxml/main.fxml", signup_btn);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
