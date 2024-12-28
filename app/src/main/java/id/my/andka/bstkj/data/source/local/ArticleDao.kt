package id.my.andka.bstkj.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.my.andka.bstkj.data.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<Article>)

    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<Article>

    @Query("SELECT DISTINCT groupName FROM articles ORDER BY groupName")
    suspend fun getAllGroup(): List<String>

    @Query("SELECT * FROM articles WHERE groupName = :groupName")
    suspend fun getArticlesByGroup(groupName: String): List<Article>

    @Query("SELECT * FROM articles WHERE slug = :slug")
    suspend fun getArticleBySlug(slug: String): Article

    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun getArticleById(id: String): Article

    @Query("DELETE FROM articles")
    suspend fun clearArticles()
}