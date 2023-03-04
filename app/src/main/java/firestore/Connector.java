package firestore;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Connector implements Database {
    private static Connector instance;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public static Connector getInstance() {
        if (instance == null) {
            instance = new Connector();
        }
        return instance;
    }
}
