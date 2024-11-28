package id.my.andka.bstkj.data

import id.my.andka.bstkj.data.source.local.ArticleDao
import id.my.andka.bstkj.data.source.remote.ApiService
import id.my.andka.bstkj.domain.interfaces.ArticleRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val apiService: ApiService,
    private val articleDao: ArticleDao
) : ArticleRepositoryInterface {

    override suspend fun getArticles(): Flow<List<Article>> = flow {
        val data = apiService.fetchArticles()
        emit(data)
    }.flowOn(Dispatchers.IO)

    override suspend fun getCachedArticles(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }

    override suspend fun getGroups(): Flow<List<String>> = flow {
        val groups = articleDao.getAllGroup()
        emit(groups)
    }.flowOn(Dispatchers.IO)

    override suspend fun getArticlesByGroup(group: String): Flow<List<Article>> = flow {
        val articles = articleDao.getArticlesByGroup(group)
        emit(articles)
    }.flowOn(Dispatchers.IO)

    override suspend fun getArticleBySlug(slug: String): Flow<Article> = flow {
        val article = articleDao.getArticleBySlug(slug)
        emit(article)
    }.flowOn(Dispatchers.IO)

    override suspend fun insertArticles(articles: List<Article>) {
        articleDao.insertArticles(articles)
    }


}