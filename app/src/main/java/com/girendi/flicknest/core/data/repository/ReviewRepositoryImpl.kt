package com.girendi.flicknest.core.data.repository

import com.girendi.flicknest.core.data.api.ApiService
import com.girendi.flicknest.core.data.response.ListReviewResponse
import com.girendi.flicknest.core.domain.Result
import com.girendi.flicknest.core.domain.repository.ReviewRepository

class ReviewRepositoryImpl(private val apiService: ApiService): BaseRepository(), ReviewRepository {
    override suspend fun fetchReviewByMovie(page: Int, movieId: Int): Result<ListReviewResponse> =
        apiRequest { apiService.fetchMovieReviews(movieId, page) }
}