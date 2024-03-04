package com.girendi.flicknest.core.domain.repository

import com.girendi.flicknest.core.data.response.ListReviewResponse
import com.girendi.flicknest.core.domain.Result

interface ReviewRepository {
    suspend fun fetchReviewByMovie(page: Int, movieId: Int): Result<ListReviewResponse>
}