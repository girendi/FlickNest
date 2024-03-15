package com.girendi.flicknest.core.domain.usecase

import com.girendi.flicknest.core.data.source.remote.response.ListGenresResponse
import com.girendi.flicknest.core.data.Result
import com.girendi.flicknest.core.domain.repository.GenreRepository

class FetchGenreUseCase(private val genreRepository: GenreRepository) {
    suspend fun fetchGenreMovie(): Result<ListGenresResponse> =
        genreRepository.fetchGenreMovie()
}