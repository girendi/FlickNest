package com.girendi.flicknest.core.data.source.remote.repository

import com.girendi.flicknest.core.data.source.local.LocalDataSource
import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.domain.repository.FavoriteRepository
import com.girendi.flicknest.core.utils.DataMapperMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(
    private val localDataSource: LocalDataSource
): FavoriteRepository {
    override fun getAllFavorite(): Flow<List<Movie>> =
        localDataSource.getAllMovie().map {
            DataMapperMovie.mapEntityToDomain(it)
        }
}