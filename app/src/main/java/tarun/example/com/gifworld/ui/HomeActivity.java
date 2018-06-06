package tarun.example.com.gifworld.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import tarun.example.com.gifworld.R;
import tarun.example.com.gifworld.data.model.AdapterGifItem;
import tarun.example.com.gifworld.ui.gifDetails.GifDetailsFragment;
import tarun.example.com.gifworld.ui.gifList.GifListFragment;

public class HomeActivity extends AppCompatActivity implements GifListFragment.OnGifListClickedListener {

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        manager = getSupportFragmentManager();
        Fragment gifListFragment = manager.findFragmentById(R.id.fragment_container);

        if (gifListFragment == null) {
            gifListFragment = GifListFragment.newInstance();
            manager.beginTransaction()
                    .add(R.id.fragment_container, gifListFragment, GifListFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void onGifClicked(AdapterGifItem gif) {
        Fragment gifDetailsFragment = GifDetailsFragment.newInstance(gif);
        manager.beginTransaction()
                .replace(R.id.fragment_container, gifDetailsFragment, GifDetailsFragment.TAG)
                .addToBackStack(GifDetailsFragment.TAG)
                .commit();
    }
}
