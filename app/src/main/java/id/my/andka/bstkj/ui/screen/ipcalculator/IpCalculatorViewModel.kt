package id.my.andka.bstkj.ui.screen.ipcalculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.my.andka.bstkj.model.CalculationResult
import id.my.andka.bstkj.utils.getIPAddressInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class IpCalculatorViewModel : ViewModel() {
    private val _ipInput = MutableStateFlow("192.168.0.1")
    private val _subnetMask = MutableStateFlow(24)

    private val _networkDetails = MutableStateFlow<CalculationResult>(CalculationResult.Idle)
    val networkDetails: StateFlow<CalculationResult> = _networkDetails

    fun onIpInputChange(newIp: String) {
        _ipInput.value = newIp
        updateNetworkDetails()
    }

    fun onSubnetMaskChange(newMask: Int) {
        _subnetMask.value = newMask
        updateNetworkDetails()
    }

    private fun updateNetworkDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            _networkDetails.value = getIPAddressInfo(
                    ipAddressString = "${_ipInput.value}/${_subnetMask.value}",
                )
        }
    }

    companion object {
        val instance by lazy { IpCalculatorViewModel() }
    }
}