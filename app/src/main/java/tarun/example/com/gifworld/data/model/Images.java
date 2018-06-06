package tarun.example.com.gifworld.data.model;

import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("fixed_width_downsampled")
    private PreviewGif previewGif;

    @SerializedName("fixed_width")
    private FullGif fullGif;


    public PreviewGif getPreviewGif() {
        return previewGif;
    }

    public void setPreviewGif(PreviewGif previewGif) {
        this.previewGif = previewGif;
    }

    public FullGif getFullGif() {
        return fullGif;
    }

    public void setFullGif(FullGif fullGif) {
        this.fullGif = fullGif;
    }

}
