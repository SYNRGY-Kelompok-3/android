package com.synrgy.travelid.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.presentation.article.DetailArticleFragment
import com.synrgy.travelid.R
import com.synrgy.travelid.presentation.article.ArticleFragment

class ArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, DetailArticleFragment())
                .commit()
        }
    }
}