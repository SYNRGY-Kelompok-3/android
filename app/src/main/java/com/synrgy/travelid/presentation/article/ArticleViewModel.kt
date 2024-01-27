package com.febi.myapplication

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.domain.model.main.Article
import com.synrgy.travelid.domain.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleViewModel (
    private val articleRepository: ArticleRepository,

    ) : ViewModel(){

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _currentFilter = MutableLiveData<FilterType>()
    val currentFilter: LiveData<FilterType> get() = _currentFilter

    private val _filteredList = MutableLiveData<List<Article>>()
    val filteredList: LiveData<List<Article>> get() = _filteredList

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        // Inisialisasi _currentFilter dengan FilterType.SEMUA
        _currentFilter.value = FilterType.SEMUA
    }
    fun fetchArticles() {
        viewModelScope.launch (Dispatchers.IO){
            try {
                withContext(Dispatchers.Main) {
                    val result = articleRepository.fetchArticle()
                    _articles.value = result
                }
            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    _error.value = e.message
                }
            }
        }
    }
    enum class FilterType{
        SEMUA,
        TERBARU,
        WISATA,
        KULINER
    }

    fun setFilter(filterType: FilterType) {
        _currentFilter.value = filterType
        filteredArticles()
    }
    fun filteredArticles(){
        val articles = _articles.value
        val filterType = _currentFilter.value ?: FilterType.SEMUA

        _filteredList.value = when (filterType) {
            FilterType.SEMUA -> articles
            FilterType.TERBARU -> articles?.sortedByDescending { it.updatedAt }
            FilterType.WISATA -> articles?.filter { it.category == "Wisata" }
            FilterType.KULINER -> articles?.filter { it.category == "Kuliner" }
        }
    }
}