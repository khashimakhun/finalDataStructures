package Controllers;

import DatabaseHandler.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.sql.*;

public class loginController extends SceneHandler {

    @FXML
    private TextField username_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button btn_signIn;

    @FXML
    private Button btn_signUp;


    @FXML
    void signInHandler(ActionEvent event) {
        ConnectionDB connectionDB = new ConnectionDB();
        String username = username_field.getText();
        String password = String.valueOf(password_field.getText());

        try {
            String query = "Select * from users_table where user_name=? AND password =?";
            PreparedStatement ps = connectionDB.getDbConnection().prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                JOptionPane.showMessageDialog(null, "Username and Password matched");
                if (username.equals("admin"))
                    openNewScene("/fxml/mainAdmin.fxml", btn_signIn);
                else
                    openNewScene("/fxml/main.fxml", btn_signIn);
            } else {
                JOptionPane.showMessageDialog(null, "Username or Password are incorrect");
                username_field.setText("");
                password_field.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signUpHandler(ActionEvent event) {
        openNewScene("/fxml/signUp.fxml", btn_signUp);
    }

}