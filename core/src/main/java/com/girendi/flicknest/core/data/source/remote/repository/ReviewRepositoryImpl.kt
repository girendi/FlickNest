package com.girendi.flicknest.core.data.source.remote.repository

import com.girendi.flicknest.core.data.source.remote.api.ApiService
import com.girendi.flicknest.core.data.source.remote.response.ListReviewResponse
import com.girendi.flicknest.core.data.Result
import com.girendi.flicknest.core.domain.repository.ReviewRepository

class ReviewRepositoryImpl(private val apiService: ApiService): BaseRepository(),
    ReviewRepository {
    override suspend fun fetchReviewByMovie(page: Int, movieId: Int): Result<ListReviewResponse> =
        apiRequest { apiService.fetchMovieReviews(movieId, page) }
}