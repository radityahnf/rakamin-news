package com.example.news

import Article
import News
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var ivTerkini: ImageView
    private lateinit var tvHeadlineTerkini: TextView
    private lateinit var tvAuthorTerkini: TextView
    private lateinit var tvDateTerkini: TextView
    private lateinit var hotNews: Article
    private var newsData: MutableList<Article> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countryCode = "us"
        val apiKey = "db63d7b3c1d54a3fb684151e99342840"

        val retrofitClient = RetrofitClient.instance
        retrofitClient.getNews(countryCode, apiKey).enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    Log.e("Hello", "Hello")
                    newsData = response.body()?.articles ?: mutableListOf()
                    val newestArticle: Article? = if (newsData.isNotEmpty()) getNewestArticle() else null

                    setViewHolder()
                    newestArticle?.let { bindViewHolder(it) }

                    newsAdapter = NewsAdapter(newsData)

                    recyclerView = findViewById(R.id.recyclerView)
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerView.adapter = newsAdapter
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                t.message
            }
        })
    }

    private fun setViewHolder() {
        ivTerkini = findViewById(R.id.ivTerkini)
        tvHeadlineTerkini = findViewById(R.id.tvHeadlineTerkini)
        tvAuthorTerkini = findViewById(R.id.tvAuthorTerkini)
        tvDateTerkini = findViewById(R.id.tvDateTerkini)
    }

    private fun bindViewHolder(article: Article) {
        Picasso.get().load(article.urlToImage).into(ivTerkini)

        tvHeadlineTerkini.text = article.title
        tvAuthorTerkini.text = article.author?: "Unknown"
        tvDateTerkini.text = article.publishedAt?.let { Util.formatDate(it) } ?: "Unknown"

    }

    private fun getNewestArticle(): Article {
        hotNews = newsData.removeAt(0)
        return hotNews
    }
}
