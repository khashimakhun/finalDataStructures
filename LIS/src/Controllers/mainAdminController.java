package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class mainAdminController extends SceneHandler {

    @FXML
    private Button addBook_btn;

    @FXML
    private Button viewBooks_btn;

    @FXML
    private Button issuedBooks_btn;

    @FXML
    private Button viewMembers_btn;

    @FXML
    private Button delete_btn;

    @FXML
    private Button signOut_btn;


    @FXML
    void addBookHandler(ActionEvent event) {
        openNewScene("/fxml/addbook.fxml", addBook_btn);
    }

    @FXML
    void deleteHandler(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/deleteBook.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Delete Book");
        stage.show();
    }

    @FXML
    void issuedBooksHandler(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/issuedListUser.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("List of Issued Books by Users");
        stage.show();
    }

    @FXML
    void signOutHandler(ActionEvent event) { openNewScene("/fxml/login.fxml", signOut_btn); }

    @FXML
    void viewBooksHandler(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/bookListAdmin.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("List of Books in Library");
        stage.show();
    }

    @FXML
    void viewMembersHandler(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/userList.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("List of Users");
        stage.show();
    }
}
