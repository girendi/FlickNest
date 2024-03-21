package com.girendi.flicknest.core.data.source.remote.repository

import com.girendi.flicknest.core.data.source.remote.api.ApiService
import com.girendi.flicknest.core.data.source.remote.response.ListVideoResponse
import com.girendi.flicknest.core.data.Result
import com.girendi.flicknest.core.domain.repository.VideoRepository

class VideoRepositoryImpl(private val apiService: ApiService): BaseRepository(),
    VideoRepository {
    override suspend fun fetchVideoByMovie(movieId: Int): Result<ListVideoResponse> =
        apiRequest { apiService.fetchMovieVideos(movieId) }
}