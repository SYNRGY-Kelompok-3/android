package com.synrgy.travelid.helper

import com.synrgy.travelid.domain.model.main.Article

interface OnClickListener {
    fun onClickItem (article: Article)
}