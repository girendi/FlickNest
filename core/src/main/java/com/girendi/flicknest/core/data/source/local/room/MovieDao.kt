package com.girendi.flicknest.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.girendi.flicknest.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMovie(): Flow<List<MovieEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)
    @Delete
    suspend fun deleteMovie(movie: MovieEntity)
    @Query("SELECT * FROM movie where id = :id")
    fun getFavoriteMovieById(id: Int): Flow<MovieEntity?>
}