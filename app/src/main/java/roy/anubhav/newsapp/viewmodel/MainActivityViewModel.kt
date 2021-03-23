package roy.anubhav.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import roy.anubhav.newsapp.BuildConfig
import roy.anubhav.newsapp.pagination.NewsDataSourceFactory
import roy.anubhav.newsapp.retrofit.models.NewsModel
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {

    @Inject
    lateinit var newsDataSourceFactory: NewsDataSourceFactory

    lateinit var dataList: LiveData<PagedList<NewsModel>>

    fun setup() {
        dataList = LivePagedListBuilder( newsDataSourceFactory,
            BuildConfig.PAGE_SIZE).build()
    }



}