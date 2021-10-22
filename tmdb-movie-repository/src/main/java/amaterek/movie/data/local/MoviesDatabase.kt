package amaterek.movie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteMovieEntity::class], version = 1, exportSchema = false)
internal abstract class MoviesDatabase : RoomDatabase() {

    internal abstract fun getFavoriteMoviesDao(): FavoriteMoviesDao
}
