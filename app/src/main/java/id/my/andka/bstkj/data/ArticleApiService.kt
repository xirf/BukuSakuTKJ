package id.my.andka.bstkj.data

import retrofit2.http.GET

interface ArticleApiService {
    @GET("api/articles.json")
    suspend fun fetchArticles(): List<Article>
}