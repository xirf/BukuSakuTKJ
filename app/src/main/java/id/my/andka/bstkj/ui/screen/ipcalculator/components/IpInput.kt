package id.my.andka.bstkj.ui.screen.ipcalculator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun IpInput(
    modifier: Modifier = Modifier,
    onIpChanged: (address: String, subnet: Int) -> Unit = { _, _ -> },
    onSubnetChanged: (subnet: Int) -> Unit,
    subnet: Int = 24
) {
    var input1 by remember { mutableStateOf("") }
    var input2 by remember { mutableStateOf("") }
    var input3 by remember { mutableStateOf("") }
    var input4 by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    fun triggerIpChanged() {
        if (input1.isNotEmpty() && input2.isNotEmpty() && input3.isNotEmpty() && input4.isNotEmpty()) {
            val ipAddress = "$input1.$input2.$input3.$input4"
            onIpChanged(ipAddress, subnet)
        }
    }

    @Composable
    fun IpSegmentInput(value: String, onValueChange: (String) -> Unit, label: String) {
        SmallNumberInput(
            label = label,
            value = value,
            onValueChange = {
                    onValueChange(it)
                    triggerIpChanged()
            }
        )
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IpSegmentInput(value = input1, onValueChange = { input1 = it }, label = "192")
        Text(".")
        IpSegmentInput(value = input2, onValueChange = { input2 = it }, label = "168")
        Text(".")
        IpSegmentInput(value = input3, onValueChange = { input3 = it }, label = "0")
        Text(".")
        IpSegmentInput(value = input4, onValueChange = { input4 = it }, label = "1")
        Text("/")
        SmallNumberInput(
            label = "24",
            min = 0,
            max = 32,
            value = subnet.toString(),
            onValueChange = {
                if (it.length <= 2) {
                    onSubnetChanged(it.toInt())
                    triggerIpChanged()
                }
            }
        )
    }
}