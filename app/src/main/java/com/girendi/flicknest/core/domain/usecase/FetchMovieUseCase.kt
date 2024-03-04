package com.girendi.flicknest.core.domain.usecase

import com.girendi.flicknest.core.data.response.ListMovieResponse
import com.girendi.flicknest.core.data.response.ListVideoResponse
import com.girendi.flicknest.core.domain.model.Movie
import com.girendi.flicknest.core.domain.model.Video
import com.girendi.flicknest.core.domain.Result
import com.girendi.flicknest.core.domain.repository.MovieRepository
import com.girendi.flicknest.core.domain.repository.VideoRepository

class FetchMovieUseCase(
    private val movieRepository: MovieRepository,
    private val videoRepository: VideoRepository
) {
    suspend fun fetchTrendingMovies(page: Int): Result<ListMovieResponse> =
        movieRepository.fetchTrendingMovies(page)
    suspend fun fetchPopularMovies(page: Int): Result<ListMovieResponse> =
        movieRepository.fetchPopularMovies(page)
    suspend fun fetchVideoByMovie(movieId: Int): Result<ListVideoResponse> =
        videoRepository.fetchVideoByMovie(movieId)
}