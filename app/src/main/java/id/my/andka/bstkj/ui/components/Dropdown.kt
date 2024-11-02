package id.my.andka.bstkj.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme

@Composable
fun Dropdown(
    onMaskSelected: (Any) -> Unit,
    modifier: Modifier = Modifier,
    values: List<String>,
    selected: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(selected ?: values[0]) }
    var items by remember { mutableStateOf(values) }


    Box(
        modifier = modifier
            .width(200.dp)
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.surfaceContainerHigh, RoundedCornerShape(8.dp))
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
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item.toString(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    onClick = {
                        selectedItem = item
                        expanded = false
                    }
                )
            }
        }
    }
}