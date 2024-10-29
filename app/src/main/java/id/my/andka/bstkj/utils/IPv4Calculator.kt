package id.my.andka.bstkj.utils

import id.my.andka.bstkj.model.*

class IPv4Calculator {
    companion object {
        fun calculate(ipAddress: String, cidr: Int): CalculationResult {
            return try {
                val octets = ipAddress.split(".").map { it.toIntOrNull() }
                if (octets.any { it == null || it !in 0..255 }) {
                    return CalculationResult.Failure(IPv4Error("IP_ADDRESS", "Invalid IP address"))
                }

                val baseIpNumeric = ipAddress.toNumericIP()
                val netmaskNumeric = calculateNetmaskFromCIDR(cidr)
                val totalIPs = (1L shl (32 - cidr)) - 1
                val baseNetworkIP = baseIpNumeric and netmaskNumeric
                val networkClass = determineNetworkClass(ipAddress)
                val totalSubnets = 1L shl (cidr - defaultClassBits(networkClass))

                val networkDetail = NetworkDetail(
                    networkClass,
                    totalSubnets,
                    totalIPs - 1,
                    baseNetworkIP.toSymbolicIP(),
                    (baseNetworkIP + 1).toSymbolicIP(),
                    (baseNetworkIP + totalIPs - 1).toInt().toSymbolicIP(),
                    (baseNetworkIP + totalIPs).toInt().toSymbolicIP(),
                    baseIpNumeric.toBinaryString(),
                    netmaskNumeric.toBinaryString(),
                    netmaskNumeric.inv().toBinaryString()
                )

                CalculationResult.Success(
                    IPv4Result(
                        "${baseNetworkIP.toSymbolicIP()}/$cidr",
                        netmaskNumeric.toSymbolicIP(),
                        totalIPs.coerceAtLeast(1),
                        netmaskNumeric.inv().toSymbolicIP(),
                        (baseNetworkIP + totalIPs).toInt().toSymbolicIP(),
                        "${(baseNetworkIP + 1).toSymbolicIP()} - ${
                            (baseNetworkIP + totalIPs - 1).toInt().toSymbolicIP()
                        }",
                        netmaskNumeric.toBinaryString(),
                        generateAvailableIPs(baseNetworkIP, totalIPs),
                        networkDetail,
                        generateSubnetDetails(baseNetworkIP, totalSubnets, totalIPs)
                    )
                )
            } catch (e: Exception) {
                CalculationResult.Failure(
                    IPv4Error(
                        "CALCULATION", "Calculation error: ${e.message}"
                    )
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
            baseNetworkIP: Int, totalSubnets: Long, hostsPerSubnet: Long
        ): List<SubnetDetail> {
            val subnetSize = hostsPerSubnet + 1
            return (0 until totalSubnets.coerceAtMost(64)).map {
                val subnetBaseIP = baseNetworkIP + (it * subnetSize)
                SubnetDetail(
                    subnetBaseIP.toInt().toSymbolicIP(),
                    (subnetBaseIP + 1).toInt().toSymbolicIP(),
                    (subnetBaseIP + subnetSize - 2).toInt().toSymbolicIP(),
                    (subnetBaseIP + subnetSize - 1).toInt().toSymbolicIP()
                )
            }
        }

        private fun String.toNumericIP() =
            split(".").fold(0) { acc, octet -> (acc shl 8) or (octet.toInt() and 0xff) }

        private fun Int.toSymbolicIP() =
            (24 downTo 0 step 8).joinToString(".") { ((this ushr it) and 0xff).toString() }

        private fun calculateNetmaskFromCIDR(cidr: Int) = -1 shl (32 - cidr)

        private fun Int.toBinaryString() =
            (31 downTo 0).joinToString("") { if ((this and (1 shl it)) != 0) "1" else "0" }
                .chunked(8).joinToString(".")

        private fun generateAvailableIPs(baseNetworkIP: Int, totalIPs: Long): List<IPRange> {
            val allIPs = (1 until totalIPs.coerceAtMost(1024) - 1).map {
                (baseNetworkIP + it).toInt().toSymbolicIP()
            }
            return when {
                totalIPs <= 4 -> listOf(IPRange("Usable Host IPs", allIPs))
                totalIPs <= 256 -> {
                    val segments = allIPs.chunked(allIPs.size / 4)
                    listOf(IPRange("Gateway Candidates", segments.getOrElse(0) { emptyList() }),
                        IPRange("General Purpose IPs",
                            segments.getOrElse(1) { emptyList() } + segments.getOrElse(2) { emptyList() }),
                        IPRange("Special Purpose", segments.getOrElse(3) { emptyList() })
                    )
                }

                else -> {
                    val segments = allIPs.chunked((allIPs.size * 0.1).toInt())
                    listOf(
                        IPRange("Infrastructure IPs", segments.getOrElse(0) { emptyList() }),
                        IPRange("DHCP Pool", segments.subList(1, 5).flatten()),
                        IPRange("Static Assignment", segments.subList(5, 9).flatten()),
                        IPRange("Reserved", segments.lastOrNull() ?: emptyList())
                    )
                }
            }
        }
    }
}