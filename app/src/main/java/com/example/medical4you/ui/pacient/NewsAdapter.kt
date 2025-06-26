package com.example.medical4you.ui.pacient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medical4you.R
import com.example.medical4you.data.model.NewsArticle

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var articles = listOf<NewsArticle>()

    fun submitList(newList: List<NewsArticle>) {
        articles = newList
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.tvNewsTitle)
        val body = itemView.findViewById<TextView>(R.id.tvNewsBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news_article, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        holder.body.text = article.body

        holder.itemView.setOnClickListener {
            it.isSelected = !it.isSelected  // pt news color selector
        }
    }

    override fun getItemCount(): Int = articles.size
}
