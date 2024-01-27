package com.synrgy.presentation.article

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.febi.myapplication.ArticleViewModel
import com.synrgy.travelid.databinding.FragmentDetailArticleBinding
import com.synrgy.travelid.domain.model.main.Article
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailArticleFragment : Fragment() {

    private var binding: FragmentDetailArticleBinding? = null

    private val viewModel by viewModel<ArticleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailArticleBinding.inflate(
            inflater, container, false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showData()
    }
    private fun showData(){
        viewModel.fetchArticles()
        viewModel.articles.observe(viewLifecycleOwner, {articles -> setResult(articles)})
        viewModel.error.observe(viewLifecycleOwner, ::handleError)
    }

    private fun setResult(articles : List<Article>){
        binding?.tvDatcTitle?.text = articles.joinToString { it.title }
//        binding?.tvDatcDate?.text = articles.joinToString { it.updatedAt }
        binding?.tvDatcDesc?.text = articles.joinToString { it.content }
        binding?.imgDatcFirst?.let {
                Glide.with(this)
                    .load(articles[0].imageUrl)
                    .into(it)
            }
        binding?.imgDatcSecond?.let {
            Glide.with(this)
                .load(articles[0].imageUrl)
                .into(it)
        }
    }

    private fun handleError(error: String?) {
        if (error != null) {
            AlertDialog.Builder(context).setMessage(error).create().show()
//            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        } else {
            Log.e("ArticleFragment", "Error message is null")
        }
    }
}
