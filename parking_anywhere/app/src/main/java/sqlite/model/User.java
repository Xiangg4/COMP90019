package sqlite.model;

public class User {
    public static final String TABLE_NAME = "USERS";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USER = "USER_NAME";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String user;
    private String timestamp;






    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_USER + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public User() {
    }

    public User(int id, String user, String timestamp) {
        this.id = id;
        this.user = user;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

