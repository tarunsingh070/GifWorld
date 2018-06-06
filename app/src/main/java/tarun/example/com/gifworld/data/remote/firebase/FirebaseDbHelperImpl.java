package tarun.example.com.gifworld.data.remote.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import tarun.example.com.gifworld.data.Constants;
import tarun.example.com.gifworld.data.model.firebase.FirebaseGif;

public class FirebaseDbHelperImpl implements FirebaseDbHelper {

    @Override
    public Task<Void> addOrUpdateGif(FirebaseGif firebaseGif) {
        return FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.GIFS)
                .child(firebaseGif.getId())
                .setValue(firebaseGif);
    }

//    @Override
//    public Task<Void> addActivePersonalChats(String userId, Map<String, Object> newChatsId) {
//        return FirebaseDatabase.getInstance()
//                .getReference()
//                .child(Constants.USERS_CHILD)
//                .child(userId)
//                .child(Constants.USER_ACTIVE_PERSONAL_CHATS)
//                .updateChildren(newChatsId);
//    }
}