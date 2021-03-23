package roy.anubhav.newsapp.ui.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import roy.anubhav.newsapp.R
import roy.anubhav.newsapp.retrofit.models.NewsModel
import roy.anubhav.newsapp.ui.activities.InAppBrowser

class NewsAdapter :
    PagedListAdapter<NewsModel, NewsAdapter.NewsViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news: NewsModel? = getItem(position)

        // Note that "concert" is a placeholder if it's null.
        news?.let { holder.bindTo(it) }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsModel>() {
            // The ID property identifies when items are the same.
            override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel) =
                oldItem.title == newItem.title

            // If you use the "==" operator, make sure that the object implements
            // .equals(). Alternatively, write custom data comparison logic here.
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: NewsModel, newItem: NewsModel) = oldItem == newItem
        }
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title:TextView = itemView.findViewById(R.id.title)
        private val description:TextView = itemView.findViewById(R.id.description)
        private val author:TextView = itemView.findViewById(R.id.author)

        fun bindTo(news:NewsModel){
            title.text = news.title
            description.text = news.description
            author.text = news.author

            itemView.setOnClickListener {
                val intent = Intent(itemView.context,InAppBrowser::class.java)
                intent.putExtra("url",news.url)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view =   LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item_layout, parent, false)

        return NewsViewHolder(view)
    }
}