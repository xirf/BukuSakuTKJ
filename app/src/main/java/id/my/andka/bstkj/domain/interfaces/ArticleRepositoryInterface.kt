package id.my.andka.bstkj.domain.interfaces

import id.my.andka.bstkj.data.Article
import kotlinx.coroutines.flow.Flow

interface  ArticleRepositoryInterface {
    suspend fun getArticles(): Flow<List<Article>>
    suspend fun getCachedArticles(): Flow<List<Article>>
    suspend fun getGroups(): Flow<List<String>>
    suspend fun insertArticles(articles: List<Article>): Unit
}