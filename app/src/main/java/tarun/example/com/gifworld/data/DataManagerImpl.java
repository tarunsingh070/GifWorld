package tarun.example.com.gifworld.data;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import okhttp3.Callback;
import tarun.example.com.gifworld.data.model.firebase.FirebaseGif;
import tarun.example.com.gifworld.data.remote.firebase.FirebaseDbHelper;
import tarun.example.com.gifworld.data.remote.firebase.FirebaseDbHelperImpl;
import tarun.example.com.gifworld.data.remote.giphy.GiphyRestClient;

public class DataManagerImpl implements DataManager {

    private GiphyRestClient restClient;
    private FirebaseDbHelper firebaseDbHelper;

    public DataManagerImpl() {
        restClient = GiphyRestClient.getRestClient();
        firebaseDbHelper = new FirebaseDbHelperImpl();
    }

    @Override
    public Task<Void> addOrUpdateGif(FirebaseGif firebaseGif) {
        return firebaseDbHelper.addOrUpdateGif(firebaseGif);
    }

    @Override
    public void getRankedGifsFromFirebase(ValueEventListener listener) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Constants.GIFS);

        ref.addListenerForSingleValueEvent(listener);
    }

    @Override
    public void getTrendingGifs(Callback callback) {
        restClient.getTrendingGifs(callback);
    }

    @Override
    public void getSearchedGifs(String searchTerm, Callback callback) {
        restClient.getSearchedGifs(searchTerm, callback);
    }

}
