package roy.anubhav.newsapp.dagger;

import android.app.Application;

import roy.anubhav.newsapp.dagger.AppComponent;
import roy.anubhav.newsapp.dagger.DaggerAppComponent;
import roy.anubhav.newsapp.dagger.modules.AppModule;

public class ApplicationClass extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
