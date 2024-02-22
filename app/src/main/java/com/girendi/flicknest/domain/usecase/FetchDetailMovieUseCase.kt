package com.girendi.flicknest.domain.usecase

import com.girendi.flicknest.data.model.Movie
import com.girendi.flicknest.data.model.Review
import com.girendi.flicknest.data.model.Video
import com.girendi.flicknest.domain.Result
import com.girendi.flicknest.domain.repository.MovieByGenreRepository

class FetchDetailMovieUseCase(private val movieByGenreRepository: MovieByGenreRepository) {
    suspend fun fetchMovieDetail(movieId: Int): Result<Movie> {
        return movieByGenreRepository.fetchMovieDetail(movieId)
    }

    suspend fun fetchMovieReviews(page: Int, movieId: Int): Result<List<Review>> {
        return movieByGenreRepository.fetchMovieReviews(page, movieId)
    }

    suspend fun fetchMovieVideos(movieId: Int): Result<List<Video>> {
        return movieByGenreRepository.fetchMovieVideos(movieId)
    }
}