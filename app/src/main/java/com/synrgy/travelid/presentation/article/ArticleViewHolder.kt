package com.febi.myapplication

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.synrgy.travelid.helper.OnClickListener
import com.synrgy.travelid.databinding.ArticleRvSemuaBinding
import com.synrgy.travelid.domain.model.main.Article

class ArticleViewHolder (
    private val binding: ArticleRvSemuaBinding,
) : RecyclerView.ViewHolder(binding.root){

    private val onClick: OnClickListener? = null

        fun bind(article: Article){
            binding.imgAtcRvSemua.load(article.imageUrl)
            binding.tvAtcTitle.text = article.title
            binding.tvAtcDescription.text = article.content
            binding.tvAtcCategory.text = article.category
    //        binding.tvAtcReadTime.text = article.readTime

            binding.root.setOnClickListener{
                onClick?.onClickItem(article)
            }
    }
}