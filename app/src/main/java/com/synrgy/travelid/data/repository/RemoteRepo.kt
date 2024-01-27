package com.synrgy.travelid.data.repository

import com.synrgy.travelid.data.remote.response.main.toFetchArticle
import com.synrgy.travelid.data.remote.service.APIService
import com.synrgy.travelid.domain.model.main.Article
import com.synrgy.travelid.domain.repository.ArticleRepository

class RemoteRepo(
    private val APIService: APIService
): ArticleRepository {

    override suspend fun fetchArticle(): List<Article> {
        return APIService.fetchArticle().map { it.toFetchArticle() }
    }
}