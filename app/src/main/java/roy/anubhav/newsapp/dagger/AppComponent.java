package roy.anubhav.newsapp.dagger;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import roy.anubhav.newsapp.dagger.modules.AppModule;
import roy.anubhav.newsapp.viewmodel.MainActivityViewModel;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {


    void inject(@NotNull MainActivityViewModel viewModel);
}
