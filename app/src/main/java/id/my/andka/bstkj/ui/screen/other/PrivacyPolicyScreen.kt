package id.my.andka.bstkj.ui.screen.other

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.jeziellago.compose.markdowntext.MarkdownText
import id.my.andka.bstkj.R

@Composable
fun PrivacyPolicyScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
    ) {
        item{
            MarkdownText(
                markdown = stringResource(R.string.tos_content),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                syntaxHighlightColor = Color.Black,
                enableSoftBreakAddsNewLine = true,
                isTextSelectable = true,
            )
        }
    }
}