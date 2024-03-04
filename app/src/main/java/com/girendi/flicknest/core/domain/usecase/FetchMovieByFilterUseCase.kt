package com.girendi.flicknest.core.domain.usecase

import com.girendi.flicknest.core.data.response.ListMovieResponse
import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.domain.Result
import com.girendi.flicknest.core.domain.repository.MovieRepository

class FetchMovieByFilterUseCase(private val movieRepository: MovieRepository) {
    suspend fun fetchMovieByGenre(page: Int, genreId: String): Result<ListMovieResponse> =
        movieRepository.fetchMovieByGenre(page, genreId)
    suspend fun fetchPopularBasedMovies(page: Int): Result<ListMovieResponse> =
        movieRepository.fetchPopularMovies(page)

    suspend fun fetchTrendingBasedMovies(page: Int): Result<ListMovieResponse> =
        movieRepository.fetchTrendingMovies(page)
}