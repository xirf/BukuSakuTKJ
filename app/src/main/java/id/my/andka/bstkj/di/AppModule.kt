package id.my.andka.bstkj.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.andka.bstkj.data.AppDatabase
import id.my.andka.bstkj.data.ArticleApiService
import id.my.andka.bstkj.data.ArticleRepository
import id.my.andka.bstkj.data.source.local.ArticleDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApiService(): ArticleApiService {
        return Retrofit.Builder()
            .baseUrl("https://bstkj.andka.my.id/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArticleApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideArticleDao(database: AppDatabase): ArticleDao {
        return database.articleDao()
    }

    @Provides
    fun provideArticleRepository(apiService: ArticleApiService, articleDao: ArticleDao): ArticleRepository {
        return ArticleRepository(
            apiService = apiService,
            articleDao = articleDao
        )
    }
}