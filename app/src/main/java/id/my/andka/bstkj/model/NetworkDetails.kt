package id.my.andka.bstkj.model

sealed class CalculationResult {
    object Idle : CalculationResult()
    data class Success(val result: NetworkDetail) : CalculationResult()
    data class Failure(val error: String) : CalculationResult()
}

data class SubnetDetail(
    val network: String,
    val firstHost: String,
    val lastHost: String,
    val broadcast: String
)

data class IPRange(
    val description: String,
    val addresses: List<String>
)

data class NetworkDetail(
    val ipAddress: String,
    val ipAddressBinary: String,
    val ipClass: String,
    val subnetMask: String,
    val broadcastAddress: String,
    val subnetMaskBinary: String,
    val wildcardMask: String,
    val firstHost: String,
    val lastHost: String,
    val effectiveSubnets: Int,
    val effectiveHostsPerSubnet: Int
)

data class IPv4Result(
    val cidr: String,
    val netmask: String,
    val numberOfHosts: Long,
    val wildcardMask: String,
    val broadcastAddress: String,
    val hostAddressRange: String,
    val binaryNetmask: String,
    val availableIPs: List<IPRange>,
    val networkDetail: NetworkDetail,
    val subnetDetails: List<SubnetDetail>
)
