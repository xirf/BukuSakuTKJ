package id.my.andka.bstkj.data

import android.util.Log
import id.my.andka.bstkj.data.source.local.ArticleDao
import id.my.andka.bstkj.data.source.remote.ApiClient
import id.my.andka.bstkj.data.source.remote.ArticleApiService
import id.my.andka.bstkj.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val articleDao: ArticleDao
) {
    private val apiService = ApiClient.articleApiService

    fun getArticles(): Flow<UiState<List<Article>>> = flow {
        emit(UiState.Loading())
        try {
            val data = apiService.fetchArticles()
            Log.d("ArticleRepository", "getArticles: ${data.spliterator().estimateSize()}")
            insertArticles(data)
            emit(UiState.Success(data))
        } catch (e: Exception) {
            emit(UiState.Error("Failed to fetch data: ${e.message}"))
        }
    }

    fun getCachedArticles(): Flow<UiState<List<Article>>> = flow {
        emit(UiState.Loading())
        try {
            val data = articleDao.getAllArticles()
            emit(UiState.Success(data))
        } catch (e: Exception) {
            emit(UiState.Error("Failed to fetch data: ${e.message}"))
        }
    }

    suspend fun insertArticles(articles: List<Article>) {
        withContext(Dispatchers.IO) {
            articleDao.insertArticles(articles)
        }
    }

    fun getArticlesByGroup(groupName: String): Flow<UiState<List<Article>>> = flow {
        emit(UiState.Loading())
        try {
            val data = articleDao.getArticlesByGroup(groupName)
            emit(UiState.Success(data))
        } catch (e: Exception) {
            emit(UiState.Error("Failed to fetch data: ${e.message}"))
        }
    }

    fun getArticleById(id: String): Flow<UiState<Article>> = flow {
        emit(UiState.Loading())
        try {
            val data = articleDao.getArticleById(id)
            emit(UiState.Success(data))
        } catch (e: Exception) {
            emit(UiState.Error("Failed to fetch data: ${e.message}"))
        }
    }

    fun getGroup(): Flow<UiState<List<String>>> = flow {
        emit(UiState.Loading())
        try {
            val data = articleDao.getAllGroup()
            emit(UiState.Success(data))
        } catch (e: Exception) {
            emit(UiState.Error("Failed to fetch data: ${e.message}"))
        }
    }
}