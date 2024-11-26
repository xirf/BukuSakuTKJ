package id.my.andka.bstkj.data.source.local

import androidx.room.Database
import android.content.Context
import id.my.andka.bstkj.data.Article
import androidx.room.RoomDatabase
import androidx.room.Room

@Database(entities = [Article::class], version = 1)
abstract class AppDatabase : androidx.room.RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "article_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}