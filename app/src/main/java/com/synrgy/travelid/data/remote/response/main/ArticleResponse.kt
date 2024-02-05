package com.synrgy.travelid.data.remote.response.main

import com.google.gson.annotations.SerializedName
import com.synrgy.travelid.domain.model.main.Article

data class ArticleResponse (
    @SerializedName("id"         ) var id        : String? = null,
    @SerializedName("title"      ) var title     : String? = null,
    @SerializedName("category"   ) var category  : String? = null,
    @SerializedName("content"    ) var content   : String? = null,
    @SerializedName("image_url"  ) var imageUrl  : String? = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("updated_at" ) var updatedAt : String? = null

    )

fun ArticleResponse.toFetchArticle() : Article {
    return Article(
        id = id.orEmpty(),
        title = title.orEmpty(),
        category = category.orEmpty(),
        content = content.orEmpty(),
        imageUrl = imageUrl.orEmpty(),
        createdAt = createdAt.orEmpty(),
        updatedAt = updatedAt.orEmpty()
    )
}



