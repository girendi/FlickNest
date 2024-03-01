package com.girendi.flicknest.domain.usecase

import com.girendi.flicknest.data.model.Movie
import com.girendi.flicknest.data.model.Video
import com.girendi.flicknest.domain.Result
import com.girendi.flicknest.domain.repository.MovieRepository

class FetchMovieUseCase(private val movieRepository: MovieRepository) {
    suspend fun fetchTrendingMovies(page: Int): Result<List<Movie>> =
        movieRepository.fetchTrendingMovies(page)
    suspend fun fetchPopularMovies(page: Int): Result<List<Movie>> =
        movieRepository.fetchPopularMovies(page)
    suspend fun fetchMostTrendingMovies(page: Int): Result<Movie> =
        movieRepository.fetchMostTrendingMovies(page)
    suspend fun fetchMovieVideos(movieId: Int): Result<List<Video>> =
        movieRepository.fetchMovieVideos(movieId)
}