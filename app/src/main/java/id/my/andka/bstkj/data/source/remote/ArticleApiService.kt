package id.my.andka.bstkj.data.source.remote

import id.my.andka.bstkj.data.Article
import retrofit2.http.GET

interface ArticleApiService {
    @GET("api/articles.json")
    suspend fun fetchArticles(): List<Article>
}