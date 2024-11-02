// NavigationTransitions.kt
package id.my.andka.bstkj.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object NavigationTransitions {
    private const val ANIMATION_DURATION = 300

    val enterTransition: AnimatedContentTransitionScope<*>.() -> EnterTransition = {
        slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(ANIMATION_DURATION)
        )
    }

    val exitTransition: AnimatedContentTransitionScope<*>.() -> ExitTransition = {
        slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(ANIMATION_DURATION)
        )
    }

    val popEnterTransition: AnimatedContentTransitionScope<*>.() -> EnterTransition = {
        slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(ANIMATION_DURATION)
        )
    }

    val popExitTransition: AnimatedContentTransitionScope<*>.() -> ExitTransition = {
        slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(ANIMATION_DURATION)
        )
    }
}