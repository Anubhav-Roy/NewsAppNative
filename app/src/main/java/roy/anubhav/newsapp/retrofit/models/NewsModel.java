package roy.anubhav.newsapp.retrofit.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NewsModel {

    public int id;

    @SerializedName("author")
    @Expose
    public String author;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("url")
    @Expose
    public String url;
}
