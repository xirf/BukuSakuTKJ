package id.my.andka.bstkj.ui.screen.ipcalculator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import id.my.andka.bstkj.ui.components.Dropdown

@Composable
fun SubnetInput(
    modifier: Modifier = Modifier,
    onMaskSelected: (Int) -> Unit,
    netMask: Int,
) {
    val subnetMasks = listOf(
        "255.0.0.0",
        "255.128.0.0",
        "255.192.0.0",
        "255.224.0.0",
        "255.240.0.0",
        "255.248.0.0",
        "255.252.0.0",
        "255.254.0.0",
        "255.255.0.0",
        "255.255.128.0",
        "255.255.192.0",
        "255.255.224.0",
        "255.255.240.0",
        "255.255.248.0",
        "255.255.252.0",
        "255.255.254.0",
        "255.255.255.0",
        "255.255.255.128",
        "255.255.255.192",
        "255.255.255.224",
        "255.255.255.240",
        "255.255.255.248",
        "255.255.255.252",
        "255.255.255.254",
        "255.255.255.255"
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Subnet Mask", style = MaterialTheme.typography.titleMedium
        )
        Dropdown(
            onMaskSelected = { selected ->
                subnetMasks.find {
                    it == selected
                }?.let {
                    // Because list starts from 0 we need to add 1
                    onMaskSelected(subnetMasks.indexOf(it) + 1)
                }
            },
            values = subnetMasks,
            selected = subnetMasks[netMask],
        )
    }
    Slider(
        value = netMask.toFloat(),
        onValueChange = { onMaskSelected(it.toInt()) },
        valueRange = 0f..32f,
        steps = 31,
        modifier = Modifier.fillMaxWidth()
    )
}