package id.my.andka.bstkj

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.my.andka.bstkj.ui.navigation.Screen
import id.my.andka.bstkj.ui.screen.home.HomeScreen
import id.my.andka.bstkj.ui.theme.BsTKJTheme


@Composable
fun BsTKJContent(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val whileListedRoutes = listOf(
        Screen.Home.route
    )

    Scaffold(
        modifier = modifier,
        bottomBar = { if (currentRoute in whileListedRoutes) BottomBar(navController = navController) },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Home
            composable(Screen.Home.route) {
                HomeScreen()
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    // Bottom bar
}

// preview
@Composable
@Preview(device = "id:pixel_9_pro_xl", showSystemUi = true, backgroundColor = 0xF0F1F2)
fun ToolCardPreview() {
    BsTKJTheme {
        BsTKJContent()
    }
}