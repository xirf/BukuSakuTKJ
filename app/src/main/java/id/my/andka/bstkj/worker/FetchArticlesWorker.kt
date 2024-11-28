package id.my.andka.bstkj.worker

import android.content.Context
import android.util.Log
import androidx.work.WorkerParameters
import id.my.andka.bstkj.data.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.work.CoroutineWorker

class FetchArticlesWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val repository: ArticleRepository
): CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            repository.getArticles().collect { articles ->
                repository.insertArticles(articles)
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}