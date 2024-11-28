package id.my.andka.bstkj.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.my.andka.bstkj.data.Article

@Composable
fun BottomSheetArticles(
    modifier: Modifier = Modifier,
    data: List<Article>?,
    onArticleClick: (String, String) -> Unit
) {
    // Display a message if the data is null or empty
    if (data.isNullOrEmpty()) {
        Text(
            text = "Tidak ada pelajaran, coba hidupkan internet untuk melihat konten",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) // Dimmed text color
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = modifier,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Space between items
        ) {
            items(data.size) { index ->
                ArticleCard(
                    article = data[index],
                    modifier = Modifier.clickable {
                        onArticleClick(
                            data[index].slug,
                            data[index].title
                        )
                    }
                )
            }
        }
    }
}