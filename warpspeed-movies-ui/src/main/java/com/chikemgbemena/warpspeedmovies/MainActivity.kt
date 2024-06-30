package com.chikemgbemena.warpspeedmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.chikemgbemena.warpspeedmovies.navigation.AppNavigation
import com.chikemgbemena.warpspeedmovies.ui.theme.WarpspeedMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val MOVIE_IMDB_ID = "movie_imdb_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WarpspeedMoviesTheme {
                WarpSpeedMovieApp()
            }
        }
    }
}

@Composable
fun WarpSpeedMovieApp() {
    val navController = rememberNavController()
    AppNavigation(navController)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WarpspeedMoviesTheme {
        Greeting("Android")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}