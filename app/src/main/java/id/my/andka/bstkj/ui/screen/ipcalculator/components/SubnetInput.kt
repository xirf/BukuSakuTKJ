package id.my.andka.bstkj.ui.screen.ipcalculator.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

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
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(subnetMasks[netMask - 8]) }
    var currentMask by remember { mutableIntStateOf(netMask) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Subnet Mask", style = MaterialTheme.typography.titleMedium
        )
        Box(
            modifier = modifier
                .width(200.dp)
                .padding(4.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceContainerHigh,
                    RoundedCornerShape(8.dp)
                )
                .clickable(
                    onClick = { expanded = true }
                )
                .padding(vertical = 16.dp, horizontal = 24.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = selectedItem,
                    style = MaterialTheme.typography.bodyLarge
                )
                Image(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "DropDown Icon",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(0.dp, 0.dp),
            ) {
                subnetMasks.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item.toString(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        onClick = {
                            selectedItem = item
                            currentMask = subnetMasks.indexOf(item) + 8
                            onMaskSelected(currentMask)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
    Slider(
        value = currentMask.toFloat(),
        onValueChange = {
            currentMask = it.toInt()
            selectedItem = subnetMasks[currentMask - 8]
            onMaskSelected(currentMask)
        },
        valueRange = 8f..30f,
        steps = 23,
        modifier = Modifier.fillMaxWidth()
    )
}