package id.my.andka.bstkj.ui.screen.ipcalculator.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.my.andka.bstkj.ui.theme.BsTKJTheme

@Composable
fun SmallNumberInput(
    label: String,
    value: String,
    min: Int = 0,
    max: Int = 255,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = value,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        onValueChange = { newValue: String ->
            val intValue = newValue.toIntOrNull()
            if (intValue != null && intValue in min..max) {
                onValueChange(newValue)
            }
        },
        singleLine = true,
        placeholder = { Text(text = label) },
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.width(64.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedPlaceholderColor = MaterialTheme.colorScheme.outline,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.outline,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    )
}

@Composable
@Preview
fun SmallNumberInputPreview() {
    BsTKJTheme {
        SmallNumberInput(
            label = "192",
            value = "",
            onValueChange = {}
        )
    }
}