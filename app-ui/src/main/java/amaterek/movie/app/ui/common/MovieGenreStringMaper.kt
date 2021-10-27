package amaterek.movie.app.ui.common

import amaterek.movie.app.ui.R
import amaterek.movie.domain.model.MovieGenre

fun getMovieGenreStringId(genre: MovieGenre) = when (genre) {
    MovieGenre.ACTION -> R.string.movie_genre_action
    MovieGenre.ADVENTURE -> R.string.movie_genre_adventure
    MovieGenre.ANIMATION -> R.string.movie_genre_animation
    MovieGenre.COMEDY -> R.string.movie_genre_comedy
    MovieGenre.CRIME -> R.string.movie_genre_crime
    MovieGenre.DOCUMENTARY -> R.string.movie_genre_documentary
    MovieGenre.DRAMA -> R.string.movie_genre_drama
    MovieGenre.FAMILY -> R.string.movie_genre_family
    MovieGenre.FANTASY -> R.string.movie_genre_fantasy
    MovieGenre.HISTORY -> R.string.movie_genre_history
    MovieGenre.HORROR -> R.string.movie_genre_horror
    MovieGenre.KIDS -> R.string.movie_genre_kids
    MovieGenre.MUSIC -> R.string.movie_genre_music
    MovieGenre.MYSTERY -> R.string.movie_genre_mystery
    MovieGenre.NEWS -> R.string.movie_genre_news
    MovieGenre.POLITICS -> R.string.movie_genre_politics
    MovieGenre.REALITY -> R.string.movie_genre_reality
    MovieGenre.ROMANCE -> R.string.movie_genre_romance
    MovieGenre.SCIENCE_FICTION -> R.string.movie_genre_science_fiction
    MovieGenre.SOAP -> R.string.movie_genre_soap
    MovieGenre.TALK -> R.string.movie_genre_talk
    MovieGenre.THRILLER -> R.string.movie_genre_thriller
    MovieGenre.TV_MOVIE -> R.string.movie_genre_tv_movie
    MovieGenre.WAR -> R.string.movie_genre_war
    MovieGenre.WESTERN -> R.string.movie_genre_western
}