package com.girendi.flicknest.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var id: Int = 0,
    var title: String? = null,
    var posterPath: String? = null,
    var releaseDate: String? = null,
    var overview: String? = null,
    var voteAverage: Double? = null,
    var homepage: String? = null,
    var tagline: String? = null,
    var backdropPath: String? = null
) : Parcelable
