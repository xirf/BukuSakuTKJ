package id.my.andka.bstkj.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jeziellago.compose.markdowntext.MarkdownText
import id.my.andka.bstkj.ui.common.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    slug: String,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val viewModelResult by viewModel.viewModelResult.collectAsState()

    LaunchedEffect(slug) {
        viewModel.fetchArticle(slug)
    }

    when (val articleResult = viewModelResult.articleResult) {
        is UiState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            LazyColumn(
                modifier = modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxSize()
            ) {
                item {
                    MarkdownText(
                        markdown = "## ${articleResult.data?.title}",
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        syntaxHighlightColor = Color.Black,
                        enableSoftBreakAddsNewLine = true,
                        isTextSelectable = true,
                    )
                    MarkdownText(
                        markdown = articleResult.data?.body.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 32.dp),
                        syntaxHighlightColor = Color.Black,
                        enableSoftBreakAddsNewLine = true,
                        isTextSelectable = true,
                    )
                }
            }
        }

        is UiState.Error -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${articleResult.message}",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        is UiState.Idle -> {}
    }
}