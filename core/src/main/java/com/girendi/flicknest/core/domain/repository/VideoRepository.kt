package com.girendi.flicknest.core.domain.repository

import com.girendi.flicknest.core.data.source.remote.response.ListVideoResponse
import com.girendi.flicknest.core.data.Result

interface VideoRepository {
    suspend fun fetchVideoByMovie(movieId: Int): Result<ListVideoResponse>
}