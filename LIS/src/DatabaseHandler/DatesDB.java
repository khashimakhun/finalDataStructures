package DatabaseHandler;

public class DatesDB {
    // Dates for Book Table
    public static final String BOOK_ID = "idbook_table";
    public static final String BOOK_TITLE = "book_title";
    public static final String BOOK_AUTHOR = "book_author";
    public static final String BOOK_EDITION = "book_edition";
    public static final String BOOK_SUBJECT = "book_subject";
    public static final String BOOK_AMOUNT = "book_amount";
    public static final String BOOK_PRICE = "book_price";

    // Dates for User Table
    public static final String USER_ID = "idusers_table";
    public static final String USER_FN = "first_name";
    public static final String USER_LN = "last_name";
    public static final String USERNAME = "user_name";
    public static final String USER_ADDRESS = "address";

    // Dates for Issued Table
    public static final String ISSUE_ID = "id";
    public static final String BOOK_ID_ISSUE = "book_id";
    public static final String USER_ID_ISSUE = "user_id";
    public static final String ISSUE_TIME = "issue_time";
}
