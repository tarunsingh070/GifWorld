package tarun.example.com.gifworld.data.remote.firebase;

import com.google.android.gms.tasks.Task;

import tarun.example.com.gifworld.data.model.firebase.FirebaseGif;

public interface FirebaseDbHelper {

    Task<Void> addOrUpdateGif(FirebaseGif firebaseGif);

}
