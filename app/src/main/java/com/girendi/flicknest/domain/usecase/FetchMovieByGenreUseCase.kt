package com.girendi.flicknest.domain.usecase

import com.girendi.flicknest.data.model.Movie
import com.girendi.flicknest.domain.Result
import com.girendi.flicknest.domain.repository.MovieRepository

class FetchMovieByGenreUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(page: Int, genreId: String): Result<List<Movie>> {
        return movieRepository.fetchMovieByGenre(page, genreId)
    }
    suspend fun fetchPopularBasedMovies(page: Int): Result<List<Movie>> =
        movieRepository.fetchPopularMovies(page)

    suspend fun fetchTrendingBasedMovies(page: Int): Result<List<Movie>> =
        movieRepository.fetchTrendingMovies(page)
}