package roy.anubhav.newsapp.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {

    @SerializedName("articles")
    @Expose
    public List<NewsModel> articles;

}
