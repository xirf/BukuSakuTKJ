package id.my.andka.bstkj.ui.screen.numbersystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.my.andka.bstkj.ui.theme.BsTKJTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import id.my.andka.bstkj.ui.components.NumberPad
import id.my.andka.bstkj.ui.components.NumberSystem

@Composable
fun NumberSystemScreen() {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var selectedInputSystem by remember { mutableStateOf(NumberSystem.BINARY) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                NumberInput(
                    label = "BIN",
                    prefix = 2,
                    value = inputValue,
                    isSelected = selectedInputSystem == NumberSystem.BINARY,
                )
            }
            item {
                NumberInput(
                    label = "OCT",
                    prefix = 8,
                    value = outputValue,
                    isSelected = selectedInputSystem == NumberSystem.OCTAL,
                )
            }
            item {
                NumberInput(
                    label = "DEC",
                    prefix = 10,
                    value = outputValue,
                    isSelected = selectedInputSystem == NumberSystem.DECIMAL,
                )
            }
            item {
                NumberInput(
                    label = "HEX",
                    prefix = 16,
                    value = outputValue,
                    isSelected = selectedInputSystem == NumberSystem.HEXADECIMAL,
                )
            }

        }
        NumberPad(
            onNumberClick = { inputValue += it },
            onDelete = { inputValue = inputValue.dropLast(1) },
            selectedSystem = selectedInputSystem,
            onClear = { inputValue = "" }
        )
    }
}

@Composable
fun NumberInput(
    label: String,
    prefix: Int,
    value: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            modifier = Modifier.weight(1f),
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "$prefix",
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}


fun convertNumber(
    input: String,
    fromSystem: NumberSystem,
    toSystem: NumberSystem
): String {
    if (input.isEmpty()) return ""

    val decimal = when (fromSystem) {
        NumberSystem.BINARY -> input.toLong(2)
        NumberSystem.DECIMAL -> input.toLong()
        NumberSystem.HEXADECIMAL -> input.toLong(16)
        NumberSystem.OCTAL -> input.toLong(8)
    }

    return when (toSystem) {
        NumberSystem.BINARY -> decimal.toString(2)
        NumberSystem.DECIMAL -> decimal.toString()
        NumberSystem.HEXADECIMAL -> decimal.toString(16).uppercase()
        NumberSystem.OCTAL -> decimal.toString(8)
    }
}

@Preview(
    device = "id:pixel_9",
    showSystemUi = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES or
            android.content.res.Configuration.UI_MODE_TYPE_UNDEFINED
)
@Composable
fun NumberSystemScreenPreview() {
    BsTKJTheme {
        NumberSystemScreen()
    }
}