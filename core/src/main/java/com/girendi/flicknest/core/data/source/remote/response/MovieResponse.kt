package com.girendi.flicknest.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
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
    var tagline: String? = null,
    @field:SerializedName("backdrop_path")
    var backdropPath: String? = null
)
