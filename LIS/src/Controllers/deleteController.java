package Controllers;

import DatabaseHandler.ConnectionDB;
import DatabaseHandler.DatesDB;
import Model.Books;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class deleteController extends SceneHandler {

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField bookID;

    @FXML
    private TableView<Books> bookInfo_table;

    @FXML
    private TableColumn<Books, String> id_col;

    @FXML
    private TableColumn<Books, String> title_col;

    @FXML
    private TableColumn<Books, String> author_col;

    @FXML
    private TableColumn<Books, String> edition_col;

    @FXML
    private TableColumn<Books, String> subject_col;

    DatesDB datesDB = new DatesDB();
    ConnectionDB connectionDB = new ConnectionDB();
    ObservableList<Books> list = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException {
        loadData();
        showBooks(list);
    }

    @FXML
    void backHandler(ActionEvent event) {
        openNewScene("/fxml/mainAdmin.fxml", backButton);
    }

    @FXML
    void deleteHandler(ActionEvent event) throws SQLException {
        Books books = bookInfo_table.getSelectionModel().getSelectedItem();
        String id = books.getId();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm delete operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to delete this Book?");

        Optional<ButtonType> response = alert.showAndWait();
        if(response.get()==ButtonType.OK) {
            bookInfo_table.getItems().removeAll(bookInfo_table.getSelectionModel().getSelectedItem());
            String query = "DELETE FROM book_table WHERE idbook_table = '" + id + "'";
            PreparedStatement ps = connectionDB.getDbConnection().prepareStatement(query);
            ps.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "You successfully deleted the Book!");
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Canceled");
            alert1.setHeaderText(null);
            alert1.setContentText("Delete operation canceled");
            alert1.showAndWait();
        }
    }

    private void loadData() throws SQLException {
        String query = "SELECT * FROM book_table";
        PreparedStatement ps = connectionDB.getDbConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery(query);

        try {
            while (rs.next()) {
                String id = rs.getString(datesDB.BOOK_ID);
                String title = rs.getString(datesDB.BOOK_TITLE);
                String author = rs.getString(datesDB.BOOK_AUTHOR);
                int edition = rs.getInt(datesDB.BOOK_EDITION);
                String subject = rs.getString(datesDB.BOOK_SUBJECT);
                int amount = rs.getInt(datesDB.BOOK_AMOUNT);
                int price = rs.getInt(datesDB.BOOK_AMOUNT);

                list.add(new Books(id, title, author, edition, subject, amount, price));
            }
        } catch (Exception e) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    protected  void showBooks(ObservableList<Books> books) {
        bookInfo_table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        id_col.setCellValueFactory(new PropertyValueFactory<Books, String>("id"));
        title_col.setCellValueFactory(new PropertyValueFactory<Books, String>("title_col"));
        author_col.setCellValueFactory(new PropertyValueFactory<Books, String>("author_col"));
        edition_col.setCellValueFactory(new PropertyValueFactory<Books, String>("edition_col"));
        subject_col.setCellValueFactory(new PropertyValueFactory<Books, String>("subject_col"));

        bookInfo_table.setItems(books);
    }

}

