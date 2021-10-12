package amaterek.movie.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface FavoriteMoviesDao {

    @Query("SELECT * FROM favorite_movies")
    suspend fun getAll(): List<FavoriteMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(favoriteMovie: FavoriteMovieEntity)

    @Query("DELETE FROM favorite_movies WHERE id=:id")
    suspend fun deleteById(id: Long)
}
