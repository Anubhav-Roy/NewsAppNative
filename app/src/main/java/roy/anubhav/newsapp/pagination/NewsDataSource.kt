package roy.anubhav.newsapp.pagination

import androidx.paging.PageKeyedDataSource
import roy.anubhav.newsapp.retrofit.RetrofitClient
import roy.anubhav.newsapp.retrofit.models.NewsModel

class NewsDataSource(private val retrofitClient: RetrofitClient) :
    PageKeyedDataSource<Int,NewsModel>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, NewsModel>
    ) {
        val news = retrofitClient.fetchNews(1)

        callback.onResult(news,1,2)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NewsModel>) {
        val items = retrofitClient.fetchNews(params.key)
        callback.onResult(items,params.key-1)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsModel>) {
        val items = retrofitClient.fetchNews(params.key)
        callback.onResult(items,params.key+1)
    }

}