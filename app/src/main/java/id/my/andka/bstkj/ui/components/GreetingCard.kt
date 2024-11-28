package id.my.andka.bstkj.ui.components

import id.my.andka.bstkj.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalTime

@Composable
fun GreetingCard(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = getGreeting(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Bagaimana harimu?",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun getGreeting(): String {
    val hour = LocalTime.now().hour
    return when {
        hour < 12 -> stringResource(id = R.string.good_morning)
        hour < 17 -> stringResource(id = R.string.good_afternoon)
        hour < 19 -> stringResource(id = R.string.good_evening)
        else -> stringResource(id = R.string.good_night)
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun GreetingCardPreview() {
    MaterialTheme {
        GreetingCard()
    }
}