package id.my.andka.bstkj.ui.screen.ipcalculator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager

@Composable
fun IpInput(
    modifier: Modifier = Modifier,
    onIpChanged: (address: String, subnet: Int) -> Unit = { _, _ -> },
    subnet: Int = 24
) {
    var input1 by remember { mutableStateOf("") }
    var input2 by remember { mutableStateOf("") }
    var input3 by remember { mutableStateOf("") }
    var input4 by remember { mutableStateOf("") }
    var subnetMask by remember { mutableIntStateOf(subnet) }
    val focusManager = LocalFocusManager.current

    fun triggerIpChanged() {
        if (input1.isNotEmpty() && input2.isNotEmpty() && input3.isNotEmpty() && input4.isNotEmpty()) {
            val ipAddress = "$input1.$input2.$input3.$input4"
            onIpChanged(ipAddress, subnetMask)
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SmallNumberInput(
            label = "192",
            value = input1,
            onValueChange = {
                if (it.length <= 3) {
                    input1 = it
                    if (it.length == 3) focusManager.moveFocus(FocusDirection.Next)
                    triggerIpChanged()
                }
            }
        )
        Text(".")
        SmallNumberInput(
            label = "168",
            value = input2,
            onValueChange = {
                if (it.length <= 3) {
                    input2 = it
                    if (it.length == 3) focusManager.moveFocus(FocusDirection.Next)
                    triggerIpChanged()
                }
            }
        )
        Text(".")
        SmallNumberInput(
            label = "0",
            value = input3,
            onValueChange = {
                if (it.length <= 3) {
                    input3 = it
                    if (it.length == 3) focusManager.moveFocus(FocusDirection.Next)
                    triggerIpChanged()
                }
            }
        )
        Text(".")
        SmallNumberInput(
            label = "1",
            value = input4,
            onValueChange = {
                if (it.length <= 3) {
                    input4 = it
                    if (it.length == 3) focusManager.moveFocus(FocusDirection.Next)
                    triggerIpChanged()
                }
            }
        )
        Text("/")
        SmallNumberInput(
            label = "24",
            min = 0,
            max = 32,
            value = subnetMask.toString(),
            onValueChange = {
                if (it.length <= 2) {
                    subnetMask = it.toInt()
                    triggerIpChanged()
                }
            }
        )
    }
}