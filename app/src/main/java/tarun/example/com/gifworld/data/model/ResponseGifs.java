package tarun.example.com.gifworld.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGifs {

    @SerializedName("data")
    List<Gif> gifs;

    Pagination pagination;

    public List<Gif> getGifs() {
        return gifs;
    }

    public void setGifs(List<Gif> gifs) {
        this.gifs = gifs;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
