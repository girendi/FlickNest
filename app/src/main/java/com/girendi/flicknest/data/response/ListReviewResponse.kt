package com.girendi.flicknest.data.response

import com.girendi.flicknest.data.model.Review
import com.google.gson.annotations.SerializedName

class ListReviewResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("page")
    val page: Int,
    @field:SerializedName("results")
    val listReview: List<Review>,
    @field:SerializedName("total_pages")
    val totalPages: Int,
    @field:SerializedName("total_results")
    val totalResults: Int
)