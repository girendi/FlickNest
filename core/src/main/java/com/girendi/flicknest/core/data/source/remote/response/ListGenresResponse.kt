package com.girendi.flicknest.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListGenresResponse (
    @field:SerializedName("genres")
    val listGenre: List<GenreResponse>
)