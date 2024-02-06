package com.synrgy.travelid.domain.repository

import com.synrgy.travelid.domain.model.main.Article

interface SideRepository {
    suspend fun fetchArticle() : List<Article>
}