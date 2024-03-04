package com.girendi.flicknest.core.domain.repository

import com.girendi.flicknest.core.data.response.ListVideoResponse
import com.girendi.flicknest.core.domain.Result

interface VideoRepository {
    suspend fun fetchVideoByMovie(movieId: Int): Result<ListVideoResponse>
}