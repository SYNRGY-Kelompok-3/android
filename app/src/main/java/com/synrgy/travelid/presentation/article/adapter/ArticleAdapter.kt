package com.synrgy.travelid.presentation.article.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.synrgy.travelid.common.formatDateArticle
import com.synrgy.travelid.databinding.ArticleRvSemuaBinding
import com.synrgy.travelid.domain.model.main.Article
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class ArticleAdapter(private val onClick: OnClickListener): RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private val diffCallBack = object: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value: List<Article>?) {
        val sortedList = value?.sortedByDescending { it.createdAt }
        differ.submitList(sortedList?.toMutableList())
    }

    interface OnClickListener {
        fun onClickItem (data: Article)
    }

    inner class ViewHolder(private val binding: ArticleRvSemuaBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (data: Article){
            Glide.with(binding.root)
                .load(data.imageUrl)
                .into(binding.imgAtcRvSemua)
            binding.tvAtcTitle.text = data.title
            binding.tvAtcDescription.text = data.content
            binding.tvAtcCategory.text = data.category
            binding.tvAtcReadTime.text = formatDateArticle(data.createdAt)
            binding.root.setOnClickListener {
                onClick.onClickItem(data)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(ArticleRvSemuaBinding.inflate(inflate,parent,false))
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}