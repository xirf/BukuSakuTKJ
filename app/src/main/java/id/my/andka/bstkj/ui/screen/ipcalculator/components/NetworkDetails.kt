package id.my.andka.bstkj.ui.screen.ipcalculator.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import id.my.andka.bstkj.ui.screen.ipcalculator.IpCalculatorViewModel
import id.my.andka.bstkj.R
import androidx.lifecycle.viewmodel.compose.viewModel
import id.my.andka.bstkj.model.CalculationResult
import androidx.compose.material.icons.Icons
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import id.my.andka.bstkj.model.NetworkDetail
import kotlinx.coroutines.delay

@Composable
fun NetworkDetails(
    modifier: Modifier = Modifier,
    viewModel: IpCalculatorViewModel = viewModel()
) {
    val networkDetails by viewModel.networkDetails.collectAsState()

    Column(modifier = modifier.fillMaxWidth()) {
        when (networkDetails) {
            is CalculationResult.Idle -> {
                Text(
                    text = stringResource(R.string.inter_ip_or_subnet),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            is CalculationResult.Success -> {
                val details = (networkDetails as CalculationResult.Success).result
                NetworkDetailsCard(details)

            }
            is CalculationResult.Failure -> {
                Text(text = "Error: ${(networkDetails as CalculationResult.Failure).error}", style = MaterialTheme.typography.bodyMedium)
            }

        }
    }
}


@Composable
fun NetworkDetailsCard(details: NetworkDetail, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // IP Address Section
            DetailSection(
                title = "IP Information",
                items = listOf(
                    "Network Address" to details.ipAddress,
                    "Network Address (Binary)" to details.ipAddressBinary
                )
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Subnet Information
            DetailSection(
                title = "Subnet Information",
                items = listOf(
                    "Subnet Mask" to details.subnetMask,
                    "Subnet Mask (Binary)" to details.subnetMaskBinary,
                    "Wildcard Mask" to details.wildcardMask
                )
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Host Range
            DetailSection(
                title = "Host Range",
                items = listOf(
                    "First Host" to details.firstHost,
                    "Last Host" to details.lastHost,
                    "Broadcast Address" to details.broadcastAddress
                )
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Network Statistics
            DetailSection(
                title = "Network Statistics",
                items = listOf(
                    "Class" to details.ipClass,
                    "Effective Subnets" to details.effectiveSubnets.toString(),
                    "Effective Hosts" to details.effectiveHostsPerSubnet.toString()
                )
            )
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    items: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        items.forEach { (label, value) ->
            DetailRow(label = label, value = value)
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    var showCopiedToast by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        IconButton(
            onClick = {
                val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText(label, value)
                clipboardManager.setPrimaryClip(clip)
                showCopiedToast = true
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.ContentCopy,
                contentDescription = "Copy $label",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

    if (showCopiedToast) {
        LaunchedEffect(Unit) {
            delay(2000)
            showCopiedToast = false
        }
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}
