package com.girendi.flicknest.domain.usecase

import com.girendi.flicknest.data.model.Genre
import com.girendi.flicknest.domain.Result
import com.girendi.flicknest.domain.repository.GenreRepository

class FetchGenreUseCase(private val genreRepository: GenreRepository) {
    suspend fun execute(): Result<List<Genre>> = genreRepository.fetchGenres()
}