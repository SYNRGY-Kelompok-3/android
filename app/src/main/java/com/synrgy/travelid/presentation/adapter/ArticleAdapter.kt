package com.synrgy.travelid.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.febi.myapplication.ArticleViewHolder
import com.synrgy.travelid.databinding.ArticleRvSemuaBinding
import com.synrgy.travelid.domain.model.main.Article

class ArticleAdapter (

) : ListAdapter<Article, ArticleViewHolder>(ArticleDiffUtilCallback()){

    private var articles: List<Article>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ArticleRvSemuaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    fun setArticles(filteredArticles: List<Article>) {
        articles = filteredArticles
        notifyDataSetChanged()
    }
}