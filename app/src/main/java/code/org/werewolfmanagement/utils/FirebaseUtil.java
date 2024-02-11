package code.org.werewolfmanagement.utils;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.UUID;

public class FirebaseUtil {


    public static CollectionReference getRoomReference(){
        return FirebaseFirestore.getInstance().collection("rooms");
    }

    public static DocumentReference getRoomWithIdReference(String roomId) {
        return FirebaseFirestore.getInstance().collection("rooms").document(roomId);
    }

    public static CollectionReference getPlayerReference(String roomId){
        return getRoomWithIdReference(roomId).collection("players");
    }
}
