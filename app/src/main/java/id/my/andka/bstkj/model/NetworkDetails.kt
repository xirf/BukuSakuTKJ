package id.my.andka.bstkj.model


sealed class CalculationResult {
    object Idle : CalculationResult()
    data class Success(val result: IPv4Result) : CalculationResult()
    data class Failure(val error: IPv4Error) : CalculationResult()
}

data class IPv4Error(
    val errorOn: String,
    val message: String
)

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
    val networkClass: String,
    val totalSubnets: Long,
    val hostsPerSubnet: Long,
    val networkAddress: String,
    val firstHost: String,
    val lastHost: String,
    val broadcastAddress: String,
    val ipBinary: String,
    val subnetBinary: String,
    val wildcardBinary: String
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
