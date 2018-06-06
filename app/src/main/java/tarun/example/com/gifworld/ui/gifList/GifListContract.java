package tarun.example.com.gifworld.ui.gifList;

import android.support.v4.app.FragmentActivity;

import java.util.List;

import tarun.example.com.gifworld.data.model.AdapterGifItem;
import tarun.example.com.gifworld.data.model.Gif;
import tarun.example.com.gifworld.ui.BasePresenter;

public interface GifListContract {

    interface View {

//        void showError(Error errorCode);
//
//        void hideErrors();

        FragmentActivity getFragmentActivity();

        boolean isViewVisible();

        void showProgress();

        void hideProgress();

        void updateGifsListAdapterData(List<AdapterGifItem> gifs);

        void setSortingDropDownVisibility(boolean visibility);

//        void collapseSearchView();

        void setActivityTitle(String title);

    }

    interface Presenter extends BasePresenter<View> {

        void searchQueryChanged(String query);

        void sortByOptionUpdated(int position);

//        void searchButtonClicked();

    }

}
