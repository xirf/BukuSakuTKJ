package id.my.andka.bstkj.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

// NavigationTransitions.kt
fun NavGraphBuilder.composableWithTransitions(
    route: String,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = NavigationTransitions.enterTransition,
        exitTransition = NavigationTransitions.exitTransition,
        popEnterTransition = NavigationTransitions.popEnterTransition,
        popExitTransition = NavigationTransitions.popExitTransition,
        content = content
    )
}