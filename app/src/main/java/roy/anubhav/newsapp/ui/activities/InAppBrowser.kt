package roy.anubhav.newsapp.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import roy.anubhav.newsapp.R

class InAppBrowser : AppCompatActivity() {

    private lateinit var wv:WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_app_browser)
        wv = findViewById(R.id.webview)

        wv.settings.javaScriptEnabled = true
        wv.settings.domStorageEnabled = true
        intent.extras?.let { wv.loadUrl(it.getString("url","")) }
        wv.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let { wv.loadUrl(it) };
                return true;
            }
        }

    }

}