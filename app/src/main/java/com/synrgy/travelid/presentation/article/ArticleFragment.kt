package com.synrgy.travelid.presentation.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.synrgy.travelid.databinding.FragmentArticleBinding
import com.synrgy.travelid.domain.model.main.Article
import com.synrgy.travelid.presentation.article.adapter.ArticleAdapter
import com.synrgy.travelid.presentation.article.adapter.SecondArticleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {
    private lateinit var binding: FragmentArticleBinding
    private val viewModel: ArticleViewModel by viewModels()
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var secondArticleAdapter: SecondArticleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData()
        bindAdapter()
        viewModel.fetchArticles()
        viewModel.fetchSecondArticles()
    }

    private fun observeLiveData() {
        viewModel.articles.observe(viewLifecycleOwner, ::handleDataArticle)
        viewModel.secondArticles.observe(viewLifecycleOwner, ::handleDataSecondArticle)
    }

    private fun bindAdapter() {
        articleAdapter = ArticleAdapter(object : ArticleAdapter.OnClickListener {
            override fun onClickItem(data: Article) {

            }
        })
        binding.rvAtcSemua.adapter = articleAdapter

        secondArticleAdapter = SecondArticleAdapter(object : SecondArticleAdapter.OnClickListener {
            override fun onClickItem(data: Article) {

            }
        })
        binding.rvAtcPostBaru.adapter = secondArticleAdapter
    }

    private fun handleDataArticle(article: List<Article>){
        articleAdapter.submitData(article)
    }

    private fun handleDataSecondArticle(article: List<Article>){
        secondArticleAdapter.submitData(article)
    }
}