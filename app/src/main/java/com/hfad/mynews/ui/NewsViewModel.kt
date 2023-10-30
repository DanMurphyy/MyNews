package com.hfad.mynews.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hfad.mynews.db.ArticleDatabase
import com.hfad.mynews.models.Article
import com.hfad.mynews.models.NewsResponse
import com.hfad.mynews.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val articleDao = ArticleDatabase.invoke((application)).getArticleDao()
    private val repository: NewsRepository

    val getAllArticles: LiveData<List<Article>>

    init {
        repository = NewsRepository(articleDao)
        getAllArticles = repository.getSavedArticles
        getBreakingNews("us")
    }

    private val _breakingNews = MutableLiveData<Resource<NewsResponse>>()
    val breakingNews: LiveData<Resource<NewsResponse>> = _breakingNews
    private var breakingNewsPage = 1

    private val _searchNews = MutableLiveData<Resource<NewsResponse>>()
    val searchNews: LiveData<Resource<NewsResponse>> = _searchNews
    private var searchNewsPage = 1


    fun getBreakingNews(countryCode: String) = viewModelScope.launch(Dispatchers.IO) {
        _breakingNews.postValue(Resource.Loading())
        val response = repository.getBreakingNews(countryCode, breakingNewsPage)
        _breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        _searchNews.postValue(Resource.Loading())
        val response = repository.searchNews(searchQuery, searchNewsPage)
        _searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.upsert(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

}