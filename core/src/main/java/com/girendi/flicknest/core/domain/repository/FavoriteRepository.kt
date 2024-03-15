package com.girendi.flicknest.core.domain.repository

import com.girendi.flicknest.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAllFavorite(): Flow<List<Movie>>
}