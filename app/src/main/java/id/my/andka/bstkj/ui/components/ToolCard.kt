package id.my.andka.bstkj.ui.components

import android.content.res.Configuration
import android.content.res.Resources
import id.my.andka.bstkj.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.my.andka.bstkj.ui.theme.BsTKJTheme

@Composable
fun ToolCard(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                spotColor = MaterialTheme.colorScheme.surfaceContainerHigh
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(16.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column {
            Image(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp)

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
@Preview(
    device = "id:pixel_9_pro_xl", showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0x121316ff
)
fun ToolCardPreview() {
    BsTKJTheme {
        Surface {
            ToolCard(
                title = "IP Calculator",
                icon = ImageVector.vectorResource(id = R.drawable.i_wireless_color)
            )
        }
    }
}