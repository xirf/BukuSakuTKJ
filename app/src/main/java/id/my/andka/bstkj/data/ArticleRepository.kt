package id.my.andka.bstkj.data

import id.my.andka.bstkj.data.source.local.ArticleDao
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val apiService: ArticleApiService,
    private val articleDao: ArticleDao
) {
    suspend fun getArticles(): List<Article> {
        val articles = apiService.fetchArticles()
        articleDao.insertArticles(articles)
        return articles
    }

    suspend fun getCachedArticles(): List<Article> {
        return articleDao.getAllArticles()
    }

    suspend fun getArticlesByGroup(groupName: String): List<Article> {
        return articleDao.getArticlesByGroup(groupName)
    }

    suspend fun getArticleById(id: String): Article {
        return articleDao.getArticleById(id)
    }

    suspend fun getGroup(): List<String>{
        return articleDao.getAllGroup()
    }
}