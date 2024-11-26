package id.my.andka.bstkj.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Entity(tableName = "articles")
@Serializable
data class Article(
    @field:SerializedName("id")
    @PrimaryKey val id: String,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("slug")
    val slug: String,
    @field:SerializedName("body")
    val body: String,
    @field:SerializedName("group")
    val groupName: String,
    @field:SerializedName("updatedDate")
    val updatedDate: String
)