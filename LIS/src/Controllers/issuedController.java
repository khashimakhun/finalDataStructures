package Controllers;

import DatabaseHandler.ConnectionDB;
import DatabaseHandler.DatesDB;
import Model.Books;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class issuedController {

    @FXML
    private TableView<Books> issueDataList;

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

    private void loadData() throws SQLException {
        String query = "SELECT * FROM issued_books_table LEFT JOIN book_table ON issued_books_table.book_id = book_table.idbook_table";
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

    protected void showBooks(ObservableList<Books> books) {
        issueDataList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        id_col.setCellValueFactory(new PropertyValueFactory<Books, String>("id"));
        title_col.setCellValueFactory(new PropertyValueFactory<Books, String>("title_col"));
        author_col.setCellValueFactory(new PropertyValueFactory<Books, String>("author_col"));
        edition_col.setCellValueFactory(new PropertyValueFactory<Books, String>("edition_col"));
        subject_col.setCellValueFactory(new PropertyValueFactory<Books, String>("subject_col"));

        issueDataList.setItems(books);
    }
}

