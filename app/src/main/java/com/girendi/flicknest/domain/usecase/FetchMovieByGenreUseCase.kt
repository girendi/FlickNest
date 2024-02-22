package com.girendi.flicknest.domain.usecase

import com.girendi.flicknest.data.model.Movie
import com.girendi.flicknest.domain.Result
import com.girendi.flicknest.domain.repository.MovieByGenreRepository

class FetchMovieByGenreUseCase(private val movieByGenreRepository: MovieByGenreRepository) {
    suspend operator fun invoke(page: Int, genreId: String): Result<List<Movie>> {
        return movieByGenreRepository.fetchMovieByGenre(page, genreId)
    }
}