package com.girendi.flicknest.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
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
) : Parcelable
