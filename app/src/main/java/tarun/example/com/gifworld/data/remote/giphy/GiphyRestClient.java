package tarun.example.com.gifworld.data.remote.giphy;

import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GiphyRestClient {

    private static final String TAG = GiphyRestClient.class.getSimpleName();

    private static final String BASE_URL = "api.giphy.com";
    private static final String URL_TRENDING_GIFS = "v1/gifs/trending";
    private static final String URL_SEARCH_GIFS = "v1/gifs/search";
    private static final String GIPHY_API_KEY = "tKSHo2xJBooBR7H2o7AYdl4hu6YRF7Wf";
    private static final String RECORDS_PAGE_SIZE = "30";

    private OkHttpClient client = new OkHttpClient();

    public static GiphyRestClient getRestClient() {
        return new GiphyRestClient();
    }

    private HttpUrl.Builder getBaseUrlBuilder() {
        return new HttpUrl.Builder()
                .scheme("https")
                .host(BASE_URL)
                .addEncodedQueryParameter("api_key", GIPHY_API_KEY);
    }

    public void getTrendingGifs(Callback callback) {
        HttpUrl httpUrl = getBaseUrlBuilder()
                .addPathSegments(URL_TRENDING_GIFS)
                .addQueryParameter("limit", RECORDS_PAGE_SIZE)
                .build();

        makeGetRequest(httpUrl, callback);
    }

    public void getSearchedGifs(String searchTerm, Callback callback) {
        HttpUrl httpUrl = getBaseUrlBuilder()
                .addPathSegments(URL_SEARCH_GIFS)
                .addQueryParameter("q", searchTerm)
                .addQueryParameter("limit", RECORDS_PAGE_SIZE)
                .build();

        makeGetRequest(httpUrl, callback);
    }

    private void makeGetRequest(HttpUrl httpUrl, Callback callback) {
        Request request = new Request.Builder().url(httpUrl).get().build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

}
