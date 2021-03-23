package roy.anubhav.newsapp.retrofit;


import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import roy.anubhav.newsapp.BuildConfig;
import roy.anubhav.newsapp.retrofit.models.NewsModel;
import roy.anubhav.newsapp.retrofit.models.NewsResponse;

public class RetrofitClient {

    private ApiClass apiInterface;

    public RetrofitClient(Retrofit retrofit){
        this.apiInterface = retrofit.create(ApiClass.class);
    }


    public List<NewsModel> fetchNews(int pageNumber){

        try {
            Response<NewsResponse> res = apiInterface.fetchNews("in",
                    BuildConfig.API_KEY,
                    pageNumber,
                    BuildConfig.PAGE_SIZE ).execute() ;
            return res.body().articles;
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Customary null
        return null;
    }
}
