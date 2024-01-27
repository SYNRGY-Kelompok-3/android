package com.synrgy.travelid.domain.repository

import com.synrgy.travelid.domain.model.main.Article

interface ArticleRepository {
    suspend fun fetchArticle() : List<Article>
}