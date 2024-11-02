package id.my.andka.bstkj.ui.screen.ipcalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import id.my.andka.bstkj.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.my.andka.bstkj.ui.theme.BsTKJTheme
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.derivedStateOf
import id.my.andka.bstkj.ui.components.Dropdown
import id.my.andka.bstkj.ui.components.SmallNumberInput

@Composable
fun IPCalculatorScreen(
    modifier: Modifier = Modifier,
) {
    var ipInput by remember { mutableStateOf(TextFieldValue("")) }
    var subnetMask by remember { mutableIntStateOf(24) }
    var netMask by remember { mutableStateOf("255.0.0.0") }
    val subnetMasks = mapOf<String, Int>(
        "255.0.0.0" to 8,
        "255.128.0.0" to 9,
        "255.192.0.0" to 10,
        "255.224.0.0" to 11,
        "255.240.0.0" to 12,
        "255.248.0.0" to 13,
        "255.252.0.0" to 14,
        "255.254.0.0" to 15,
        "255.255.0.0" to 16,
        "255.255.128.0" to 17,
        "255.255.192.0" to 18,
        "255.255.224.0" to 19,
        "255.255.240.0" to 20,
        "255.255.248.0" to 21,
        "255.255.252.0" to 22,
        "255.255.254.0" to 23,
        "255.255.255.0" to 24,
        "255.255.255.128" to 25,
        "255.255.255.192" to 26,
        "255.255.255.224" to 27,
        "255.255.255.240" to 28,
        "255.255.255.248" to 29,
        "255.255.255.252" to 30,
        "255.255.255.254" to 31,
        "255.255.255.255" to 32
    )

    val derivedNetMask = remember(subnetMask) {
        derivedStateOf { subnetMasks.entries.find { it.value == subnetMask }?.key ?: "255.0.0.0" }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
        ,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.ip_calculator),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Black
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Alamat IPv4",
            style = MaterialTheme.typography.titleMedium
        )
        IpInput()
        Spacer(modifier = Modifier.height(8.dp))
        SubnetInput(
            subnetMasks = subnetMasks.keys.toList(),
            onMaskSelected = {subnetMask = it},
            netMask = derivedNetMask.value,
            subnetMask = subnetMask.toFloat()
        )

    }
}

@Composable
fun SubnetInput(
    modifier: Modifier = Modifier,
    subnetMasks: List<String>,
    onMaskSelected: (Int) -> Unit,
    netMask: String,
    subnetMask: Float
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Subnet Mask",
            style = MaterialTheme.typography.titleMedium
        )
        Dropdown(
            onMaskSelected = {onMaskSelected},
            values = subnetMasks,
            selected = netMask
        )
    }
    Slider(
        value = subnetMask,
        onValueChange = { onMaskSelected(it.toInt()) },
        valueRange = 0f..32f,
        steps = 31,
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
fun IpInput(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SmallNumberInput(label = "192")
        Text(".")
        SmallNumberInput(label = "192")
        Text(".")
        SmallNumberInput(label = "192")
        Text(".")
        SmallNumberInput(label = "192")
        Text("/")
        SmallNumberInput(label = "24")
    }
}

@Composable
@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES or android.content.res.Configuration.UI_MODE_TYPE_UNDEFINED,
    device = "id:pixel_6"
)
fun IPCalculatorScreenPreview() {
    BsTKJTheme {
        Surface {
            IPCalculatorScreen()
        }
    }
}