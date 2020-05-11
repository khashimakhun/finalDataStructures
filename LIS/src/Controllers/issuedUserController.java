package Controllers;

import DatabaseHandler.ConnectionDB;
import DatabaseHandler.DatesDB;
import Model.Issue;
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

public class issuedUserController {

    @FXML
    private TableView<Issue> issueDataList;

    @FXML
    private TableColumn<Issue, String> id_col;

    @FXML
    private TableColumn<Issue, String> bookID_col;

    @FXML
    private TableColumn<Issue, String> userID_col;

    @FXML
    private TableColumn<Issue, String> timeIssue_col;

    DatesDB datesDB = new DatesDB();
    ConnectionDB connectionDB = new ConnectionDB();
    ObservableList<Issue> list = FXCollections.observableArrayList();

    @FXML
    void initialize() throws SQLException {
        loadData();
        showBooks(list);
    }

    private void loadData() throws SQLException {
        String query = "SELECT * FROM issued_books_table";
        PreparedStatement ps = connectionDB.getDbConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery(query);

        try {
            while (rs.next()) {
                String id = rs.getString(datesDB.ISSUE_ID);
                String bookID = rs.getString(datesDB.BOOK_ID_ISSUE);
                String userID = rs.getString(datesDB.USER_ID_ISSUE);
                String issueTime = rs.getString(datesDB.ISSUE_TIME);

                list.add(new Issue(id, bookID, userID, issueTime));
            }
        } catch (Exception e) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    protected  void showBooks(ObservableList<Issue> issue) {
        issueDataList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        id_col.setCellValueFactory(new PropertyValueFactory<Issue, String>(datesDB.ISSUE_ID));
        bookID_col.setCellValueFactory(new PropertyValueFactory<Issue, String>(datesDB.BOOK_ID_ISSUE));
        userID_col.setCellValueFactory(new PropertyValueFactory<Issue, String>(datesDB.USER_ID_ISSUE));
        timeIssue_col.setCellValueFactory(new PropertyValueFactory<Issue, String>(datesDB.ISSUE_TIME));

        issueDataList.setItems(issue);
    }

}
