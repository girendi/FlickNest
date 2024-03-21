package com.girendi.flicknest.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String
) : Parcelable
