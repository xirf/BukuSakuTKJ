package id.my.andka.bstkj.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object IpCalculator: Screen("ip_calculator")
}