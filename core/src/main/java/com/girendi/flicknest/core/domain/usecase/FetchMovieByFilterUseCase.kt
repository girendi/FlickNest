package com.girendi.flicknest.core.domain.usecase

import com.girendi.flicknest.core.data.source.remote.response.ListMovieResponse
import com.girendi.flicknest.core.data.Result
import com.girendi.flicknest.core.domain.repository.MovieRepository

class FetchMovieByFilterUseCase(private val movieRepository: MovieRepository) {
    suspend fun fetchMovieByGenre(page: Int, genreId: String): Result<ListMovieResponse> =
        movieRepository.fetchMovieByGenre(page, genreId)
    suspend fun fetchPopularBasedMovies(page: Int): Result<ListMovieResponse> =
        movieRepository.fetchPopularMovies(page)

    suspend fun fetchTrendingBasedMovies(page: Int): Result<ListMovieResponse> =
        movieRepository.fetchTrendingMovies(page)
}