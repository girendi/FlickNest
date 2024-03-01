package com.girendi.flicknest.domain.usecase

import com.girendi.flicknest.data.model.Movie
import com.girendi.flicknest.data.model.Review
import com.girendi.flicknest.data.model.Video
import com.girendi.flicknest.domain.Result
import com.girendi.flicknest.domain.repository.MovieRepository

class FetchDetailMovieUseCase(private val movieRepository: MovieRepository) {
    suspend fun fetchMovieDetail(movieId: Int): Result<Movie> =
        movieRepository.fetchMovieDetail(movieId)

    suspend fun fetchMovieReviews(page: Int, movieId: Int): Result<List<Review>> =
        movieRepository.fetchMovieReviews(page, movieId)

    suspend fun fetchMovieVideos(movieId: Int): Result<List<Video>> =
        movieRepository.fetchMovieVideos(movieId)
}