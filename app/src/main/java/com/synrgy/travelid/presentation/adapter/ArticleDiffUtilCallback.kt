package com.synrgy.travelid.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.travelid.domain.model.main.Article

class ArticleDiffUtilCallback : DiffUtil.ItemCallback<Article>(){
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Article,
        newItem: Article
    ): Boolean {
        return oldItem.id == newItem.id
    }
}