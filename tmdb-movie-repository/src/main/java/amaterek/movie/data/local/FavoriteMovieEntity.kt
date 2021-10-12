package amaterek.movie.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
internal data class FavoriteMovieEntity(
    @PrimaryKey val id: Long,
)
