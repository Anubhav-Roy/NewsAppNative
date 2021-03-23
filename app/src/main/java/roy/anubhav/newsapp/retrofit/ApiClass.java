package roy.anubhav.newsapp.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import roy.anubhav.newsapp.retrofit.models.NewsResponse;

public interface ApiClass {

    @GET("top-headlines")
    Call<NewsResponse> fetchNews(@Query("country") String country,
                                         @Query("apiKey") String apiKey,
                                         @Query("page") int page,
                                         @Query("pageSize") int pageSize);

}
