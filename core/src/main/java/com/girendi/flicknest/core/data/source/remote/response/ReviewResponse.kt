package com.girendi.flicknest.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("content")
    val content: String,
    @field:SerializedName("author")
    val author: String,
    @field:SerializedName("url")
    val url: String,
    @field:SerializedName("created_at")
    val createdAt: String
)
