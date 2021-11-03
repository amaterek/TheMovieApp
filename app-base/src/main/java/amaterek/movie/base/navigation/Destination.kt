package amaterek.movie.base.navigation

open class Destination(
    internal val baseRoute: String,
    internal val argumentName: String? = null
) {
    val route = argumentName?.let { "$baseRoute/{$it}" } ?: baseRoute

    fun routeFor(argumentValue: String) = "$baseRoute/$argumentValue"
}