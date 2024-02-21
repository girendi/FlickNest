package com.girendi.flicknest.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("title")
    var title: String? = null,
    @field:SerializedName("poster_path")
    var posterPath: String? = null,
    @field:SerializedName("release_date")
    var releaseDate: String? = null,
    @field:SerializedName("overview")
    var overview: String? = null,
    @field:SerializedName("vote_average")
    var voteAverage: Double? = null,
    @field:SerializedName("homepage")
    var homepage: String? = null,
    @field:SerializedName("tagline")
    var tagline: String? = null
) : Parcelable
