package com.girendi.flicknest.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val id: String,
    val content: String,
    val author: String,
    val url: String,
    val createdAt: String
) : Parcelable
