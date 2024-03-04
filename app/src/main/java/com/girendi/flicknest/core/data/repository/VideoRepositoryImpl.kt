package com.girendi.flicknest.core.data.repository

import com.girendi.flicknest.core.data.api.ApiService
import com.girendi.flicknest.core.data.response.ListVideoResponse
import com.girendi.flicknest.core.domain.Result
import com.girendi.flicknest.core.domain.repository.VideoRepository

class VideoRepositoryImpl(private val apiService: ApiService): BaseRepository(), VideoRepository {
    override suspend fun fetchVideoByMovie(movieId: Int): Result<ListVideoResponse> =
        apiRequest { apiService.fetchMovieVideos(movieId) }
}