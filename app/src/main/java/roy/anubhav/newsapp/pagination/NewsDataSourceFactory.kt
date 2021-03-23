package roy.anubhav.newsapp.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import roy.anubhav.newsapp.retrofit.RetrofitClient
import roy.anubhav.newsapp.retrofit.models.NewsModel

class NewsDataSourceFactory(val retrofitClient: RetrofitClient): DataSource.Factory<Int, NewsModel>() {


    val sourceLiveData = MutableLiveData<NewsDataSource>()

    var latestSource: NewsDataSource? = null

    override fun create(): DataSource<Int,NewsModel> {
        latestSource = NewsDataSource(retrofitClient)
        sourceLiveData.postValue(latestSource)
        return latestSource as NewsDataSource
    }


}
