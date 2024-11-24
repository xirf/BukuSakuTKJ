package id.my.andka.bstkj

import android.app.Application
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.navigation.compose.NavHost
import dagger.hilt.android.HiltAndroidApp
import id.my.andka.bstkj.ui.navigation.Screen
import id.my.andka.bstkj.ui.navigation.composableWithTransitions
import id.my.andka.bstkj.ui.screen.home.HomeScreen
import id.my.andka.bstkj.ui.screen.ipcalculator.IPCalculatorScreen
import id.my.andka.bstkj.ui.screen.numbersystem.NumberSystemScreen
import id.my.andka.bstkj.ui.theme.BsTKJTheme



@HiltAndroidApp
class BsTKJApp : Application()


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
                HomeScreen(navController = navController)
            }

            composableWithTransitions(Screen.IpCalculator.route) {
                ScreenWrapper(
                    navController = navController,
                    title = stringResource(R.string.ip_calculator),
                ) {
                    IPCalculatorScreen(modifier = modifier.padding(it))
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
    navController: NavController,
    title: String,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                title = title,
            )
        },
        contentWindowInsets = WindowInsets(
            top = 0.dp,
            left = 0.dp,
            right = 0.dp,
            bottom = 0.dp
        ),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    navController: NavController,
    title: String,
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Outlined.ArrowBackIosNew, contentDescription = "Back")
            }
        },
        actions = {
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