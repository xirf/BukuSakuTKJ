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
            wildcardMask = ipAddress.networkMask.toString().removeSlash().toWildcard(),
            firstHost = ipAddress.lower.toCanonicalString().removeSlash(),
            lastHost = ipAddress.toIPv4().toBroadcastAddress().subtract(1),
            effectiveSubnets = 2.0.pow((32 - subnetMask).toDouble()).toInt(),
            effectiveHostsPerSubnet = (2.0.pow((32 - subnetMask).toDouble()) - 2).toInt(),
            broadcastAddress = ipAddress.toIPv4().toBroadcastAddress().withoutPrefixLength().toString()
        )

        return CalculationResult.Success(ipInfo)
    } catch (e: Exception) {
        Log.e("IPCalculator", "Error calculating IP Address Info", e)
        return CalculationResult.Failure(e.message ?: "Unknown error")
    }
}

private fun IPAddress.subtract(count: Int): String {
    return this.withoutPrefixLength().toString().split(".").mapIndexed { index, octet ->
        if(index == 3) {
            (octet.toInt() - count).toString()
        }else {
            octet
        }
    }.joinToString(".")
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

private  fun String.toWildcard(): String {
    return this.split("."). map { octet -> 255 - octet.toInt() }.joinToString(".")
}

private fun getIPClass(ipAddress: IPAddress): String {
    if (ipAddress.isIPv4) {
        val firstOctet = ipAddress.toIPv4().segments[0].value.toInt()
        return when {
            firstOctet == 10 -> "Class A (Private)"
            firstOctet == 172 && ipAddress.toIPv4().segments[1].value.toInt() in 16..31 -> "Class B (Private)"
            firstOctet == 192 && ipAddress.toIPv4().segments[1].value.toInt() == 168 -> "Class C (Private)"
            firstOctet in 0..127 -> "Class A (Public)"
            firstOctet in 128..191 -> "Class B (Public)"
            firstOctet in 192..223 -> "Class C (Public)"
            firstOctet in 224..239 -> "Class D (Special)"
            firstOctet in 240..255 -> "Class E (Special)"
            else -> "N/A"
        }
    }
    return "N/A (IPv6)"
}
