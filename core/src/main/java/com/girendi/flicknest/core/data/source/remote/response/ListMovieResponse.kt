package com.girendi.flicknest.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

class ListMovieResponse(
    @field:SerializedName("page")
    var page: Int = 0,
    @field:SerializedName("results")
    val listMovie: List<MovieResponse>
)