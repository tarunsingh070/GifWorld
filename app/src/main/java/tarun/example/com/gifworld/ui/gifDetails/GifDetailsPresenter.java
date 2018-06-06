package tarun.example.com.gifworld.ui.gifDetails;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import tarun.example.com.gifworld.data.DataManager;
import tarun.example.com.gifworld.data.DataManagerImpl;
import tarun.example.com.gifworld.data.model.AdapterGifItem;
import tarun.example.com.gifworld.data.model.FullGif;
import tarun.example.com.gifworld.data.model.Gif;
import tarun.example.com.gifworld.data.model.ResponseGif;
import tarun.example.com.gifworld.data.model.firebase.FirebaseGif;

public class GifDetailsPresenter implements GifDetailsContract.Presenter {

    private GifDetailsContract.View view;
    private DataManager dataManager;
    private FullGif fullGif;

    public GifDetailsPresenter() {
        dataManager = new DataManagerImpl();
    }

    public GifDetailsPresenter(FullGif fullGif) {
        this();
        this.fullGif = fullGif;
    }

    @Override
    public void takeView(GifDetailsContract.View view) {
        this.view = view;
        loadData();
    }

    @Override
    public void dropView() {
        view = null;
    }

    private void loadData() {
        if (!TextUtils.isEmpty(fullGif.getUrl())) {
            view.loadGif(fullGif.getUrl());
        }

        view.populateGifDetails();
    }

    @Override
    public void onGifRated(AdapterGifItem gif, int rating) {
        // Store in firebase.
        if (rating > 0) {
            int newRatingCount = gif.getRatingCount() + 1;
            float newAverageRating = (gif.getAverageRating() + rating)/newRatingCount;
            dataManager.addOrUpdateGif(new FirebaseGif(gif.getId(), newAverageRating, newRatingCount, gif.getPreviewUrl()));
        } else {
            view.showInvalidRatingErrorMessage();
        }
    }
}
