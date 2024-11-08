package id.my.andka.bstkj.utils

import id.my.andka.bstkj.model.*
import inet.ipaddr.IPAddressString

class IPv4Calculator {
    companion object {
        fun calculate(ipAddress: String, cidr: Int): CalculationResult {
            return try {
                // Parse IP and CIDR using IPAddressString
                val ipString = "$ipAddress/$cidr"
                val ipAddressString = IPAddressString(ipString).address
                    ?: return CalculationResult.Failure(
                        IPv4Error(
                            "IP_ADDRESS",
                            "Invalid IP address"
                        )
                    )

                // Validate that it's an IPv4 address
                if (!ipAddressString.isIPv4) {
                    return CalculationResult.Failure(IPv4Error("IP_ADDRESS", "Not an IPv4 address"))
                }

                val ipv4Address = ipAddressString.toIPv4()`

                // Network properties
                val networkClass = determineNetworkClass(ipv4Address.toNormalizedString())
                val totalIPs =
                    ipv4Address.networkPrefixLength.toLong().let { 1L shl ((32 - it).toInt()) }
                val totalSubnets = 1L shl (cidr - defaultClassBits(networkClass))

                // Calculate Network and Broadcast Addresses
                val networkAddress = ipv4Address.toNetworkAddress()
                val broadcastAddress = ipv4Address.toBroadcastAddress()

                // Network detail encapsulation
                val networkDetail = NetworkDetail(
                    networkClass = networkClass,
                    totalSubnets = totalSubnets,
                    usableHosts = totalIPs - 2, // Exclude network and broadcast
                    networkAddress = networkAddress.toNormalizedString(),
                    firstHostAddress = networkAddress.increment(1).toNormalizedString(),
                    lastHostAddress = broadcastAddress.toBroadcastAddress().toNormalizedString(),
                    broadcastAddress = broadcastAddress.toNormalizedString(),
                    binaryNetworkAddress = toBinaryString(networkAddress.toNormalizedString()),
                    binaryNetmask = toBinaryString(ipv4Address.networkPrefixLength),
                    binaryWildcardMask = toBinaryString(ipv4Address.networkPrefixLength.inv())
                )

                CalculationResult.Success(
                    IPv4Result(
                        cidr = "$ipAddress/$cidr",
                        netmask = toBinaryString(ipv4Address.networkPrefixLength),
                        numberOfHosts = totalIPs,
                        wildcardMask = toBinaryString(ipv4Address.networkPrefixLength.inv()),
                        broadcastAddress = broadcastAddress.toNormalizedString(),
                        hostAddressRange = "${networkAddress.increment(1).toNormalizedString()} - ${
                            broadcastAddress.toBroadcastAddress().toNormalizedString()
                        }",
                        binaryNetmask = toBinaryString(ipv4Address.networkPrefixLength),
                        availableIPs = generateAvailableIPs(IPAddressString(ipString), totalIPs),
                        networkDetail = networkDetail,
                        subnetDetails = generateSubnetDetails(
                            IPAddressString(ipString),
                            totalSubnets,
                            totalIPs
                        )
                    )
                )
            } catch (e: Exception) {
                CalculationResult.Failure(
                    IPv4Error("CALCULATION", "Calculation error: ${e.message}")
                )
            }
        }

        private fun determineNetworkClass(ip: String): String {
            return when (ip.substringBefore(".").toInt()) {
                in 1..126 -> "Class A"
                in 128..191 -> "Class B"
                in 192..223 -> "Class C"
                in 224..239 -> "Class D (Multicast)"
                in 240..255 -> "Class E (Reserved)"
                else -> "Special Purpose"
            }
        }

        private fun defaultClassBits(networkClass: String) = when (networkClass) {
            "Class A" -> 8
            "Class B" -> 16
            "Class C" -> 24
            else -> 0
        }

        private fun generateSubnetDetails(
            baseNetworkIP: IPAddressString, totalSubnets: Long, hostsPerSubnet: Long
        ): List<SubnetDetail> {
            val subnetList = mutableListOf<SubnetDetail>()
            val subnetSize = hostsPerSubnet + 2
            for (i in 0 until totalSubnets) {
                val subnetBaseIP = baseNetworkIP.toAddress().increment(i * subnetSize)
                subnetList.add(
                    SubnetDetail(
                        network = subnetBaseIP.toNormalizedString(),
                        firstHost = subnetBaseIP.increment(1).toNormalizedString(),
                        lastHost = subnetBaseIP.increment(subnetSize - 2).toNormalizedString(),
                        broadcast = subnetBaseIP.increment(subnetSize - 1).toNormalizedString()
                    )
                )
            }
            return subnetList
        }

        private fun generateAvailableIPs(
            baseNetworkIP: IPAddressString,
            totalIPs: Long
        ): List<IPRange> {
            val usableIPs = (1 until totalIPs.coerceAtMost(1024) - 1).map {
                baseNetworkIP.toAddress().increment(it.toInt().toLong()).toNormalizedString()
            }
            return listOf(IPRange("Available IPs", usableIPs))
        }

        private fun toBinaryString(ip: String): String {
            return ip.split(".").joinToString(".") {
                it.toInt().toString(2).padStart(8, '0')
            }
        }

        private fun toBinaryString(prefixLength: Int): String {
            return "1".repeat(prefixLength).padEnd(32, '0').chunked(8).joinToString(".")
        }
    }
}