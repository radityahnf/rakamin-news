package com.example.news

import Article
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class NewsAdapter (private val newsData : List<Article>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivNews : ImageView = view.findViewById(R.id.ivNews)
        var tvHeadline: TextView = view.findViewById(R.id.tvHeadline)
        var tvAuthor : TextView = view.findViewById(R.id.tvAuthor)
        var tvPublishedAt: TextView = view.findViewById(R.id.tvPublishedAt)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        Picasso.get().load(newsData[position].urlToImage).into(holder.ivNews)
        holder.tvHeadline.text = newsData[position].title
        holder.tvAuthor.text = newsData[position].author
        holder.tvPublishedAt.text = Util.formatDate(newsData[position].publishedAt)

    }

    override fun getItemCount(): Int {
        return newsData.size
    }


}

