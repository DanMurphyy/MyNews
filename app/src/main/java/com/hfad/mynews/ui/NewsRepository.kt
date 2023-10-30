package com.hfad.mynews.ui

import RetrofitInstance
import androidx.lifecycle.LiveData
import com.hfad.mynews.db.ArticleDao
import com.hfad.mynews.models.Article

class NewsRepository(private val articleDao: ArticleDao) {

    val getAllArticles: LiveData<List<Article>> = articleDao.getAllArticles()

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

}