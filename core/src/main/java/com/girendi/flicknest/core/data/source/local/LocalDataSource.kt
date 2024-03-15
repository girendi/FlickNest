package com.girendi.flicknest.core.data.source.local

import com.girendi.flicknest.core.data.source.local.entity.MovieEntity
import com.girendi.flicknest.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {
    fun getAllMovie(): Flow<List<MovieEntity>> = movieDao.getAllMovie()
    suspend fun insertMovie(movieEntity: MovieEntity) = movieDao.insertMovie(movieEntity)
    suspend fun deleteMovie(movieEntity: MovieEntity) = movieDao.deleteMovie(movieEntity)
    fun getFavoriteMovieById(id: Int): Flow<MovieEntity?> = movieDao.getFavoriteMovieById(id)
}