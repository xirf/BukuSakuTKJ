package id.my.andka.bstkj.ui.screen.ipcalculator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun IpInput(
    modifier: Modifier = Modifier,
    onIpChanged: (address: String, subnet: Int) -> Unit = { _, _ -> },
    onSubnetChanged: (subnet: Int) -> Unit,
    subnet: Int = 24
) {
    data class IpState(
        val input1: String = "",
        val input2: String = "",
        val input3: String = "",
        val input4: String = ""
    )

    var ipState by remember { mutableStateOf(IpState()) }
    val focusManager = LocalFocusManager.current

    val handleIpSegmentChange = remember<(String, (String) -> IpState) -> Unit> {
        { newValue, updateState ->
            val intValue = newValue.toIntOrNull()
            if (intValue != null && intValue in 0..255) {
                ipState = updateState(newValue)
                if (newValue.length == 3) {
                    focusManager.moveFocus(FocusDirection.Next)
                }

                // Trigger IP changed hanya jika semua field terisi
                with(ipState) {
                    if (input1.isNotEmpty() && input2.isNotEmpty() &&
                        input3.isNotEmpty() && input4.isNotEmpty()) {
                        onIpChanged("$input1.$input2.$input3.$input4", subnet)
                    }
                }
            }
        }
    }

    val onInput1Change = remember { { value: String ->
        handleIpSegmentChange(value) { ipState.copy(input1 = it) }
    }}
    val onInput2Change = remember { { value: String ->
        handleIpSegmentChange(value) { ipState.copy(input2 = it) }
    }}
    val onInput3Change = remember { { value: String ->
        handleIpSegmentChange(value) { ipState.copy(input3 = it) }
    }}
    val onInput4Change = remember { { value: String ->
        handleIpSegmentChange(value) { ipState.copy(input4 = it) }
    }}

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OptimizedIpSegmentInput(
            value = ipState.input1,
            onValueChange = onInput1Change,
            label = "192"
        )
        Text(".")
        OptimizedIpSegmentInput(
            value = ipState.input2,
            onValueChange = onInput2Change,
            label = "168"
        )
        Text(".")
        OptimizedIpSegmentInput(
            value = ipState.input3,
            onValueChange = onInput3Change,
            label = "0"
        )
        Text(".")
        OptimizedIpSegmentInput(
            value = ipState.input4,
            onValueChange = onInput4Change,
            label = "1"
        )
        Text("/")
        OptimizedSubnetInput(
            value = subnet.toString(),
            onValueChange = { newValue ->
                if (newValue.length <= 2) {
                    val subnetValue = newValue.toIntOrNull() ?: return@OptimizedSubnetInput
                    if (subnetValue in 0..32) {
                        onSubnetChanged(subnetValue)
                        with(ipState) {
                            if (input1.isNotEmpty() && input2.isNotEmpty() &&
                                input3.isNotEmpty() && input4.isNotEmpty()) {
                                onIpChanged("$input1.$input2.$input3.$input4", subnetValue)
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun OptimizedIpSegmentInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    val keyboardOptions = remember {
        KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )
    }

    val focusManager = LocalFocusManager.current
    TextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        placeholder = { Text(text = label) },
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .width(64.dp)
            .onKeyEvent { keyEvent ->
                if (keyEvent.key == Key.Period) {
                    focusManager.moveFocus(FocusDirection.Next)
                    true
                } else false
            },
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
private fun OptimizedSubnetInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OptimizedIpSegmentInput(
        value = value,
        onValueChange = onValueChange,
        label = "24",
        modifier = modifier
    )
}