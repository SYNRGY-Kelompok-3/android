package com.synrgy.travelid.data.remote.service

import com.synrgy.travelid.data.remote.response.main.ArticleResponse
import retrofit2.http.GET
interface APIService {
    @GET("v1/artikel")
    suspend fun fetchArticle(): List<ArticleResponse>
}