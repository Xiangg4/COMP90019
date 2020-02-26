package firebase.models;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

@IgnoreExtraProperties
public class MyFirebaseUser {
    public String username;
    public String email;
    public Map<String, ParkingHistoryInfo> history;


    public MyFirebaseUser() {
    }

    public MyFirebaseUser(String username, String email,Map<String, ParkingHistoryInfo> history) {
        this.username = username;
        this.email = email;
        this.history = history;
    }
}
