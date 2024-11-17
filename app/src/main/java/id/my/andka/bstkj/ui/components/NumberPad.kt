package id.my.andka.bstkj.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.my.andka.bstkj.model.PadButton
import id.my.andka.bstkj.ui.theme.BsTKJTheme

enum class NumberSystem {
    BINARY, DECIMAL, HEXADECIMAL, OCTAL
}

@Composable
fun NumberPad(
    onNumberClick: (String) -> Unit,
    onDelete: () -> Unit,
    onClear: () -> Unit,
    selectedSystem: NumberSystem,
    modifier: Modifier = Modifier
) {
    val buttons = remember {
        listOf(
            listOf(
                PadButton.Clear,
                PadButton.Delete,
                PadButton.Number("F"),
                PadButton.Number("E")
            ),
            listOf(
                PadButton.Number("7"),
                PadButton.Number("8"),
                PadButton.Number("9"),
                PadButton.Number("D")
            ),
            listOf(
                PadButton.Number("4"),
                PadButton.Number("5"),
                PadButton.Number("6"),
                PadButton.Number("C")
            ),
            listOf(
                PadButton.Number("1"),
                PadButton.Number("2"),
                PadButton.Number("3"),
                PadButton.Number("B")
            ),
            listOf(
                PadButton.TripleZero,
                PadButton.DoubleZero,
                PadButton.Number("0"),
                PadButton.Number("A")
            )
        )
    }

    val isButtonEnabled: (PadButton) -> Boolean = { button ->
            when (button) {
                is PadButton.Clear, is PadButton.Delete -> true
                is PadButton.Number -> when (selectedSystem) {
                    NumberSystem.BINARY -> button.value in listOf("0", "1")
                    NumberSystem.OCTAL -> button.value.toIntOrNull() in 0..7
                    NumberSystem.DECIMAL -> button.value.toIntOrNull() != null
                    NumberSystem.HEXADECIMAL -> true
                }

                is PadButton.TripleZero,
                is PadButton.DoubleZero -> when (selectedSystem) {
                    NumberSystem.BINARY -> true
                    NumberSystem.OCTAL -> true
                    NumberSystem.DECIMAL -> true
                    NumberSystem.HEXADECIMAL -> false
                }
            }
        }


    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface,

        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        disabledContentColor = MaterialTheme.colorScheme.outlineVariant,
    )

    val specialButton = ButtonDefaults.buttonColors(
        contentColor = MaterialTheme.colorScheme.primary,
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    )

    Column(modifier = modifier.fillMaxWidth()) {
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                row.forEach { button ->
                    NumberPadButton(
                        button = button,
                        isEnabled = isButtonEnabled(button),
                        colors = if (button == PadButton.Clear || button == PadButton.Delete) {
                            specialButton
                        } else {
                            buttonColors
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .height(64.dp),
                        onNumberClick = onNumberClick,
                        onDelete = onDelete,
                        onClear = onClear
                    )
                }
            }
        }
    }
}

@Composable
private fun NumberPadButton(
    button: PadButton,
    isEnabled: Boolean,
    colors: ButtonColors,
    modifier: Modifier,
    onNumberClick: (String) -> Unit,
    onDelete: () -> Unit,
    onClear: () -> Unit
) {
    Button(
        onClick = {
            when (button) {
                is PadButton.Clear -> onClear()
                is PadButton.Delete -> onDelete()
                else -> onNumberClick(button.label)
            }
        },
        modifier = modifier,
        enabled = isEnabled,
        colors = colors,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = button.label,
            fontSize = 20.sp
        )
    }
}

@Preview(device = "id:pixel_9", showSystemUi = true, showBackground = true)
@Composable
fun Preview(modifier: Modifier = Modifier) {
    BsTKJTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            NumberPad(
                onNumberClick = { },
                onDelete = { },
                onClear = { },
                selectedSystem = NumberSystem.HEXADECIMAL,
                modifier = Modifier
            )
        }
    }
}