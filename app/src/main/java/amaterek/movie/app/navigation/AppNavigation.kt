package amaterek.movie.app.navigation

import amaterek.movie.app.ui.moviedetails.MovieDetailsScreen
import amaterek.movie.app.ui.movielist.MovieListScreen
import amaterek.movie.app.ui.splash.SplashScreen
import amaterek.movie.base.navigation.Navigation
import amaterek.movie.base.navigation.destination
import amaterek.movie.base.navigation.navigateTo
import androidx.compose.runtime.Composable

@Composable
fun AppNavigation() {
    Navigation(startDestination = SplashDestination) {
        destination(SplashDestination) {
            SplashScreen(
                onSplashFinished = { navigateTo(SplashDestination.movieListDirection) },
            )
        }
        destination(MovieListDestination) {
            MovieListScreen(
                onMovieClick = { movie ->
                    navigateTo(MovieListDestination.movieDetailsDirection(movie))
                }
            )
        }
        destination(MovieDetailsDestination) {
            val movieId = MovieDetailsDestination.getMovieId(this)
                ?: throw IllegalArgumentException()
            MovieDetailsScreen(movieId)
        }
    }
}