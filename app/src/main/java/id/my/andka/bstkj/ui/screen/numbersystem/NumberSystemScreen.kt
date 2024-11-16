package id.my.andka.bstkj.ui.screen.numbersystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.my.andka.bstkj.R

@Composable
fun NumberSystemScreen(
    modifier: Modifier = Modifier,
//    viewModel: NumberSystemViewModel = viewModel()
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.number_system),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 24.sp, fontWeight = FontWeight.Black
                )
            )
        }
    }
}