package tarun.example.com.gifworld.data.model;

import com.google.gson.annotations.SerializedName;

public class ResponseGif {

    @SerializedName("data")
    Gif gif;

    public Gif getGif() {
        return gif;
    }

    public void setGif(Gif gif) {
        this.gif = gif;
    }
}
