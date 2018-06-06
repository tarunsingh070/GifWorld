package tarun.example.com.gifworld.data;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ValueEventListener;

import okhttp3.Callback;
import tarun.example.com.gifworld.data.model.firebase.FirebaseGif;

public interface DataManager {

    void getTrendingGifs(Callback callback);

    void getSearchedGifs(String searchTerm, Callback callback);

    Task<Void> addOrUpdateGif(FirebaseGif firebaseGif);

    void getRankedGifsFromFirebase(ValueEventListener listener);

}
