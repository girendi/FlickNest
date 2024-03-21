package com.girendi.flicknest.core.domain.usecase

import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class FetchFavoriteUseCase(
    private val favoriteRepository: FavoriteRepository
) {
    fun getAllFavorite(): Flow<List<Movie>> =
        favoriteRepository.getAllFavorite()
}