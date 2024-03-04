package com.girendi.flicknest.core.domain.usecase

import com.girendi.flicknest.core.data.response.ListReviewResponse
import com.girendi.flicknest.core.data.response.ListVideoResponse
import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.domain.model.Review
import com.girendi.flicknest.core.domain.model.Video
import com.girendi.flicknest.core.domain.Result
import com.girendi.flicknest.core.domain.repository.MovieRepository
import com.girendi.flicknest.core.domain.repository.ReviewRepository
import com.girendi.flicknest.core.domain.repository.VideoRepository

class FetchDetailMovieUseCase(
    private val movieRepository: MovieRepository,
    private val reviewRepository: ReviewRepository,
    private val videoRepository: VideoRepository
    ) {
    suspend fun fetchMovieDetail(movieId: Int): Result<Movie> =
        movieRepository.fetchMovieDetail(movieId)
    suspend fun fetchReviewByMovie(page: Int, movieId: Int): Result<ListReviewResponse> =
        reviewRepository.fetchReviewByMovie(page, movieId)
    suspend fun fetchVideoByMovie(movieId: Int): Result<ListVideoResponse> =
        videoRepository.fetchVideoByMovie(movieId)
}