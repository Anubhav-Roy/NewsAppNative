package roy.anubhav.newsapp.utils;

public interface Callback<T> {

    void onSuccess(T result);

    void onFailure(String message);
}
