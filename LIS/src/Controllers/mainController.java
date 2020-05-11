package Controllers;

import DatabaseHandler.ConnectionDB;
import DatabaseHandler.DatesDB;
import Model.Books;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class mainController extends SceneHandler {

    @FXML
    private TextField bookIDSumbit;

    @FXML
    private Button signOut_btn;

    @FXML
    private TextField bookID_field;

    @FXML
    private Text bookName_text;

    @FXML
    private Text author_text;

    @FXML
    private Text bookStatus;

    @FXML
    private TextField memberID_field;

    @FXML
    private Text memberName_text;

    @FXML
    private Text firstName_text;

    @FXML
    private Text bookNameSubmit_text;

    @FXML
    private Text authorSubmit_text;

    ObservableList<Books> list = FXCollections.observableArrayList();
    ConnectionDB connectionDB = new ConnectionDB();
    DatesDB datesDB = new DatesDB();

    @FXML
    void checkBookAction(ActionEvent event) throws SQLException {
        clearBookCache();

        Boolean flag = false;
        String id = bookID_field.getText();
        String query = "SELECT * FROM book_table WHERE idbook_table = '" + id +  "'";
        PreparedStatement ps = connectionDB.getDbConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery(query);

        try {
            while (rs.next()) {
                String bName = rs.getString(datesDB.BOOK_TITLE);
                String bAuthor = rs.getString(datesDB.BOOK_AUTHOR);
                String bAmount = rs.getString(datesDB.BOOK_AMOUNT);
                Boolean bStatus;
                flag = true;

                if(Integer.parseInt(bAmount) > 0)
                    bStatus = true;
                else
                    bStatus = false;

                bookName_text.setText(bName);
                author_text.setText(bAuthor);
                bookStatus.setText(String.valueOf(bStatus));
            }

            if (!flag) {
                bookName_text.setText("No such book");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void checkSubmitAction(ActionEvent event) throws SQLException {
        clearBookSubmitCache();

        Boolean flag = false;
        String id = bookIDSumbit.getText();
        String query = "SELECT * FROM book_table WHERE idbook_table = '" + id +  "'";
        PreparedStatement ps = connectionDB.getDbConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery(query);

        try {
            while (rs.next()) {
                String bName = rs.getString(datesDB.BOOK_TITLE);
                String bAuthor = rs.getString(datesDB.BOOK_AUTHOR);

                flag = true;

                bookNameSubmit_text.setText(bName);
                authorSubmit_text.setText(bAuthor);
            }

            if (!flag) {
                bookName_text.setText("No such book");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void checkUserAction(ActionEvent event) throws SQLException {
        clearUserCache();

        Boolean flag = false;
        String id = memberID_field.getText();
        String query = "SELECT * FROM users_table WHERE idusers_table = '" + id +  "'";
        PreparedStatement ps = connectionDB.getDbConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery(query);

        try {
            while (rs.next()) {
                String username = rs.getString(datesDB.USERNAME);
                String fName = rs.getString(datesDB.USER_FN);

                flag = true;
                memberName_text.setText(username);
                firstName_text.setText(fName);
            }

            if (!flag) {
                bookName_text.setText("No such user");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void clearBookCache() {
        bookName_text.setText("");
        author_text.setText("");
        bookStatus.setText("");
    }

    void clearUserCache() {
        memberName_text.setText("");
        firstName_text.setText("");
    }

    void clearBookSubmitCache() {
        bookNameSubmit_text.setText("");
        authorSubmit_text.setText("");
    }

    @FXML
    void issueNowHandler(ActionEvent event) throws SQLException {
        String userID = memberID_field.getText().trim();
        String bookID = bookID_field.getText().trim();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm issue operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to issue the book '" + bookName_text.getText() + "'\n to '" + memberName_text.getText() + "'?");

        Optional<ButtonType> response = alert.showAndWait();
        if(response.get()==ButtonType.OK) {
            String query1 = "INSERT INTO issued_books_table(book_id, user_id, issue_time) VALUES (?,?,?)";
            PreparedStatement ps = connectionDB.getDbConnection().prepareStatement(query1);

            ps.setString(1, bookID);
            ps.setString(2, userID);
            ps.setString(3, String.valueOf(timestamp));

            if (ps.executeUpdate() > 0) {
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setTitle("Success");
                alert1.setHeaderText(null);
                alert1.setContentText("Book issue complete");
                alert1.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Issue operation failed");
                alert1.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Canceled");
            alert1.setHeaderText(null);
            alert1.setContentText("Issue operation canceled");
            alert1.showAndWait();
        }
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
        stage.setTitle("Issued Books");
        stage.show();
    }

    @FXML
    void signOutHandler(ActionEvent event) { openNewScene("/fxml/login.fxml", signOut_btn); }

    @FXML
    void submissionAction(ActionEvent event) throws SQLException {
        String id = bookIDSumbit.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm submission operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to return the book ?");

        Optional<ButtonType> response = alert.showAndWait();

        if(response.get()==ButtonType.OK) {
            String query = "DELETE FROM issued_books_table WHERE book_id = '" + id + "'";
            PreparedStatement ps = connectionDB.getDbConnection().prepareStatement(query);
            ps.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "You successfully submitted the Book!");
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Canceled");
            alert1.setHeaderText(null);
            alert1.setContentText("Submission operation canceled");
            alert1.showAndWait();
        }
    }

    @FXML
    void viewBooksHandler(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/bookList.fxml"));
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

}
