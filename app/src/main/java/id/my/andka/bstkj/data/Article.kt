package id.my.andka.bstkj.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import kotlinx.serialization.Serializable

@Entity(tableName = "articles")
@Serializable
data class Article(
    @PrimaryKey val id: String,
    val slug: String,
    val body: String,
    val title: String,
    val groupName: String,
    val updatedDate: String
)