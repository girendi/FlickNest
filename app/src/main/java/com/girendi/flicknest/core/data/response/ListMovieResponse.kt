package com.girendi.flicknest.core.data.response

import com.girendi.flicknest.core.domain.model.Movie
import com.google.gson.annotations.SerializedName

class ListMovieResponse(
    @field:SerializedName("page")
    var page: Int = 0,
    @field:SerializedName("results")
    val listMovie: List<Movie>,
    @field:SerializedName("total_pages")
    var totalPages: Int = 0,
    @field:SerializedName("total_results")
    var totalResults: Int = 0
)