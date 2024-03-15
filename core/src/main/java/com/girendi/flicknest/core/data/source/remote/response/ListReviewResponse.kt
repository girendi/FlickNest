package com.girendi.flicknest.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

class ListReviewResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("page")
    val page: Int,
    @field:SerializedName("results")
    val listReview: List<ReviewResponse>,
)