package tarun.example.com.gifworld.ui.gifList;

import android.content.Context;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import tarun.example.com.gifworld.R;
import tarun.example.com.gifworld.data.model.AdapterGifItem;

public class GifsListAdapter extends RecyclerView.Adapter<GifsListAdapter.ViewHolder> {

    private List<AdapterGifItem> gifs;
    private Context context;
    private ItemClickListener itemClickListener;

    // data is passed into the constructor
    GifsListAdapter(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        gifs = new ArrayList<>();
        this.itemClickListener = itemClickListener;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gif, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(gifs.get(position));
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return gifs.size();
    }

    public void setGifs(List<AdapterGifItem> gifs) {
        this.gifs = gifs;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivGif;

        ViewHolder(View itemView) {
            super(itemView);
            ivGif = itemView.findViewById(R.id.iv_gif);
        }

        void bind(AdapterGifItem gif) {
            loadGif(gif.getPreviewUrl());
            itemView.setOnClickListener(this);
        }

        void loadGif(String url) {
            if (!TextUtils.isEmpty(url)) {
                getProgressPlaceHolderView();
                Glide.with(context)
                        .asGif()
                        .load(url)
                        .apply(new RequestOptions()
                                .placeholder(getProgressPlaceHolderView()))
                        .into(ivGif);
            }
        }

        private CircularProgressDrawable getProgressPlaceHolderView() {
            CircularProgressDrawable progressPlaceHolder = new CircularProgressDrawable(context);
            progressPlaceHolder.setStrokeWidth(5f);
            progressPlaceHolder.setCenterRadius(30f);
            progressPlaceHolder.start();
            return progressPlaceHolder;
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(gifs.get(getAdapterPosition()));
            }
        }
    }

//    // convenience method for getting data at click position
//    String getItem(int id) {
//        return mData[id];
//    }

//    // allows clicks events to be caught
//    void setClickListener(ItemClickListener itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(AdapterGifItem gif);
    }
}
