package id.my.andka.bstkj.utils

import android.util.Log
import id.my.andka.bstkj.model.CalculationResult
import id.my.andka.bstkj.model.NetworkDetail
import inet.ipaddr.IPAddress
import inet.ipaddr.IPAddressString
import kotlin.math.pow

fun getIPAddressInfo(ipAddressString: String): CalculationResult {
    try {
        Log.d("IPCalculator", "Calculating IP Address Info for $ipAddressString")
        val ipAddress = IPAddressString(ipAddressString).address

        if(!ipAddress.isIPAddress || ipAddress == null) {
            return CalculationResult.Failure("Invalid IP Address")
        }

        if(!ipAddress.isIPv4) {
            return CalculationResult.Failure("IPv6 is not supported")
        }

        val subnetMask = ipAddress.networkPrefixLength

        val ipInfo = NetworkDetail(
            ipAddress = ipAddress.toCanonicalHostName().toNormalizedString().removeSlash(),
            ipAddressBinary = ipAddress.toFormattedBinaryString(),
            ipClass = getIPClass(ipAddress),
            subnetMask = ipAddress.getNetworkMask().toCanonicalString().removeSlash(),
            subnetMaskBinary = ipAddress.getNetworkMask().toFormattedBinaryString(),
            wildcardMask = ipAddress.networkMask.toString().removeSlash().split(".").reversed().joinToString("."),
            firstHost = ipAddress.lower.toCanonicalString().removeSlash(),
            lastHost = ipAddress.upper.toCanonicalString().removeSlash(),
            effectiveSubnets = 2.0.pow((32 - subnetMask).toDouble()).toInt(),
            effectiveHostsPerSubnet = (2.0.pow((32 - subnetMask).toDouble()) - 2).toInt(),
            broadcastAddress = ipAddress.toMaxHost().toString().removeSlash(),
        )

        return CalculationResult.Success(ipInfo)
    } catch (e: Exception) {
        Log.e("IPCalculator", "Error calculating IP Address Info", e)
        return CalculationResult.Failure(e.message ?: "Unknown error")
    }
}

private fun IPAddress.toFormattedBinaryString(): String {
    return this.toBinaryString().replace("(.{8})".toRegex(), "$1 ").trim()
}

private fun String.removeSlash(): String {
    return if(this.contains("/")) {
        this.substringBefore("/")
    }else {
        this
    }
}

private fun getIPClass(ipAddress: IPAddress): String {
    if (ipAddress.isIPv4) {
        val firstOctet = ipAddress.toIPv4().segments[0].value.toInt()
        return when (firstOctet) {
            in 0..127 -> "Class A"
            in 128..191 -> "Class B"
            in 192..223 -> "Class C"
            in 224..239 -> "Class D"
            else -> "Class E"
        }
    }
    return "N/A (IPv6)"
}
