package com.girendi.flicknest.core.utils

import com.girendi.flicknest.core.data.source.remote.response.ReviewResponse
import com.girendi.flicknest.core.domain.model.Review

object DataMapperReview {
    fun mapResponsesToDomain(input: List<ReviewResponse>): List<Review> {
        val reviewList = ArrayList<Review>()
        input.map {
            val review = Review(
                id = it.id,
                content = it.content,
                author = it.author,
                url = it.url,
                createdAt = it.createdAt
            )
            reviewList.add(review)
        }
        return reviewList
    }
}