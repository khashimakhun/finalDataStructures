package Model;

public class Issue {
    private String id;
    private String bookID_col;
    private String userID_col;
    private String issueTime_col;

    public Issue(String id, String bookID_col, String userID_col, String issueTime_col) {
        this.id = id;
        this.bookID_col = bookID_col;
        this.userID_col = userID_col;
        this.issueTime_col = issueTime_col;
    }

    public String getId() {
        return id;
    }

    public String getBookID_col() {
        return bookID_col;
    }

    public String getUserID_col() {
        return userID_col;
    }

    public String getIssueTime_col() {
        return issueTime_col;
    }
}
