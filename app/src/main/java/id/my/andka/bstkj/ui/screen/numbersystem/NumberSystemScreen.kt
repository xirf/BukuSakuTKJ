package id.my.andka.bstkj.ui.screen.numbersystem

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import id.my.andka.bstkj.ui.theme.BsTKJTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import id.my.andka.bstkj.ui.components.NumberPad
import id.my.andka.bstkj.R
import id.my.andka.bstkj.ui.components.NumberSystem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun NumberSystemScreen(
    modifier: Modifier = Modifier
) {
    var inputValue by remember { mutableStateOf("0") }
    var selectedInputSystem by remember { mutableStateOf(NumberSystem.BINARY) }
    val inputFlow = remember { MutableStateFlow(inputValue) }

    val outputValue = remember(inputValue, selectedInputSystem) {
        convertNumber(inputValue, selectedInputSystem).toTypedArray()
    }

    LaunchedEffect(inputFlow) {
        inputFlow.collectLatest { value ->
            delay(25)
            inputValue = value
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            listOf(
                "BIN" to NumberSystem.BINARY,
                "OCT" to NumberSystem.OCTAL,
                "DEC" to NumberSystem.DECIMAL,
                "HEX" to NumberSystem.HEXADECIMAL
            ).forEachIndexed { index, (label, system) ->
                item {
                    NumberInput(
                        label = label,
                        prefix = when (system) {
                            NumberSystem.BINARY -> 2
                            NumberSystem.OCTAL -> 8
                            NumberSystem.DECIMAL -> 10
                            NumberSystem.HEXADECIMAL -> 16
                        },
                        value = outputValue[index],
                        isSelected = selectedInputSystem == system,
                        modifier = Modifier.clickable {
                            selectedInputSystem = system
                            inputValue = outputValue[index]
                            inputFlow.value = outputValue[index]
                        }
                    )
                }
            }
        }
        NumberPad(
            onNumberClick = {
                inputValue += it
                inputFlow.value = inputValue
            },
            onDelete = {
                if (inputValue.isNotEmpty()) {
                    inputValue = inputValue.dropLast(1)
                    inputFlow.value = inputValue
                }
            },
            selectedSystem = selectedInputSystem,
            onClear = {
                inputValue = "0"
                inputFlow.value = "0"
            }
        )
    }
}

fun convertNumber(input: String, fromSystem: NumberSystem): List<String> {
    if (input.isEmpty()) return listOf("0", "0", "0", "0")

    val decimal = when (fromSystem) {
        NumberSystem.BINARY -> input.toLong(2)
        NumberSystem.DECIMAL -> input.toDouble().toLong()
        NumberSystem.HEXADECIMAL -> input.toLong(16)
        NumberSystem.OCTAL -> input.toLong(8)
    }

    return listOf(
        decimal.toString(2),
        decimal.toString(8),
        decimal.toString(10),
        decimal.toString(16)
    )
}

@Composable
fun NumberInput(
    label: String,
    prefix: Int,
    value: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val ctx = LocalContext.current
    val copiedMessage = stringResource(id = R.string.copied)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "$prefix",
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(20.dp),
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    clipboardManager.setText(AnnotatedString(value))
                    Toast.makeText(ctx, copiedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
        ) {
            Icon(
                imageVector = Icons.Outlined.ContentCopy,
                contentDescription = "Copy",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
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