package id.my.andka.bstkj

import android.app.Application
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import dagger.hilt.android.HiltAndroidApp
import id.my.andka.bstkj.ui.navigation.Screen
import id.my.andka.bstkj.ui.navigation.composableWithTransitions
import id.my.andka.bstkj.ui.screen.detail.DetailScreen
import id.my.andka.bstkj.ui.screen.home.HomeScreen
import id.my.andka.bstkj.ui.screen.ipcalculator.IPCalculatorScreen
import id.my.andka.bstkj.ui.screen.numbersystem.NumberSystemScreen
import id.my.andka.bstkj.ui.screen.other.PrivacyPolicyScreen
import id.my.andka.bstkj.ui.screen.other.TosScreen
import id.my.andka.bstkj.ui.theme.BsTKJTheme

@HiltAndroidApp
class BsTKJApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}


@Composable
fun BsTKJContent(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composableWithTransitions(Screen.Home.route) {
                HomeScreen(navController = navController, onArticleClick = { slug, title ->
                    navController.navigate(Screen.Detail.createRoute(slug, title))
                })
            }

            composableWithTransitions(
                Screen.Detail.route,
                arguments = listOf(navArgument("slug") { type = NavType.StringType },
                    navArgument("title") { type = NavType.StringType })
            ) {
                val slug = it.arguments?.getString("slug")
                val title = it.arguments?.getString("title")
                ScreenWrapper(
                    navController = navController,
                    title = title ?: slug ?: "",
                ) {
                    DetailScreen(
                        slug = slug ?: "", modifier = modifier.padding(it)
                    )
                }
            }


            composableWithTransitions(Screen.IpCalculator.route) {
                ScreenWrapper(
                    navController = navController,
                    title = stringResource(R.string.ip_calculator),
                ) {
                    IPCalculatorScreen(modifier = modifier.padding(it))
                }
            }

            composableWithTransitions(Screen.Tos.route) {
                ScreenWrapper(
                    navController = navController,
                    title = stringResource(R.string.tos),
                ) {
                    TosScreen(modifier = modifier.padding(it))
                }
            }

            composableWithTransitions(Screen.PrivacyPolicy.route) {
                ScreenWrapper(
                    navController = navController,
                    title = stringResource(R.string.privacy_policy),
                ) {
                    PrivacyPolicyScreen(modifier = modifier.padding(it))
                }
            }

            composableWithTransitions(Screen.NumberSystem.route) {
                ScreenWrapper(
                    navController = navController,
                    title = stringResource(R.string.number_system),
                ) {
                    NumberSystemScreen(modifier = modifier.padding(it))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenWrapper(
    navController: NavController, title: String, content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                title = title,
            )
        }, contentWindowInsets = WindowInsets(
            top = 0.dp, left = 0.dp, right = 0.dp, bottom = 0.dp
        ), content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    navController: NavController,
    title: String,
) {
    TopAppBar(title = {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
    }, navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Outlined.ArrowBackIosNew, contentDescription = "Back")
        }
    }, actions = {
        Spacer(modifier = Modifier.width(48.dp))
    }

    )
}

// preview
@Composable
@Preview(device = "id:pixel_9_pro_xl", showSystemUi = true, backgroundColor = 0xF0F1F2)
fun ToolCardPreview() {
    BsTKJTheme {
        BsTKJContent()
    }
}