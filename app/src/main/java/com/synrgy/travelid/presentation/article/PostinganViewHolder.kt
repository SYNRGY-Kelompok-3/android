package com.synrgy.travelid.presentation.article

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.synrgy.travelid.databinding.ArticleRvPostinganTerbaruBinding
import com.synrgy.travelid.domain.model.main.Article

class PostinganViewHolder(
    private val binding: ArticleRvPostinganTerbaruBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(post: Article){
        binding.imgAtcRvPost.load(post.imageUrl)
        binding.tvAtcPostTitle.text = post.title
        binding.tvAtcPostDesc.text = post.content
        binding.tvAtcPostCategory.text = post.category
//        binding.tvAtcPostReadtime.text = post.readTime
    }
}