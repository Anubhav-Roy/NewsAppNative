package roy.anubhav.newsapp.dagger.modules;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import roy.anubhav.newsapp.BuildConfig;
import roy.anubhav.newsapp.pagination.NewsDataSourceFactory;
import roy.anubhav.newsapp.retrofit.RetrofitClient;
import roy.anubhav.newsapp.utils.Logger;
import roy.anubhav.newsapp.utils.PermissionsHelper;
import roy.anubhav.newsapp.utils.Threader;

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application){
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return application;
    }

    //Cache limit for HTTP
    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        int cacheSize = 20 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    //Gson for converting java objects into JSON
    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    //Creating OKHTTP client for creating http request
    //Additional headers can be added here
    @Provides
    @Singleton
    OkHttpClient provideHttpClient(Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .build();
                return chain.proceed(request);
            }
        });
        client.cache(cache);
        return client.build();
    }

    //Initialising retrofit interface and returning it's instance
    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(okHttpClient)
                    .build();
    }

    //Initialising retrofit client and returning it's instance
    @Provides
    @Singleton
    RetrofitClient provideRetrofitClient(Retrofit retrofit) {

        return new RetrofitClient(retrofit);
    }

    @Provides
    @Singleton
    Logger provideLogger() {
        return new Logger(Logger.LEVEL.DEBUG);
    }

    @Provides
    @Singleton
    PermissionsHelper providePermissionHelper() {
        return new PermissionsHelper();
    }

    @Provides
    @Singleton
    Threader provideThreader() {
        return new Threader();
    }

    @Provides
    @Singleton
    NewsDataSourceFactory provideNewsDataSourceFactory(RetrofitClient retrofitClient) {
        return new NewsDataSourceFactory(retrofitClient);
    }
}
