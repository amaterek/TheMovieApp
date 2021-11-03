package amaterek.movie.base.navigation

import amaterek.base.log.Log
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    startDestination: Destination,
    builder: NavigationScope.() -> Unit
) {
    Log.v("ComposeRender", "Navigation")

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        route = "/",
    ) {
        Log.v("ComposeRender", "Navigation.NavGraphBuilder")
        NavigationScope(this, navController).builder()
    }
}

class NavigationScope(
    val navGraphBuilder: NavGraphBuilder,
    val navController: NavController,
)

fun <T : Destination> NavigationScope.destination(
    destination: T,
    content: @Composable NavBackStackEntry.() -> Unit
) {
    navGraphBuilder.composable(
        route = destination.route,
        arguments = destination.argumentName?.let {
            listOf(
                navArgument(name = it) { type = NavType.StringType }
            )
        } ?: emptyList(),
        content = content
    )
}

fun NavigationScope.navigateTo(direction: Direction) {
    with(direction) {
        val route = argument?.let { destination.routeFor(it) } ?: destination.route
        navController.navigate(route) {
            popupTo?.let { destination ->
                popUpTo(destination.route) { inclusive = direction.inclusive }
            }
        }
    }
}

fun NavBackStackEntry.getArgumentFor(destination: Destination): String? =
    destination.argumentName?.let { arguments?.getString(it) }
