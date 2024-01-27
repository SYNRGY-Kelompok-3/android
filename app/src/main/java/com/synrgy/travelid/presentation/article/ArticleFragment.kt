//package com.synrgy.travelid.presentation.article
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.synrgy.travelid.R
//import com.synrgy.travelid.databinding.FragmentArticleBinding
//import com.synrgy.travelid.databinding.FragmentNotificationBinding
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class ArticleFragment : Fragment() {
//    private lateinit var binding: FragmentArticleBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        binding = FragmentArticleBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }
//}

package com.synrgy.travelid.presentation.article

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.travelid.presentation.adapter.ArticleAdapter
import com.febi.myapplication.ArticleViewModel
import com.synrgy.travelid.presentation.adapter.PostinganAdapter
import com.synrgy.travelid.R
import com.synrgy.travelid.databinding.FragmentArticleBinding
import com.synrgy.travelid.domain.model.main.Article
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleFragment : Fragment() {

    private var binding: FragmentArticleBinding? = null
    private var adapterArticle: ArticleAdapter? = null
    private var adapterPost: PostinganAdapter? = null
    private var navController: NavController? = null

    private val viewModel by viewModel<ArticleViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(
            inflater, container, false
        )
        return binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLiveData()
        viewModel.fetchArticles()
        setStatusBarTransparent()
        setBadgeMenu()

        binding?.bottomNav?.setOnItemSelectedListener { itemId ->
            navController?.let {
                navigateToDestination(itemId, it)
            }
            true
        }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            val destinationId = destination.id
            binding?.bottomNav?.setItemSelected(destinationId)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupArticle()
        setupArticlePost()
//        setupArticleRecyclerViewPost()
    }
    private fun setupArticlePost() {
        adapterPost = PostinganAdapter()
        binding?.rvAtcPostBaru?.apply {
            adapter = adapterPost
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
    private fun setupArticle() {
        adapterArticle = ArticleAdapter()
        binding?.rvAtcSemua?.apply {
            adapter = adapterArticle
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
    private fun observeLiveData() {
        viewModel.error.observe(this, ::handleError)
        viewModel.articles.observe(this, ::handleArticles)
        viewModel.filteredList.observe(this, {filteredArticles ->
            updateContent(filteredArticles)
        })
        binding?.btnSemua?.setOnClickListener{
            viewModel.setFilter(ArticleViewModel.FilterType.SEMUA)
        }
        binding?.btnTerbaru?.setOnClickListener{
            viewModel.setFilter(ArticleViewModel.FilterType.TERBARU)
        }
        binding?.btnKuliner?.setOnClickListener{
            viewModel.setFilter(ArticleViewModel.FilterType.KULINER)
        }
        binding?.btnWisata?.setOnClickListener{
            viewModel.setFilter(ArticleViewModel.FilterType.WISATA)
        }
    }

    private fun updateContent(filterArticle: List<Article>){
        adapterArticle?.setArticles(filterArticle)
    }

    private fun handleError(error: String?) {
        if (error != null) {
            AlertDialog.Builder(context).setMessage(error).create().show()
//            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        } else {
            Log.e("ArticleFragment", "Error message is null")
        }
    }

    private fun handleArticles(articles: List<Article>) {
        adapterArticle?.submitList(articles)
        adapterPost?.submitList(articles)
        adapterArticle?.notifyDataSetChanged()
        adapterPost?.notifyDataSetChanged()
    }

    private fun setBadgeMenu() {
        binding?.bottomNav?.showBadge(R.id.notificationFragment)
    }

    private fun setStatusBarTransparent() {
        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        requireActivity().window.statusBarColor = Color.TRANSPARENT
    }
    private fun navigateToDestination(itemId: Int, navController: NavController) {
        navController.navigate(itemId)
    }

}