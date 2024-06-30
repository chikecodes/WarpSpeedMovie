package com.chikemgbemena.warpspeedmovies.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chikemgbemena.warpspeedmovies.MainActivity
import com.chikemgbemena.warpspeedmovies.compose.MovieDetailScreen
import com.chikemgbemena.warpspeedmovies.compose.SearchMovieScreen
import com.chikemgbemena.warpspeedmovies.utils.Utils

@Composable
fun AppNavigation(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screens.SearchMovieScreen.route
    ) {

        composable(
            Screens.SearchMovieScreen.route,
        ) {
            SearchMovieScreen { movie ->
                navController.navigate(
                    Screens.MovieDetailScreen.withArgs(
                        args = mapOf(
                            MainActivity.MOVIE_IMDB_ID to movie.imdbId
                        )
                    )
                )
            }
        }
        composable(
            Screens.MovieDetailScreen.route +
                "?${MainActivity.MOVIE_IMDB_ID}={id}",
            arguments = listOf(
                navArgument(MainActivity.MOVIE_IMDB_ID) { nullable = true }
            ),
        ) {
            MovieDetailScreen(
                imdbId = Utils.getStringArg("id", it)
            ) {
                navController.popBackStack()
            }
        }
    }
}

const val ANIMATION_DURATION = 300
@ExperimentalAnimationApi
fun enterTransition(desRoute: String, initial: NavBackStackEntry): EnterTransition? {
    return when (initial.destination.route) {
        desRoute ->
            slideInHorizontally(
                initialOffsetX = { ANIMATION_DURATION },
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
        else -> null
    }
}

@ExperimentalAnimationApi
fun exitTransition(desRoute: String, target: NavBackStackEntry): ExitTransition? {
    return when (target.destination.route) {
        desRoute ->
            slideOutHorizontally(
                targetOffsetX = { -ANIMATION_DURATION },
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
        else -> null
    }
}

@ExperimentalAnimationApi
fun popEnterTransition(desRoute: String, initial: NavBackStackEntry): EnterTransition? {
    return when (initial.destination.route) {
        desRoute ->
            slideInHorizontally(
                initialOffsetX = { -ANIMATION_DURATION },
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
        else -> null
    }
}

@ExperimentalAnimationApi
fun popExitAnimation(desRoute: String, target: NavBackStackEntry): ExitTransition? {
    return when (target.destination.route) {
        desRoute ->
            slideOutHorizontally(
                targetOffsetX = { ANIMATION_DURATION },
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
        else -> null
    }
}
