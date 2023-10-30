package com.hfad.mynews.ui

import RetrofitInstance
import androidx.lifecycle.LiveData
import com.hfad.mynews.db.ArticleDao
import com.hfad.mynews.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(private val articleDao: ArticleDao) {

    val getSavedArticles: LiveData<List<Article>> = articleDao.getAllArticles()

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = withContext(Dispatchers.IO) {
        articleDao.upsert(article)
    }

    suspend fun deleteArticle(article: Article) = withContext(Dispatchers.IO) {
        articleDao.deleteArticle(article)
    }

}