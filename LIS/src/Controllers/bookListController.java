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

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class bookListController extends SceneHandler {

    @FXML
    private TableView<Books> table_view;

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

    @FXML
    private TableColumn<Books, String> amount_col;

    @FXML
    private TextField bookTitle_txt;

    @FXML
    private TextField bookAuthor_txt;

    @FXML
    private Button backButton;

    DatesDB datesDB = new DatesDB();
    ConnectionDB connectionDB = new ConnectionDB();
    ObservableList<Books> list = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException {
        loadData();
        showBooks(list);
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
                int price = rs.getInt(datesDB.BOOK_PRICE);

                list.add(new Books(id, title, author, edition, subject, amount, price));
            }
        } catch (Exception e) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    protected  void showBooks(ObservableList<Books> books) {
        table_view.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        id_col.setCellValueFactory(new PropertyValueFactory<Books, String>("id"));
        title_col.setCellValueFactory(new PropertyValueFactory<Books, String>("title_col"));
        author_col.setCellValueFactory(new PropertyValueFactory<Books, String>("author_col"));
        edition_col.setCellValueFactory(new PropertyValueFactory<Books, String>("edition_col"));
        subject_col.setCellValueFactory(new PropertyValueFactory<Books, String>("subject_col"));
        amount_col.setCellValueFactory(new PropertyValueFactory<Books, String>("amount_col"));

        table_view.setItems(books);
    }

    @FXML
    void backHandler(ActionEvent event) {
        openNewScene("/fxml/mainAdmin.fxml", backButton);
    }

    @FXML
    void editAction(ActionEvent event) throws SQLException {
        Books books = table_view.getSelectionModel().getSelectedItem();
        String id = books.getId();
        String title = bookTitle_txt.getText();
        String author = bookAuthor_txt.getText();

        if(title.equals("") & author.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter title or author field");
        } else if(author.equals("")) {
            String query = "UPDATE book_table SET book_title = '" + title + "' WHERE idbook_table = '" + id + "'";
            PreparedStatement ps = connectionDB.getDbConnection().prepareStatement(query);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Book edit complete");
        } else if(title.equals("")) {
            String query = "UPDATE book_table SET book_author = '" + author + "' WHERE idbook_table = '" + id + "'";
            PreparedStatement ps = connectionDB.getDbConnection().prepareStatement(query);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Book edit complete");
        } else {
            String query = "UPDATE book_table SET book_author = '" + author + "', book_title = '" + title +"' WHERE idbook_table = '" + id + "'";
            PreparedStatement ps = connectionDB.getDbConnection().prepareStatement(query);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Book edit complete");
        }

        bookTitle_txt.setText("");
        bookAuthor_txt.setText("");
    }
}

