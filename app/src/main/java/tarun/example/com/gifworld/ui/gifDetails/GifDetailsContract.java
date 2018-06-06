package tarun.example.com.gifworld.ui.gifDetails;

import tarun.example.com.gifworld.data.model.AdapterGifItem;
import tarun.example.com.gifworld.data.model.FullGif;
import tarun.example.com.gifworld.ui.BasePresenter;

public class GifDetailsContract {

    interface View {

//        void showError(Error errorCode);
//
//        void hideErrors();

        void loadGif(String url);

        void populateGifDetails();

        void showInvalidRatingErrorMessage();

    }

    interface Presenter extends BasePresenter<GifDetailsContract.View> {

        void onGifRated(AdapterGifItem gif, int rating);
//
//        void searchButtonClicked();

    }

}
