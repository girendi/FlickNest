package com.girendi.flicknest.core.domain.usecase

import com.girendi.flicknest.core.data.source.remote.response.ListReviewResponse
import com.girendi.flicknest.core.data.source.remote.response.ListVideoResponse
import com.girendi.flicknest.core.data.Result
import com.girendi.flicknest.core.data.source.remote.response.MovieResponse
import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.domain.repository.MovieRepository
import com.girendi.flicknest.core.domain.repository.ReviewRepository
import com.girendi.flicknest.core.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow

class FetchDetailMovieUseCase(
    private val movieRepository: MovieRepository,
    private val reviewRepository: ReviewRepository,
    private val videoRepository: VideoRepository
    ) {
    suspend fun fetchMovieDetail(movieId: Int): Result<MovieResponse> =
        movieRepository.fetchMovieDetail(movieId)
    suspend fun insertFavoriteMovie(movie: Movie) =
        movieRepository.insertFavoriteMovie(movie)
    suspend fun fetchReviewByMovie(page: Int, movieId: Int): Result<ListReviewResponse> =
        reviewRepository.fetchReviewByMovie(page, movieId)
    suspend fun fetchVideoByMovie(movieId: Int): Result<ListVideoResponse> =
        videoRepository.fetchVideoByMovie(movieId)
    fun getFavoriteMovieById(movieId: Int): Flow<Movie?> =
        movieRepository.getFavoriteMovieById(movieId)
    suspend fun deleteFavoriteMovie(movie: Movie) =
        movieRepository.deleteFavoriteMovie(movie)
}