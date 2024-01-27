package com.synrgy.travelid.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.travelid.databinding.ArticleRvPostinganTerbaruBinding
import com.synrgy.travelid.domain.model.main.Article
import com.synrgy.travelid.presentation.article.PostinganViewHolder

class PostinganAdapter : ListAdapter<Article, PostinganViewHolder>(ArticleDiffUtilCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostinganViewHolder {
        val binding = ArticleRvPostinganTerbaruBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostinganViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PostinganViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}