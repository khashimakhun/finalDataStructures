package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Books {
    private final SimpleStringProperty id;
    private final SimpleStringProperty title_col;
    private final SimpleStringProperty author_col;
    private final SimpleIntegerProperty edition_col;
    private final SimpleStringProperty subject_col;
    private final SimpleIntegerProperty amount_col;
    private final SimpleIntegerProperty price_col;

    public Books(String id, String title_col, String author_col, int edition_col, String subject_col, int amount_col, int price_col) {
        this.id = new SimpleStringProperty(id);
        this.title_col = new SimpleStringProperty(title_col);
        this.author_col = new SimpleStringProperty(author_col);
        this.edition_col = new SimpleIntegerProperty(edition_col);
        this.subject_col = new SimpleStringProperty(subject_col);
        this.amount_col = new SimpleIntegerProperty(amount_col);
        this.price_col = new SimpleIntegerProperty(price_col);
    }

    public String getId() {
        return id.get();
    }

    public String getTitle_col() {
        return title_col.get();
    }

    public String getAuthor_col() {
        return author_col.get();
    }

    public String getSubject_col() {
        return subject_col.get();
    }

    public int getEdition_col() {
        return edition_col.get();
    }

    public int getAmount_col() {
        return amount_col.get();
    }

    public int getPrice_col() {
        return price_col.get();
    }

}
