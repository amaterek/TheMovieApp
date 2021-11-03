package amaterek.movie.base.navigation

data class Direction(
    val destination: Destination,
    val popupTo: Destination? = null,
    val inclusive: Boolean = false,
    val argument: String? = null
)