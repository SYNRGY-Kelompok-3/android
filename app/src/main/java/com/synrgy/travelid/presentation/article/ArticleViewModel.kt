package com.synrgy.travelid.presentation.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.travelid.domain.model.main.Article
import com.synrgy.travelid.domain.repository.SideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val sideRepository: SideRepository
): ViewModel(){
    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _secondArticles = MutableLiveData<List<Article>>()
    val secondArticles: LiveData<List<Article>> = _secondArticles

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun fetchArticles() {
        viewModelScope.launch (Dispatchers.IO){
            try {
                val result = sideRepository.fetchArticle()
                withContext(Dispatchers.Main) {
                    _articles.value = result
                }
            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    _error.value = e.message
                }
            }
        }
    }

    fun fetchSecondArticles() {
        viewModelScope.launch (Dispatchers.IO){
            try {
                val result = sideRepository.fetchArticle()
                withContext(Dispatchers.Main) {
                    _secondArticles.value = result
                }
            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    _error.value = e.message
                }
            }
        }
    }
}