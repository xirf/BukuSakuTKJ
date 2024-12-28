package id.my.andka.bstkj.ui.screen.ipcalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.my.andka.bstkj.ui.theme.BsTKJTheme
import id.my.andka.bstkj.ui.screen.ipcalculator.components.IpInput
import id.my.andka.bstkj.ui.screen.ipcalculator.components.NetworkDetails
import id.my.andka.bstkj.ui.screen.ipcalculator.components.SubnetInput
import inet.ipaddr.IPAddressString
import kotlin.text.compareTo


//@Composable
//fun IPCalculatorScreen(
//    modifier: Modifier = Modifier, viewModel: IpCalculatorViewModel = IpCalculatorViewModel.instance
//) {
//    var subnetMask by remember { mutableIntStateOf(24) }
//
//    LazyColumn(
//        modifier = modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//        ,
//        contentPadding = PaddingValues(16.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        item {
//            Text(
//                text = "Alamat IPv4",
//                style = MaterialTheme.typography.titleMedium,
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            IpInput(
//                onIpChanged = { address, subnet ->
//                    if(IPAddressString(address).isValid()) {
//                        viewModel.onIpInputChange(address)
//                    }
//                    viewModel.onSubnetMaskChange(subnet)
//                },
//                onSubnetChanged = { subnet ->
//                    subnetMask = subnet
//                    viewModel.onSubnetMaskChange(subnet)
//                },
//                subnet = subnetMask,
//            )
//        }
//        item {
//            SubnetInput(
//                onMaskSelected = {
//                    subnetMask = it
//                    viewModel.onSubnetMaskChange(it)
//                },
//                netMask = subnetMask,
//            )
//        }
//        item {
//            NetworkDetails(viewModel = viewModel)
//        }
//    }
//}

@Composable
fun IPCalculatorScreen(
    modifier: Modifier = Modifier,
    viewModel: IpCalculatorViewModel = IpCalculatorViewModel.instance
) {
    var subnetMask by remember { mutableIntStateOf(24) }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isTablet = this.maxWidth > 600.dp

        if (isTablet) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Alamat IPv4",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    IpInput(
                        onIpChanged = { address, subnet ->
                            if (IPAddressString(address).isValid()) {
                                viewModel.onIpInputChange(address)
                            }
                            viewModel.onSubnetMaskChange(subnet)
                        },
                        onSubnetChanged = { subnet ->
                            subnetMask = subnet
                            viewModel.onSubnetMaskChange(subnet)
                        },
                        subnet = subnetMask,
                    )
                    SubnetInput(
                        onMaskSelected = {
                            subnetMask = it
                            viewModel.onSubnetMaskChange(it)
                        },
                        netMask = subnetMask,
                    )
                }
                NetworkDetails(
                    modifier = Modifier.weight(1f),
                    viewModel = viewModel
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        text = "Alamat IPv4",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    IpInput(
                        onIpChanged = { address, subnet ->
                            if (IPAddressString(address).isValid()) {
                                viewModel.onIpInputChange(address)
                            }
                            viewModel.onSubnetMaskChange(subnet)
                        },
                        onSubnetChanged = { subnet ->
                            subnetMask = subnet
                            viewModel.onSubnetMaskChange(subnet)
                        },
                        subnet = subnetMask,
                    )
                }
                item {
                    SubnetInput(
                        onMaskSelected = {
                            subnetMask = it
                            viewModel.onSubnetMaskChange(it)
                        },
                        netMask = subnetMask,
                    )
                }
                item {
                    NetworkDetails(viewModel = viewModel)
                }
            }
        }
    }
}


@Composable
@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES or android.content.res.Configuration.UI_MODE_TYPE_UNDEFINED,
    device = "id:10.1in WXGA (Tablet)"
)
fun IPCalculatorScreenPreview() {
    BsTKJTheme {
        Surface {
            IPCalculatorScreen()
        }
    }
}