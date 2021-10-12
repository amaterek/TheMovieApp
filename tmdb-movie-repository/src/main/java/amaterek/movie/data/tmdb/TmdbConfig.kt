package amaterek.movie.data.tmdb

import amaterek.base.locale.LocaleProvider

data class TmdbConfig(
    val baseUrl: String,
    val basePosterImageUrl: String,
    val baseBackdropImageUrl: String,
    val apiKey: String,
)
