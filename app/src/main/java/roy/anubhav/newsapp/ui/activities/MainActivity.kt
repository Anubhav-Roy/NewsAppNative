package roy.anubhav.newsapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import roy.anubhav.newsapp.R
import roy.anubhav.newsapp.dagger.ApplicationClass
import roy.anubhav.newsapp.ui.adapters.NewsAdapter
import roy.anubhav.newsapp.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel =
            ViewModelProvider(this).get(MainActivityViewModel::class.java)

        (application as ApplicationClass).appComponent.inject(viewModel)



        val rvAdapter= NewsAdapter()
        val linearLayoutManager =  LinearLayoutManager(this);

        findViewById<RecyclerView>(R.id.newsRV).layoutManager =  linearLayoutManager;
        findViewById<RecyclerView>(R.id.newsRV).adapter = rvAdapter;

        viewModel.setup()
        viewModel.dataList.observe(this, Observer {
            rvAdapter.submitList(it)
        })
    }
}