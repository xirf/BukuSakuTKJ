package id.my.andka.bstkj.ui.screen.ipcalculator.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import id.my.andka.bstkj.ui.screen.ipcalculator.IpCalculatorViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import id.my.andka.bstkj.model.CalculationResult

@Composable
fun NetworkDetails(
    modifier: Modifier = Modifier,
    viewModel: IpCalculatorViewModel = viewModel()
) {
    val networkDetails by viewModel.networkDetails.collectAsState()

    Column(modifier = modifier.fillMaxWidth()) {
        when (networkDetails) {
            is CalculationResult.Idle -> {
                Text(text = "Enter IP and Subnet Mask", style = MaterialTheme.typography.bodyMedium)
            }
            is CalculationResult.Success -> {
                val details = (networkDetails as CalculationResult.Success).result
                Text(text = "Network Address: ${details.networkDetail.networkAddress}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Broadcast Address: ${details.broadcastAddress}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "First Host: ${details.networkDetail.firstHost}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Last Host: ${details.networkDetail.lastHost}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Total Hosts: ${details.networkDetail.totalSubnets}", style = MaterialTheme.typography.bodyMedium)
            }
            is CalculationResult.Failure -> {
                Text(text = "Error: ${(networkDetails as CalculationResult.Failure).error}", style = MaterialTheme.typography.bodyMedium)
            }

        }
    }
}