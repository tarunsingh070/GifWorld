package tarun.example.com.gifworld.ui;

public interface BasePresenter<V> {

    void takeView(V v);

    void dropView();

}
