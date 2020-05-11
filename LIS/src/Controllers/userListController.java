package Controllers;

import DatabaseHandler.ConnectionDB;
import DatabaseHandler.DatesDB;
import Model.Books;
import Model.Users;
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

public class userListController {

    DatesDB datesDB = new DatesDB();
    ConnectionDB connectionDB = new ConnectionDB();
    ObservableList<Users> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Users> table_view;

    @FXML
    private TableColumn<Users, String> id_col;

    @FXML
    private TableColumn<Users, String> firstname_col;

    @FXML
    private TableColumn<Users, String> lastname_col;

    @FXML
    private TableColumn<Users, String> username_col;

    @FXML
    private TableColumn<Users, String> address_col;

    @FXML
    void initialize() throws SQLException {
        loadData();
        showUsers(list);
    }

    private void loadData() throws SQLException {
        String query = "SELECT * FROM users_table";
        PreparedStatement ps = connectionDB.getDbConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery(query);

        try {
            while (rs.next()) {
                String id = rs.getString(datesDB.USER_ID);
                String firstName = rs.getString(datesDB.USER_FN);
                String lastName = rs.getString(datesDB.USER_LN);
                String username = rs.getString(datesDB.USERNAME);
                String address = rs.getString(datesDB.USER_ADDRESS);

                list.add(new Users(id, firstName, lastName, username, address));
            }
        } catch (Exception e) {
            Logger.getLogger(mainController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    protected  void showUsers(ObservableList<Users> users) {
        table_view.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        id_col.setCellValueFactory(new PropertyValueFactory<Users, String>("id"));
        firstname_col.setCellValueFactory(new PropertyValueFactory<Users, String>("firstName"));
        lastname_col.setCellValueFactory(new PropertyValueFactory<Users, String>("lastName"));
        username_col.setCellValueFactory(new PropertyValueFactory<Users, String>("username"));
        address_col.setCellValueFactory(new PropertyValueFactory<Users, String>("address"));

        table_view.setItems(users);
    }
}

