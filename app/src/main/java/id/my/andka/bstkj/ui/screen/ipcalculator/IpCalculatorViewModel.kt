package id.my.andka.bstkj.ui.screen.ipcalculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.my.andka.bstkj.model.CalculationResult
import id.my.andka.bstkj.model.NetworkDetail
import id.my.andka.bstkj.utils.IPv4Calculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IpCalculatorViewModel : ViewModel() {
    private val _ipInput = MutableStateFlow("")
    val ipInput: StateFlow<String> = _ipInput

    private val _subnetMask = MutableStateFlow(24)
    val subnetMask: StateFlow<Int> = _subnetMask

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
        viewModelScope.launch {
            _networkDetails.value = IPv4Calculator.calculate(
                ipAddress = _ipInput.value,
                cidr = _subnetMask.value
            )
        }
    }

    companion object {
        val instance by lazy { IpCalculatorViewModel() }
    }
}