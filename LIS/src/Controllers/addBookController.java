package Controllers;

import DatabaseHandler.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addBookController extends SceneHandler {

    @FXML
    private TextField title_txt;

    @FXML
    private TextField author_txt;

    @FXML
    private TextField edition_txt;

    @FXML
    private TextField subject_txt;

    @FXML
    private TextField price_txt;

    @FXML
    private Button save_btn;

    @FXML
    private Button cancel_btn;

    @FXML
    private TextField amount_txt;

    @FXML
    void add_book(ActionEvent event) {
        ConnectionDB connectionDB = new ConnectionDB();
        String title = title_txt.getText();
        String author = author_txt.getText();
        String subject = subject_txt.getText();
        int price = validateField(edition_txt);
        int amount = validateField(amount_txt);
        int edition = validateField(price_txt);

        if(title.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter a title");
        } else if(author.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter an author");
        } else if(subject.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter a subject");
        } else {
            try {
                String query = "INSERT INTO book_table (book_title, book_author, book_edition, book_subject, book_amount, book_price) " +
                        "VALUES (?,?,?,?,?,?)";
                PreparedStatement ps = connectionDB.getDbConnection().prepareStatement(query);

                ps.setString(1, title);
                ps.setString(2, author);
                ps.setInt(3, edition);
                ps.setString(4, subject);
                ps.setInt(5, amount);
                ps.setInt(6, price);

                if (ps.executeUpdate() > 0)
                    JOptionPane.showMessageDialog(null, "Book Successfully added");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    int validateField(TextField $field) {
        int str = 0;
        try {
            str = Integer.parseInt($field.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, $field.getPromptText() + " take only numbers!");
        }
        return str;
    }

    @FXML
    void cancil_adding(ActionEvent event) { openNewScene("/fxml/mainAdmin.fxml", cancel_btn); }

}
