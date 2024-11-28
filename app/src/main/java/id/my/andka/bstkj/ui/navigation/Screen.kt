package id.my.andka.bstkj.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object IpCalculator: Screen("ip_calculator")
    object NumberSystem: Screen("number_system")
    object Tos: Screen("tos")
    object PrivacyPolicy: Screen("privacy_policy")
    object Detail: Screen("detail/{slug}?title={title}") {
        fun createRoute(slug: String, title: String): String {
            return "detail/$slug?title=$title"
        }
    }
}