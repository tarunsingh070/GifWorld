package tarun.example.com.gifworld.ui.gifList;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import tarun.example.com.gifworld.data.Constants;
import tarun.example.com.gifworld.data.DataManager;
import tarun.example.com.gifworld.data.DataManagerImpl;
import tarun.example.com.gifworld.data.model.AdapterGifItem;
import tarun.example.com.gifworld.data.model.Gif;
import tarun.example.com.gifworld.data.model.ResponseGifs;
import tarun.example.com.gifworld.data.model.firebase.FirebaseGif;

public class GifListPresenter implements GifListContract.Presenter {

    private int sortBySelectedOptionPosition;
    private GifListContract.View view;
    private DataManager dataManager;
    private GsonBuilder gsonBuilder;
    private ResponseGifs responseGifs;

    private List<AdapterGifItem> unRankedGifItems;
    private List<AdapterGifItem> rankedGifItems;
    private List<AdapterGifItem> finalizedGifItems;

    GifListPresenter() {
        dataManager = new DataManagerImpl();
        gsonBuilder = new GsonBuilder();
        unRankedGifItems = new ArrayList<>();
        rankedGifItems = new ArrayList<>();
        finalizedGifItems = new ArrayList<>();
    }

    @Override
    public void takeView(GifListContract.View view) {
        this.view = view;
        fetchTrendingGifs();
        fetchRankedGifsFromFirebase();
    }

    @Override
    public void dropView() {
        this.view = null;
    }

    private void fetchRankedGifsFromFirebase() {
        dataManager.getRankedGifsFromFirebase(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!view.isViewVisible()) {
                    return;
                }

                List<FirebaseGif> rankedFirebaseGifs = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    FirebaseGif firebaseGif = snapshot.getValue(FirebaseGif.class);
                    rankedFirebaseGifs.add(firebaseGif);
                }

                rankedGifItems.clear();
                for (FirebaseGif firebaseGif: rankedFirebaseGifs) {
                    AdapterGifItem adapterGifItem = new AdapterGifItem(firebaseGif.getId(), firebaseGif.getPreviewUrl()
                            , firebaseGif.getAverageRating(), firebaseGif.getRatingCount());
                    rankedGifItems.add(adapterGifItem);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Todo: Handle
            }
        });
    }

    private void fetchTrendingGifs() {
        dataManager.getTrendingGifs(getTrendingGifsCallback());
        Log.d("ACTIVITY TITLE TRENDING", "FETCH TRENDING GIFS");
        view.setActivityTitle(Constants.ACTIVITY_TITLE_TRENDING);
    }

    private Callback getTrendingGifsCallback() {
        view.showProgress();
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (!view.isViewVisible()) {
                    return;
                }

                view.hideProgress();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!view.isViewVisible()) {
                    return;
                }

                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {

                    String responseJson = response.body().string();

                    if (!TextUtils.isEmpty(responseJson)) {
                        responseGifs = gsonBuilder.create()
                                .fromJson(responseJson, ResponseGifs.class);

                        if (responseGifs != null) {
                            populateUnrankedGifItems();
//                            populateFinalizedGifItems();
                            // Because ranking doesn't apply to trending gifs.
                            finalizedGifItems.addAll(unRankedGifItems);
                            updateGifsRecyclerView();
                        }
                    }
                }
            }
        };
    }

    private void populateUnrankedGifItems() {
        unRankedGifItems.clear();
        for (Gif gif: responseGifs.getGifs()) {
            AdapterGifItem gifItem = new AdapterGifItem(gif.getId(), gif.getUserName(), gif.getImportDate()
                    ,gif.getTitle(), gif.getImages().getPreviewGif().getUrl(), gif.getImages().getFullGif());
            unRankedGifItems.add(gifItem);
        }
    }

    private Callback getSearchedGifsCallback() {
        view.showProgress();
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (!view.isViewVisible()) {
                    return;
                }

                view.hideProgress();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!view.isViewVisible()) {
                    return;
                }

                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {

                    String responseJson = response.body().string();

                    if (!TextUtils.isEmpty(responseJson)) {
                        responseGifs = gsonBuilder.create()
                                .fromJson(responseJson, ResponseGifs.class);

                        if (responseGifs != null) {
                            populateUnrankedGifItems();
                            populateFinalizedSearchedGifItems();
                            updateGifsRecyclerView();
                        }
                    }
                }
            }
        };
    }

    private void updateGifsRecyclerView() {
        view.getFragmentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Stuff that updates the UI on main thread because we cannot update UI from inside a background thread.
                view.updateGifsListAdapterData(finalizedGifItems);
            }
        });
    }

//    private void populateFinalizedGifItems() {
//        finalizedGifItems.clear();
//        if (sortBySelectedOptionPosition == GifListFragment.SPINNER_OPTION_RANKING_POSITION) {
//            finalizedGifItems.addAll(rankedGifItems);
//            finalizedGifItems.addAll(unRankedGifItems);
//        } else if (sortBySelectedOptionPosition == GifListFragment.SPINNER_OPTION_RELEVANCE_POSITION) {
//            finalizedGifItems.addAll(unRankedGifItems);
//        }
//    }

    private void populateFinalizedSearchedGifItems() {
        finalizedGifItems.clear();

        if (rankedGifItems != null && rankedGifItems.size() > 0) {
            LinkedHashMap<String, AdapterGifItem> unRankedGifItemsMap = new LinkedHashMap<>();
            for (AdapterGifItem adapterGifItem : unRankedGifItems) {
                unRankedGifItemsMap.put(adapterGifItem.getId(), adapterGifItem);
            }

            for (AdapterGifItem rankedGifItem : rankedGifItems) {
                if (unRankedGifItemsMap.containsKey(rankedGifItem.getId())) {
                    AdapterGifItem gifItem = unRankedGifItemsMap.get(rankedGifItem.getId());

                    gifItem.setRatingCount(rankedGifItem.getRatingCount());
                    gifItem.setAverageRating(rankedGifItem.getAverageRating());
                }
            }
            finalizedGifItems.addAll(unRankedGifItemsMap.values());
        } else {
            finalizedGifItems.addAll(unRankedGifItems);
        }

        if (sortBySelectedOptionPosition == GifListFragment.SPINNER_OPTION_RANKING_POSITION) {



            Collections.sort(finalizedGifItems);

//            // Add only those ranked items which are present in the search results as well.
//            for (AdapterGifItem rankedGifItem: rankedGifItems) {
//                if (unRankedGifItems.contains(rankedGifItem)) {
//                    finalizedGifItems.add(rankedGifItem);
//                }
//            }
//
//            // Exclude those items which were ranked and have already been added to the list.
//            for (AdapterGifItem unRankedGifItem: unRankedGifItems) {
//                if (!rankedGifItems.contains(unRankedGifItem)) {
//                    unRankedGifItems.
//                    finalizedGifItems.add(unRankedGifItem);
//                }
//            }
        } /*else if (sortBySelectedOptionPosition == GifListFragment.SPINNER_OPTION_RELEVANCE_POSITION) {
            finalizedGifItems.addAll(unRankedGifItems);
        }*/
    }

    @Override
    public void searchQueryChanged(String query) {
        if (view == null || !view.isViewVisible()) {
            return;
        }

        if (!TextUtils.isEmpty(query)) {
            dataManager.getSearchedGifs(query, getSearchedGifsCallback());
            view.setSortingDropDownVisibility(true);
            Log.d("ACTIVITY TITLE RESULTS", "SEARCH QUERY CHANGED");
            view.setActivityTitle(Constants.ACTIVITY_TITLE_RESULTS);
        } else {
            dataManager.getTrendingGifs(getTrendingGifsCallback());
            view.setSortingDropDownVisibility(false);
            Log.d("ACTIVITY TITLE TRENDING", "SEARCH QUERY CHANGED");
            view.setActivityTitle(Constants.ACTIVITY_TITLE_TRENDING);
        }
    }

    @Override
    public void sortByOptionUpdated(int position) {
        sortBySelectedOptionPosition = position;
        populateFinalizedSearchedGifItems();
        updateGifsRecyclerView();
    }

//    @Override
//    public void searchButtonClicked() {
//        view.collapseSearchView();
//    }
}